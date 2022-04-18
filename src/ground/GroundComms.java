/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ground;

import airborne.AirborneComms;
import java.util.Arrays;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vladb
 */
public class GroundComms {
    
    private Ivy bus;
    private SelfTrafficStatus currentStatus = new SelfTrafficStatus(0,0,0,0,0);
    
    public GroundComms() throws IvyException {
        bus = new Ivy("A-G-Connection", "Ground Communications", null);
        
        //bind AAR Format
        bus.bindMsg("DM(.*)", new IvyMessageListener(){
            @Override
            public void receive(IvyClient ic, String[] strings) {
                String ack = strings[0];
                String[] arrayACK = ack.split(" ");
                String message = "";
                
                if(arrayACK[1].contains("WILCO"))message = "Instruction understood and will be complied with.";
                else if(arrayACK[1].contains("UNABLE"))message = "Instruction cannot be complied with.";
                else if(arrayACK[1].contains("STANDBY"))message = "A response will be delivered shortly.";
                
                try {
                    //System.out.println(message);
                    //System.out.println();
                    bus.sendMsg(message);
                }catch (IvyException ex){
                    Logger.getLogger(GroundComms.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }
        });
        
        //      bind APDLC Format
        bus.bindMsg("^TrackMovedEvents(.*)", new IvyMessageListener() {
            // callback
            @Override
            public void receive(IvyClient client, String[] strings) {
                
                String textReceived = strings[0];
                String[] arrayReceived = textReceived.split(" ");
                List <String> trafficStatus = new ArrayList <String>();
                
                //flightOperator = the arguments for flight status
                //statusValue = the value of the operators
                
                for(int i = 0; i < arrayReceived.length; i++){
                    String flightOperator = arrayReceived[i].substring(0,arrayReceived[i].indexOf("=") + 1);
                    
                    if(arrayReceived[i].indexOf("=") != -1){
                        flightOperator = arrayReceived[i].substring(0,arrayReceived[i].indexOf("="));
                    }
                    
                    String statusValue = arrayReceived[i].substring(arrayReceived[i].indexOf("=") + 1);
                    
                    //example for change in flight current status to be sent to GUI
                    if(flightOperator.contains("Afl")){
                        int val = Integer.parseInt(statusValue);
                        currentStatus.setAlt(val);
                    }
                    
                    else if(flightOperator.contains("GroundSpeed")){
                        int val = Integer.parseInt(statusValue);
                        currentStatus.setSpd(val);
                    }
                    
                    else if(flightOperator.contains("Heading")){
                        int val = Integer.parseInt(statusValue);
                        currentStatus.setHdg(val);
                    }
                    
                    //System.out.print(flightOperator + ": " + statusValue + "; ");
                    trafficStatus.add(flightOperator + ": " + statusValue);
                }
                
                //System.out.println();
                
                try {
                    bus.sendMsg(trafficStatus.toString());
                } catch (IvyException ex) {
                    Logger.getLogger(GroundComms.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //      send APDLC Format
        
        //bus.start(null);
        bus.start("127.255.255.255:2010");
    }
    
    public void sendToAirborne(String message){
        try {
            bus.sendMsg(message);
        } catch (IvyException ex) {
            Logger.getLogger(GroundComms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stop(){
        bus.stop();
    }
    
    public static void main(String[] args) throws IvyException {
        try {
            new GroundComms();
        } catch (IvyException ex) {
            // Print an explicit message in case of IvyException
            throw new RuntimeException("Ivy bus had a failure. Quitting Ex2_SimpleIvyApplication...");
        }
    }
    
    
}
