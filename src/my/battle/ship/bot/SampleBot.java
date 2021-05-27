package my.battle.ship.bot;


import battleship.BattleShip;
import battleship.CellState;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.Stack;


/** class SampleBot
 *      Makes the decision on which square to shoot at. 
 *      
 *       Constructor SampleBot()
 *      
 *      Methods 
 *          fireShot()
 *          fibinnachi()
 *          fib()
 *          thirdshot()
 *          AreaFireShot()
 *          InlineFireShot() 
 *          printMap()
 * 
 * 
 * @author Norman
 */
public class SampleBot {    
    private int gameSize;
    private BattleShip battleShip;
    private Random random;
    private CellState[][] board;      
    private int Totalhit = 0;
    
 
    /** Constructor SampleBot()
     *      Keeps a copy of the battleship instance
     * 
     * @param Ship previously created battleship instance - should be a new game
     */
    public SampleBot(BattleShip ship) {     
      battleShip = ship;
      gameSize = ship.BOARDSIZE;
      random = new Random();   
      board = new CellState[gameSize][gameSize];
      
      for (int x=0; x<gameSize; x++) {
          for (int y=0; y<gameSize; y++) {
              board[x][y] = CellState.Empty;            
          }
      }
      random = new Random();
    }//// End of constructor SampleBot




    /** Method Fireshot() 
     *      This method determines where to shoot, and shoots.
     *      There are three types of aiming methods
     *      First one aims by every 3 thrid spot
     *      Second Aims in the area of a hit.
     *      Third uses a uses a second recursive method to aim close to a hit
     * @AIMX @AIMY Global variable used to determine best choice for next shot Point. 
     */      
    public void fireShot() {
        fibinnachi(1);     
        fibinnachi(2);    
        fibinnachi(3);     
        fibinnachi(4);          
        while (!battleShip.allSunk()) {    
            thridshot();  
       } 
    }//// End method fireShot() 
  
    
    
  /** Method fibinnachi()
   *    Builds an array of points based on a fibinnachi sequence of points. 
   *    Then shoots at the points untill a hit, or all points gone.
   * 
   * @param direction 
   */
  public void fibinnachi(int direction) {    
    int axisy;
    int axisx;
    ArrayDeque<Point> fibpairs = new ArrayDeque<Point>();
     if (direction == 1) { 
        for( int x = 0; x < 10; x++) {    
            axisy =  (fib(x))%10; 
            axisx = x;  
            Point pair = new Point(axisx, axisy);
            fibpairs.add(pair);        
        }
     }
     if (direction == 2) { 
         for( int x = 0; x < 10; x++) {                
            axisy =  (fib(x))%10; 
            axisx = x;           
            axisy = 9 - axisy;
            axisx = x;                      
            Point pair = new Point(axisx, axisy);
            fibpairs.add(pair);        
        } 
     }     
     if (direction == 3) {
         for( int x = 0; x < 10; x++) {    
            axisy =  (fib(x))%10; 
            axisx = x;   
            axisy = 9 - axisy;
            axisx = 9 - x;                        
            Point pair = new Point(axisx, axisy);
            fibpairs.add(pair);        
        }
     }     
     if (direction == 4) {         
         for( int x = 0; x < 10; x++) {                
           axisy =  (fib(x))%10; 
           axisx = x;      
           axisy = axisy;
           axisx = 9 - axisx;                                 
            Point pair = new Point(axisx, axisy);
            fibpairs.add(pair);        
        }
     }                
     while( ! fibpairs.isEmpty() ) {
        Point fibpoint = new Point(fibpairs.pop());            
        int x =  (int) fibpoint.getX();
        int y = (int)  fibpoint.getY();             
        if( board[x][y] == CellState.Empty ) {
            boolean hit = battleShip.shoot(fibpoint); 
            if(hit) {
                Totalhit++;
                board[x][y] = CellState.Hit;
                AreaFireShot(fibpoint, x, y); 
            } else {
                board[x][y] = CellState.Miss; 
            }
        } 
     }//// End while        
  }/// End of method fibinnachi
  
  
  
  /** Method fib()
   * 
   * @param n 
   * @return 
   */
  public static int fib(int n) {
    if ( n == 0) {
        return 0;
    } else if (n == 1) {
        return 1;   
    } else {
        return fib(n -1) + fib(n - 2);
    }  
  }//// End of method fib()

  
  
