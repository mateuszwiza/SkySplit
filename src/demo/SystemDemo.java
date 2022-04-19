/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo;

import airborne.AirborneComms;
import fr.dgac.ivy.IvyException;
import ground.GroundProcessor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author mateu
 */
public class SystemDemo {
    public static void main(String[] args) throws IvyException, InterruptedException {
        GroundProcessor groundSystem = new GroundProcessor();
        AirborneComms selfTraffic = new AirborneComms();
        
        TimeUnit.SECONDS.sleep(20);
        
        groundSystem.altitude(290);
        
        TimeUnit.SECONDS.sleep(20);
        
        groundSystem.speed(540);
        
        TimeUnit.SECONDS.sleep(20);
        
        groundSystem.heading(80);
        
        TimeUnit.SECONDS.sleep(120);
        
        //roundSystem.altitude(250);
        
        //TimeUnit.SECONDS.sleep(100);
        
        groundSystem.stop();
        selfTraffic.stop();
        
        //groundSystem.sendToAirborne("NewFlight NewF NewID=101");
        
        
        /*
        String[] commands = new String[13];
        
        commands[0] = "UM67 PROCEED BACK ON ROUTE";
        commands[1] = "UM20 CLIMB TO 300";
        commands[2] = "UM20 DESCEND TO 300";
        commands[3] = "UM19 MAINTAIN 300";
        commands[4] = "UM74 PROCEED DIRECT TO XAMPO";
        commands[5] = "UM94 TURN LEFT HEADING 20";
        commands[6] = "UM94 TURN RIGHT HEADING 20";
        commands[7] = "UM106 MAINTAIN 240";
        commands[8] = "UM111 INCREASE SPEED TO 240";
        commands[9] = "UM113 REDUCE SPEED TO 240";
        commands[10] = "UM190 FLY HEADING 20";
        commands[11] = "UM215 FLY HEADING LEFT 20";
        commands[12] = "UM215 FLY HEADING RIGHT 20";
        
        for (String command : commands) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(SystemDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(command);
            groundSystem.sendToAirborne(command);
        }
        groundSystem.stop();
        selfTraffic.stop();

*/
        
    }
}
