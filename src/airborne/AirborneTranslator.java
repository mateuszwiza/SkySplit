/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airborne;

/**
 *
 * @author mateu
 */
public class AirborneTranslator {
    private int flightID;
    
    public AirborneTranslator(int number){
        flightID = number;
    }
    
    public String rejeuToAAR(String[] rejeu){
        String altitude = rejeu[8];
        String heading = rejeu[10];
        String aar = "Flight Level: " + altitude + "   |   Heading: " + heading;
        System.out.println(aar);
        return aar;
    }
    
    public String apdlcToRejeu(String[] apdlc){
        String rejeu = "";
        if(apdlc[0].contains("20 CLIMB TO")){
            String alt = apdlc[0].split(" ")[3];
            rejeu = "AircraftLevel Flight=" + Integer.toString(flightID) + " Fl=" + alt;
            
        }else if(apdlc[0].contains("23 DESCEND TO")){
            String alt = apdlc[0].split(" ")[3];
            rejeu = "AircraftLevel Flight=" + Integer.toString(flightID) + " Fl=" + alt;
        }else if(apdlc[0].contains("190 FLY HEADING")){
            String hdg = apdlc[0].split(" ")[3];
            rejeu = "AircraftHeading Flight=" + Integer.toString(flightID) + " To=" + hdg;
        }
        //System.out.println(rejeu);
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
