package my.battle.ship.bot;


import battleship.BattleShip;
import battleship.CellState;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.Stack;


/** Class SampleBot
 *      Makes the decision on which square to shoot at. 
 *      
 *       Constructor SampleBot()
 *      
 *      Methods 
 *          ShotStrategy()
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
      for (int x=0; x<gameSize; x++) {  for (int y=0; y<gameSize; y++) {  board[x][y] = CellState.Empty;  }  }
      random = new Random();
    }//// End of constructor SampleBot




    /** Method ShotStrategy() 
     *      This method begins deciding where to shoot. 
     *      It calls fibinnachiModTen(), four times, then ends with a while loop calling thridshot().   
     *      
     * 
     * @AIMX @AIMY Global variable used to determine best choice for next shot Point. 
     */      
    public void ShotStrategy() {
        fibinnachiModTen(1);     
        fibinnachiModTen(2);    
        fibinnachiModTen(3);     
        fibinnachiModTen(4);          
        while (!battleShip.allSunk()) {    
            thridshot();  
       } 
    }//// End method ShotStrategy() 
  
    
    
  /** Method fibinnachiModTen()
   *    Builds an array of points based on a fibinnachi sequence of points. 
   *    Mod 10 is used on the return value to keep the number below 10. 
   *    Then shoots at the points untill a hit, or all points gone.
   *    When their is a hit, it calls the AreaShot() method. 
   *    When the fibinnachi method ends without a hit, the fireshot() method calls it again with a different direction
   *    value. The direction parameter builds the fibinnachi sequence starting from a different corner.
   * 
   *    The first  fibpairs deque looks like this on the game board. (No areashots, only fib) 
   *        
   *        x--->
   *     y o.........    0
   *     \  .oo.....o.  1
   *     \  ...o......   2
   *    V   ....o..o..  3 
   *        .........o   4
   *        .....o....   5
   *        ..........    6
   *        ..........    7
   *        ......o...   8
   *        ..........   9
   *        0123456789
   * 
   *      fibpairs: [x,y]  [0,0]  [1,1]  [2,1]  [3,2]  [4,3]  [5,5]  [6,8]  [7,3]  [8,1]  [9,4]
   *      The four other fibpairs would the same as above but they start in different corners. 
   *      The second set starts from the bottom left, thrid set from bottom right, fourth, from bottom top. 
   *      The direction parameter specifies which corner the sequence gets developed out of.
   *      When the fibpairs are ready to be use to shoot at the map, if the point already has been shot, 
   *      the method will skip to the next point. If their is a hit, the method will call AreaFireShot(), which 
   *      hits around that point. 
   *       
   * 
   * 
   *  
   * 
   * 
   * @param direction 
   */
  public void fibinnachiModTen(int direction) {    
    int axisy;
    int axisx;
    ArrayDeque<Point> fibpairs = new ArrayDeque<Point>();
     if (direction == 1) { 
        for( int x = 0; x < 10; x++) {  
            ////System.out.printf("\nfib("+x+") = "+fib(x)+" ");
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
           axisx = 9 - axisx;                                 
            Point pair = new Point(axisx, axisy);
            fibpairs.add(pair);        
        }
     }      
    ///System.out.printf("\nfibpairs: ");
     while( ! fibpairs.isEmpty() ) {
        Point fibpoint = new Point(fibpairs.pop());                    
        int x =  (int) fibpoint.getX();
        int y = (int)  fibpoint.getY();                     
        //System.out.print(" ["+x+","+y+"] ");
        if( board[x][y] == CellState.Empty ) {
            boolean hit = battleShip.shoot(fibpoint); 
            if(hit) {
                Totalhit++;
                board[x][y] = CellState.Hit;
                AreaFireShot(x, y); 
            } else {
                board[x][y] = CellState.Miss; 
            }
        } 
     }//// End while    
     //printMap();
  }/// End of method fibinnachiModTen
  
  
  
  /** Method fib()
   *        calculates the sequence point based on n
   *        
   * Example Output of fib(): 
   * fib(0) = 0 
   * fib(1) = 1 
   * fib(2) = 1 
   * fib(3) = 2 
   * fib(4) = 3 
   * fib(5) = 5 
   * fib(6) = 8 
   * fib(7) = 13 
   * fib(8) = 21 
   * fib(9) = 34
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
              if( AIMX >= gameSize ) {
                  AIMX = AIMX - gameSize;
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
        AreaFireShot( x, y);
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
private void AreaFireShot(int x, int y) {    
    boolean hit;        
    board[x][y] = CellState.Hit;      
    Stack<Point> AreaShots = new Stack<Point>();
    if ( x+1 != 10 && board[x+1][y] == CellState.Empty)  {         
        AreaShots.add(new Point(x+1,y));     
    }
    if (x-1 !=-1 && board[x-1][y] == CellState.Empty ) {         
        AreaShots.add(new Point(x-1,y));
    }
    if (y+1 != 10 && board[x][y+1] == CellState.Empty ) {        
        AreaShots.add(new Point(x,y+1));  
    }
    if ( y-1 != -1 &&  board[x][y-1] == CellState.Empty) {        
        AreaShots.add(new Point(x,y-1));
    }
    while( !AreaShots.empty()) {    
       // System.out.printf("\nAreaShot ");
        Point pewpew = AreaShots.pop();
        hit = battleShip.shoot(pewpew);  
        int c =  (int) pewpew.getX();
        int d = (int) pewpew.getY();  
       // System.out.print(" ["+x+","+y+"] ");
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
        //printMap();
    }
}/// End of Method AreaFireShot()
 
  
   

/** Method InlineFireShot() 
 *      A recursive method which shoots inline with the last two hits.
 *      When InlineFireShot gets a hit it will call it self with the hit point, and the x,y of the previous hit. 
 *      This causes the method to shoot inline untill it gets a miss. A miss will end the method and 
 *      AreaShots method will continue shooting it's stack if is not empty. 
 *  
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
 *      Prints game map.
 *      X his a hit
 *      o is a miss.
 *      . not shot at.
 */
 public void printMap() {
      System.out.println();
      for(int y=0; y<gameSize; y++) {
          for(int x=0; x<gameSize; x++) {
              System.out.print(" "+board[x][y]);         
          } 
          System.out.println();
      }            
  }//// End printMap()

 
 

}// End Class SampleBot()