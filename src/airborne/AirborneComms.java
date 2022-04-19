/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airborne;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mateu
 */
public class AirborneComms {
    private AirborneTranslator translator;
    private Ivy bus;
    private int flightID;
    
    public AirborneComms() throws IvyException {
        // initialize (set up the bus, name and ready message)
        bus = new Ivy("AirborneConnection", "GetNewFlight MsgName=NewF", null);
        
        // Initialize the flight
        bus.bindMsg("^NewFlight NewF NewID=(.*)", new IvyMessageListener() {
            // callback
            @Override
            public void receive(IvyClient client, String[] strings) {
                //flightID = Integer.parseInt(strings[0]);
                flightID = 4996;
                translator = new AirborneTranslator(flightID);
                System.out.println("Flight number: " + flightID);
                System.out.println();
                //String fpMessage = "SetMiniPln Flight=" + flightID + " CallSign=AF123KQ Speed=300 Ssr=4732 Dep=LFBO Arr=LFPG";
                try {
                    //bus.sendMsg(fpMessage);
                    flight();
                } catch (IvyException ex) {
                    Logger.getLogger(AirborneComms.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });

        // start the bus on the default domain
        bus.start("127.255.255.255:2010");
    }
    
    public void flight() throws IvyException{
        
        // Listen for movement reports 
        bus.bindMsg("^TrackMovedEvent Flight="+flightID+" CallSign=(.*) Ssr=(.*) "
                + "Sector=(.*) Layers=(.*) X=(.*) Y=(.*) Vx=(.*) Vy=(.*) Afl=(.*) "
                + "Rate=(.*) Heading=(.*) GroundSpeed=(.*) Tendency=(.*) Time=(.*)", 
                new IvyMessageListener() {
            // callback
            @Override
            public void receive(IvyClient client, String[] strings) {
                try {
                    bus.sendMsg(translator.rejeuToAAR(strings));
                } catch (IvyException ex) {
                    Logger.getLogger(AirborneComms.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        // Listen for APDLC commands
        bus.bindMsg("^UM(\\d*) (.*) (\\d*)", 
                new IvyMessageListener() {
            // callback
            @Override
            public void receive(IvyClient client, String[] strings) {
                try {
                    bus.sendMsg(translator.apdlcToRejeu(strings));
                } catch (IvyException ex) {
                    Logger.getLogger(AirborneComms.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       
        // Listen for APDLC route commands
        bus.bindMsg("^UM(\\d*) PROCEED (.*)", 
                new IvyMessageListener() {
            // callback
            @Override
            public void receive(IvyClient client, String[] strings) {
                try {
                    bus.sendMsg(translator.apdlcToRejeu(strings));
                } catch (IvyException ex) {
                    Logger.getLogger(AirborneComms.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        // Listen for acknowledgement from Rejeu
        bus.bindMsg("^ReportEvent (.*) Result=(.*) Info=(.*)", 
                new IvyMessageListener() {
            // callback
            @Override
            public void receive(IvyClient client, String[] strings) {
                try {
                    //System.out.println(translator.acknowledge(strings[1]));
                    bus.sendMsg(translator.acknowledge(strings[1]));
                } catch (IvyException ex) {
                    Logger.getLogger(AirborneComms.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }
    
    public void stop(){
        bus.stop();
    }
    
    public static void main(String[] args) throws IvyException {
        AirborneComms airborneComms = new AirborneComms();
    }
    
}

