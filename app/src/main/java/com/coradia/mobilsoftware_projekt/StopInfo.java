package com.coradia.mobilsoftware_projekt;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.List;

public class StopInfo {

    private int index;
    private String stadt_Name;
    private String haltestellen_Name;
    private double entfernung;
    private String productClassesString;
    private String stationid;

    private int departures;


    public StopInfo(int index, String stadt_Name, String haltestellen_Name, double entfernung, String productClassesString, String stationid, int departures) {
        this.index = index;
        this.stadt_Name = stadt_Name;
        this.haltestellen_Name = haltestellen_Name;
        this.entfernung = entfernung;
        this.productClassesString = productClassesString;
        this.stationid = stationid;
        this.departures = departures;
    }

    public static void loggeStopInfoListe(List<StopInfo> stopInfoList) {
        for (StopInfo daten : stopInfoList) {
            @SuppressLint("DefaultLocale") String logText = String.format(
                    "Index: %d, Stadtname: %s, Haltestellenname: %s, Entfernung: %.2f, Verkehrsmittel: %s, StationID: %s, Abfahrten: %s",
                    daten.getIndex(),
                    daten.getStadt_Name(),
                    daten.getHaltestellen_Name(),
                    daten.getEntfernung(),
                    daten.getProductClassesString(),
                    daten.getStationid(),
                    daten.getDepartures()
            );

            Log.d("MapActivity", logText);
        }
    }

    public static void leereStopInfoList(List<StopInfo> stopInfoList) {
        stopInfoList.clear();
    }



    public void setIndex(int index) {
        this.index = index;
    }

    public void setStadt_Name(String stadt_Name) {
        this.stadt_Name = stadt_Name;
    }

    public void setHaltestellen_Name(String haltestellen_Name) {
        this.haltestellen_Name = haltestellen_Name;
    }

    public void setEntfernung(double entfernung) {
        this.entfernung = entfernung;
    }

    public void setProductClassesString(String productClassesString) {
        this.productClassesString = productClassesString;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }

    public void setDepartures(int departures) {
        this.departures = departures;
    }

    public int getIndex() {
        return index;
    }

    public String getStadt_Name() {
        return stadt_Name;
    }

    public String getHaltestellen_Name() {
        return haltestellen_Name;
    }

    public double getEntfernung() {
        return entfernung;
    }

    public String getProductClassesString() {
        return productClassesString;
    }

    public String getStationid() {
        return stationid;
    }

    public int getDepartures() {
        return departures;
    }


}

