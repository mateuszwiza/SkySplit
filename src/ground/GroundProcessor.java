/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ground;

import fr.dgac.ivy.IvyException;
import java.util.Arrays;

/**
 *
 * @author vladb
 */
// ???
public class GroundProcessor {
    private SelfTrafficStatus currentStatus;
    private GroundComms comms;
    
    public GroundProcessor() throws IvyException{
        currentStatus = new SelfTrafficStatus(0,0,0,0,0);
        comms = new GroundComms(this);
    }
    
    public void altitude(int newAlt){
        
        String message = "";
        if(newAlt < currentStatus.getAlt()){
            message = "UM23 DESCEND TO " + newAlt;
        }else if(newAlt == currentStatus.getAlt()){
           message = "UM19 MAINTAIN " + newAlt;
        }else if(newAlt > currentStatus.getAlt()){
           message = "UM20 CLIMB TO " + newAlt;
        }
        comms.sendToAirborne(message); 
    }
    
    public void speed(int newSpd){
        
        String message = "";
        if(newSpd < currentStatus.getSpd()){
            message = "UM113 REDUCE SPEED TO " + newSpd;
        }else if(newSpd == currentStatus.getSpd()){
           message = "UM106 MAINTAIN " + newSpd;
        }else if(newSpd > currentStatus.getSpd()){
           message = "UM111 INCREASE SPEED TO " + newSpd;
        }
        comms.sendToAirborne(message);
    }
    
    public void heading(int newHdg){
        
        String message = "";
        if (currentStatus.getHdg() + 180 > 360){
            int holder = currentStatus.getHdg() + 180 - 360;
            if((newHdg >= currentStatus.getHdg() && (newHdg < 360)) || ((newHdg >= 0) && (newHdg < holder))){
                message = "UM215 TURN RIGHT " + newHdg;
            }else{
                message = "UM215 TURN LEFT " + newHdg;
            }
        }else if (currentStatus.getHdg() - 180 <= 0){
            int holder = currentStatus.getHdg() - 180 + 360;
            if((newHdg >= holder && (newHdg < 360)) || ((newHdg >= 0) && (newHdg <= currentStatus.getHdg()))){
                message = "UM215 TURN LEFT " + newHdg;
            }else {
                message = "UM215 TURN RIGHT " + newHdg;
            }
        }
        
        message = "UM190 FLY HEADING " + newHdg;
        comms.sendToAirborne(message);
    }

    //anti-collision - To be done
    public String processAlert(String alert){
        return alert;
    }
    
    //update selfTraffic status
    public void processAAR(String[] message){
        //System.out.println(Arrays.toString(message));
        String aar = message[0];
        currentStatus.setAlt(Integer.parseInt(aar.split(" ")[1]));
        currentStatus.setHdg(Integer.parseInt(aar.split(" ")[3]));
        currentStatus.setSpd(Integer.parseInt(aar.split(" ")[5]));
        
        String toprint = "Flight Level: " + currentStatus.getAlt() + " | Heading: " + currentStatus.getHdg() + " | Ground Speed: " + currentStatus.getSpd();
        System.out.println(toprint);
        //String[] status = message.split("; ");
        //return status;
    }
    
    public void stop(){
        comms.stop();
    }
    
      
}
