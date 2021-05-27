package my.battle.ship.bot;
import battleship.BattleShip;

/** Class MyBattleShipBot 
 *  This program plays battle ship for the number of games specified. 
 *  The program has a bot which tries to win the game as fast as possible. 
 * 
 * @author Norman
 */
public class MyBattleShipBot {    
    public static void main(String[] args) {
        startingSolution();
    }
    static final int NUMBEROFGAMES = 1000000; 
    public static void startingSolution()  {        
        double seconds = 0;
        int totalShots = 0;
        System.out.println(BattleShip.version());
        for (int game = 0; game < NUMBEROFGAMES; game++) {
            BattleShip battleShip = new BattleShip();
            SampleBot sampleBot = new SampleBot(battleShip);                           
            long starttime = System.nanoTime(); /// Start timer.
            // Call the Bot,  fire a shot, untill all boats are sunk. 
            while (!battleShip.allSunk()) {                
                sampleBot.ShotStrategy();            
            }          
            seconds = (System.nanoTime() - starttime)/1000000000; // End Timer
            //sampleBot.printMap(); /// Uncomment to print map.
            int gameShots = battleShip.totalShotsTaken();
            totalShots += gameShots;
        }
        //// Print report of the average of shots required per game to sink allships.
        System.out.printf("SampleBot - The Average # of Shots required in %d games to sink all Ships = %.2f\n.      "
        + "       The  time required to finish  %f seconds \n", NUMBEROFGAMES, (double)totalShots / NUMBEROFGAMES, seconds);    
        
    }//// End of method startingSolution()
}//// End of class MyBattleShipBot()