  /** Method thirdshot()
   *    Fires the third shot.
   */
  public void thridshot() {
    int AIMX = 0; 
    int AIMY = 0; 
     if (board[0][0] != CellState.Empty) {               
          do {
              AIMX = AIMX +3;
              if( AIMX >= gameSize) {
                  AIMX = AIMX - gameSize ;
                  AIMY++;
              }
             if( AIMY >= gameSize) {
                 AIMY = 0;                
             }               
          } while(board[AIMX][AIMY] != CellState.Empty);    
     }              
    Point shot = new Point(AIMX,AIMY);     
    boolean hit = battleShip.shoot(shot);
    int x =  (int) shot.getX();
    int y = (int)  shot.getY();    
    if (hit) {
        Totalhit++;                        
        AreaFireShot(shot, x, y);
    } else {
          board[x][y] = CellState.Miss; 
    }         
  }//// End of method thirdshot
  
  
  
  
/** Method AreaFireShot()
 *   It was a hit, shoot near boat. 
 * 
 * @param shot
 * @param x
 * @param y 
 */
private void AreaFireShot(Point shot, int x, int y) {
    boolean hit;        
    board[x][y] = CellState.Hit;      
    Stack<Point> AreaShots = new Stack<Point>();
    if ( x+1 != 10 && board[x+1][y] == CellState.Empty)  { 
        shot = new Point(x+1,y);
        AreaShots.add(shot);     
    }
    if (x-1 !=-1 && board[x-1][y] == CellState.Empty ) { 
        shot = new Point(x-1,y);
        AreaShots.add(shot);
    }
    if (y+1 != 10 && board[x][y+1] == CellState.Empty ) { 
        shot = new Point(x,y+1);
        AreaShots.add(shot);  
    }
    if ( y-1 != -1 &&  board[x][y-1] == CellState.Empty) { 
        shot = new Point(x,y-1);
        AreaShots.add(shot);
    }
    while( !AreaShots.empty()) {           
        Point pewpew = AreaShots.pop();
        hit = battleShip.shoot(pewpew);  
        int c =  (int) pewpew.getX();
        int d = (int) pewpew.getY();        
        if(hit == true) {            
            Totalhit++;               
            int Xhit =  (int) pewpew.getX(); 
            int Yhit = (int) pewpew.getY();   
            board[Xhit][Yhit] = CellState.Hit; 
            InlineFireShot(pewpew, x, y); 
        } else {            
            int Xhit =  (int) pewpew.getX();
            int Yhit = (int) pewpew.getY();
            board[Xhit][Yhit] = CellState.Miss;
        }           
    }
}
 
  
   

/** Method InlineFireShot() 
 *      A recursive method which shoots inline with the last two hits.
 *      It will shoot in the same direction untill it gets a miss. Meaning the end of the boat has been reached.
 *      AreaShots will continue shooting it's stack if it is not empty. iI there is more of the boat in the other
 *      direction than it will shoot inline that way.
 *      Parameters that are passed are: 
 *      @param oldshot The previous hit. 
 *      @param x the pervious x axis of  the last hit.
 *      @param y The previous y axis of the last hit. 
 */    
private void InlineFireShot(Point oldshot, int x, int y) {
    boolean okaytoshoot = false; 
    Point nextshot = new Point(x,y); 
    int OldX = (int) oldshot.getX(); 
    int OldY = (int) oldshot.getY();    
    if ( OldX > x  && OldY == y) { 
        if ( OldX+1 != 10 && board[OldX+1][OldY] == CellState.Empty )  {
            nextshot = new Point(OldX+1,OldY);
            okaytoshoot = true;
        }
    }
    if (OldX < x && OldY == y) { 
        if ( OldX-1 != -1 && board[OldX-1][OldY] == CellState.Empty ) {
            nextshot = new Point(OldX-1,OldY); 
            okaytoshoot = true;
        }
     }
     if ( OldY > y && OldX == x) { 
        if ( OldY+1 != 10 &&  board[OldX][OldY+1] == CellState.Empty ) {
            nextshot = new Point(OldX,OldY+1); 
            okaytoshoot = true;
        }
     }
     if (OldY < y && OldX == x) { 
        if ( OldY-1 != -1 &&  board[OldX][OldY-1] == CellState.Empty ) {
            nextshot = new Point(OldX,OldY-1); 
            okaytoshoot = true;
        }
     }
     if (okaytoshoot == true) {
        boolean Inlinehit = battleShip.shoot(nextshot);   
        if (Inlinehit == true)  {
            Totalhit++;
            int XRecord =  (int) nextshot.getX();
            int YRecord = (int) nextshot.getY();
            board[XRecord][YRecord] = CellState.Hit;            
            InlineFireShot(nextshot, OldX, OldY );  
        } else {            
            int XRecord =  (int) nextshot.getX();
            int YRecord = (int) nextshot.getY();
            board[XRecord][YRecord] = CellState.Miss;
        }
    }                                 
}//// End of method InlineFireShot()




/** Method printMap()
 * Prints game.
 * X his a hit
 * o is a miss.
 * . not shot at.
 */
 public void printMap() {
      System.out.println();
      for(int y=0; y<gameSize; y++) {
          for(int x=0; x<gameSize; x++) {
              System.out.print(board[x][y]);         
          } 
          System.out.println();
      }            
  }//// End printMap()

 
 
 

}// End Class SampleBot()