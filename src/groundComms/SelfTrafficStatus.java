/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skysplit;

/**
 *
 * @author vladb
 */
public class SelfTrafficStatus {
    private double lat;
    private double lng;
    private int alt; //ft
    private int spd; //kts
    private int hdg; //degrees
    
    public SelfTrafficStatus(double lat, double lng, int alt, int spd, int hdg) {
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
        this.spd = spd;
        this.hdg = hdg;
    }
    
    private double getLat(){
        return this.lat;
    }

    public double getLng() {
        return lng;
    }

    public int getAlt() {
        return alt;
    }

    public int getSpd() {
        return spd;
    }

    public int getHdg() {
        return hdg;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public void setHdg(int hdg) {
        this.hdg = hdg;
    }
}
