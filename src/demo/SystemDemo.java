/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo;

import airborne.AirborneComms;
import fr.dgac.ivy.IvyException;
import ground.GroundComms;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mateu
 */
public class SystemDemo {
    public static void main(String[] args) throws IvyException {
        GroundComms groundSystem = new GroundComms();
        AirborneComms selfTraffic = new AirborneComms();
        
        //groundSystem.stop();
        //selfTraffic.stop();
       
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            Logger.getLogger(SystemDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println();
        System.out.println("Requesting Flight Level 300");
        String cpdlc = "UM20 CLIMB TO 300";
        System.out.println(cpdlc);
        groundSystem.sendToAirborne(cpdlc);
        
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException ex) {
            Logger.getLogger(SystemDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         System.out.println();
        System.out.println("Requesting Heading 80");
        cpdlc = "UM190 FLY HEADING 80";
        System.out.println(cpdlc);
        groundSystem.sendToAirborne(cpdlc);
        
        try {
            TimeUnit.SECONDS.sleep(40);
        } catch (InterruptedException ex) {
            Logger.getLogger(SystemDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println();
        System.out.println("Requesting Flight Level 200");
        cpdlc = "UM23 DESCEND TO 200";
        System.out.println(cpdlc);
        groundSystem.sendToAirborne(cpdlc);
    }
}
