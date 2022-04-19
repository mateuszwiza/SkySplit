/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airborne;

import java.util.Arrays;

/**
 *
 * @author mateu
 */
public class AirborneTranslator {
    private final int flightID;
    private final String[] altIdents = {"19", "20", "23"};
    private final String[] routeIdents = {"67", "72", "74"};
    private final String[] hdgIdents = {"94", "190", "215"};
    private final String[] spdIdents = {"106", "111", "113"};
    
    public AirborneTranslator(int number){
        flightID = number;
    }
    
    public String rejeuToAAR(String[] rejeu){
        String altitude = rejeu[8];
        String heading = rejeu[10];
        String speed = rejeu[11];
        String aar = "AAR FlightLevel: " + altitude + " Heading: " + heading + " GroundSpeed: " + speed;
        //System.out.println(aar);
        return aar;
    }
    
    public String apdlcToRejeu(String[] apdlc){
        String rejeu = "";
        // Altitude
        if(Arrays.asList(altIdents).contains(apdlc[0])){
            String alt = apdlc[2];
            rejeu = "AircraftLevel Flight=" + Integer.toString(flightID) + " Fl=" + alt;
        }
        // Route
        else if(Arrays.asList(routeIdents).contains(apdlc[0])){
            System.out.println(Arrays.toString(apdlc));
        }
        // Heading
        else if(Arrays.asList(hdgIdents).contains(apdlc[0])){
            String hdg = apdlc[2];
            if(apdlc[0].equals("94")){
                String direction = apdlc[1].split(" ")[1];
                rejeu = "AircraftHeading Flight=" + Integer.toString(flightID) + " To=" + hdg + " By=" + direction;
            }else if(apdlc[0].equals("215")){
                String direction = apdlc[1].split(" ")[2];
                if(direction.equals("LEFT")) rejeu = "AircraftTurn Flight=" + Integer.toString(flightID) + " Angle=-" + hdg;  
                else rejeu = "AircraftTurn Flight=" + Integer.toString(flightID) + " Angle=" + hdg;
            }else{
                rejeu = "AircraftHeading Flight=" + Integer.toString(flightID) + " To=" + hdg;
            }
        }
        // Speed
        else if(Arrays.asList(spdIdents).contains(apdlc[0])){
            String spd = apdlc[2];
            rejeu = "AircraftSpeed Flight=" + Integer.toString(flightID) + " Type=IAS Value=" + spd;
        }
        
        System.out.println(rejeu);
        return rejeu;
    }
    
    public String acknowledge(String result){
        String response = "";
        if(result.equals("OK")){
            response = "DM0 WILCO";
        }else if(result.equals("ERROR")){
            response = "DM1 UNABLE";
        }else if(result.equals("WARNING")){
            response = "DM2 WILCO WARNING";
        }
        return response;
    }
}
