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
        // TO DO
        return "";
    }
    
    public String apdlcToRejeu(String[] apdlc){
        // TO DO
        return "";
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
