/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skysplit;

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
    
    public GroundComms() throws IvyException {
        bus = new Ivy("A-G-Connection", "GetNewFlight MsgName=NewF", null);
        
        //bind AAR Format
        bus.bindMsg("^DM(.*)", new IvyMessageListener(){
            @Override
            public void receive(IvyClient ic, String[] strings) {
                String ack = strings[0];
                String[] arrayACK = ack.split(" ");
                String message = "";
                
                if(arrayACK[1] == "WILCO")message = "Instruction understood and will be complied with.";
                else if(arrayACK[1] == "UNABLE")message = "Instruction cannot be complied with.";
                else if(arrayACK[1] == "STANDBY")message = "A response will be delivered shortly.";
                
                try {
                    bus.sendMsg(message);
                }catch (IvyException ex){
                    Logger.getLogger(GroundComms.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }
        });
        
        //      bind APDLC Format
        bus.bindMsg("^TrackMovedEvent(.*)", new IvyMessageListener() {
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
                    System.out.print(flightOperator + ": " + statusValue + "; ");
                    trafficStatus.add(flightOperator + ": " + statusValue);
                }
                
                System.out.println();
                
                try {
                    bus.sendMsg(trafficStatus.toString());
                } catch (IvyException ex) {
                    Logger.getLogger(AirborneComms.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //      send APDLC Format
        
        //bus.start(null);
        bus.start("127.255.255.255:2010");
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
