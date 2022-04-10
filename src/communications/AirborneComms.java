/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communications;

import java.util.Arrays;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mateu
 */
public class AirborneComms {
    private Ivy bus;
    
    public AirborneComms() throws IvyException {
        // initialize (set up the bus, name and ready message)
        bus = new Ivy("AirborneConnection", "GetNewFlight MsgName=NewF", null);
        bus.bindMsg("^NewFlight NewF NewID=(.*)", new IvyMessageListener() {
            // callback
            @Override
            public void receive(IvyClient client, String[] strings) {
                String flightNb = strings[0];
                String fpMessage = "SetMiniPln Flight=" + flightNb + " CallSign=AF123KQ Speed=300 Ssr=4732 Dep=LFBO Arr=LFPG";
                try {
                    bus.sendMsg("a");
                } catch (IvyException ex) {
                    Logger.getLogger(AirborneComms.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        // start the bus on the default domain
        //bus.start(null);
        bus.start("127.255.255.255:2010");
    }
    
    public void stop(){
        
    }
    
    public static void main(String[] args) throws IvyException {
        AirborneComms airborneComms = new AirborneComms();
    }
    
}

