/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ground;

import java.util.Arrays;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author vladb
 */
// ???
public class GroundProcessor {
    private String callsign;
    
    //Change aircraft altitude from APDLC message coming from the pilot
    public int altitude(String message, SelfTrafficStatus currentStatus){
        
        if(message.contains("UM19 MAINTAIN")){
            int newAlt = Integer.parseInt(message.substring(15));
            currentStatus.setAlt(newAlt);
            return newAlt;
        }
        
        if(message.contains("UM20 CLIMB TO")){
            int newAlt = Integer.parseInt(message.substring(15)) + currentStatus.getAlt();
            currentStatus.setAlt(newAlt);
            return newAlt;
        }
        
        if(message.contains("UM23 DESCEND TO")){
            int newAlt = Integer.parseInt(message.substring(17)) + currentStatus.getAlt();
            currentStatus.setAlt(newAlt);
            return newAlt;
        }
        
        return currentStatus.getAlt();
    }
    
    //Change aircraft heading from APDLC message coming from the pilot
    public int heading(String message, SelfTrafficStatus currentStatus){
        
        if(message.contains("UM94 TURN") || message.contains("UM215 TURN")){
            if(message.contains("LEFT")){
                int newHdg = Integer.parseInt(message.substring(16)) - currentStatus.getHdg();
                if(newHdg < 0)newHdg = 360 - Math.abs(newHdg);
                currentStatus.setHdg(newHdg);
            }
            
            if(message.contains("RIGHT")){
                int newHdg = Integer.parseInt(message.substring(17)) + currentStatus.getHdg();
                if(newHdg > 360)newHdg = Math.abs(newHdg) - 360;
                currentStatus.setHdg(newHdg);
            }
        }
        
        if(message.contains("UM190 FLY HEADING")){   
            int newHdg = Integer.parseInt(message.substring(19));
            if(newHdg < 0 || newHdg > 360)System.out.println("ERROR - Heading -> [0,360)");
                else currentStatus.setHdg(newHdg);
        }
        
        return currentStatus.getHdg();
    }
    
    //Change aircraft speed from APDLC message coming from the pilot
    public int speed(String message, SelfTrafficStatus currentStatus){
        
        if(message.contains("UM106 MAINTAIN")){
            int newSpd = currentStatus.getSpd();
            currentStatus.setSpd(newSpd);
            return newSpd;
        }
        
        if(message.contains("UM111 INCREASE SPEED TO")){
            int newSpd = Integer.parseInt(message.substring(25));
            currentStatus.setSpd(newSpd);
            return newSpd;
        }
        
        if(message.contains("UM113 REDUCE SPEED TO")){
            int newSpd = Integer.parseInt(message.substring(23));
            currentStatus.setSpd(newSpd);
            return newSpd;
        }
                
        return currentStatus.getSpd();
    }
    
    //configure functions for waypoints
    //proceedToWp
    //directTo
    
    //check what type of APDLC message the pilot sends, and apply the correct function from above
    public void prepAPDLC(String message, SelfTrafficStatus currentStatus){
        
        if (message.contains("UM19") || message.contains("UM20") || message.contains("UM23")){
            this.altitude(message, currentStatus);
        }
        
        if (message.contains("UM94") || message.contains("UM215")){
            this.heading(message, currentStatus);
        }
        
        if (message.contains("UM106") || message.contains("UM111") || message.contains("UM113")){
            this.speed(message, currentStatus);
        }
        
    }
    
    //anti-collision - To be done
    public String processAlert(String alert){
        return alert;
    }
    
    //update selfTraffic status
    public String[] processAAR(String message){
        String[] status = message.split(" ");
        return status;
    }
    
    
}
