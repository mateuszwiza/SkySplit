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
                
                String ackMessage = "ACK = " + ack;
                
                try {
                    bus.sendMsg(ackMessage);
                }catch (IvyException ex){
                    Logger.getLogger(GroundComms.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
            }
        });
        
        //      bind APDLC Format

        
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
