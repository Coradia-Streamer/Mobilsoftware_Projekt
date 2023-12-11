package com.coradia.mobilsoftware_projekt.methods;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.List;

public class StopInfo {

    private int index;
    private String stadtName;
    private String haltestellenName;
    private double entfernung;
    private String productClassesString;
    private String stationId;

    private int departures;


    public StopInfo(int index, String stadtName, String haltestellenName, double entfernung, String productClassesString, String stationId, int departures) {
        this.index = index;
        this.stadtName = stadtName;
        this.haltestellenName = haltestellenName;
        this.entfernung = entfernung;
        this.productClassesString = productClassesString;
        this.stationId = stationId;
        this.departures = departures;
    }

    public static void loggeStopInfoListe(List<StopInfo> stopInfoList) {
        for (StopInfo daten : stopInfoList) {
            @SuppressLint("DefaultLocale") String logText = String.format(
                    "Index: %d, Stadtname: %s, Haltestellenname: %s, Entfernung: %.2f, Verkehrsmittel: %s, StationID: %s, Abfahrten: %s",
                    daten.getIndex(),
                    daten.getStadtName(),
                    daten.getHaltestellenName(),
                    daten.getEntfernung(),
                    daten.getProductClassesString(),
                    daten.getStationId(),
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

    public void setStadtName(String stadtName) {
        this.stadtName = stadtName;
    }

    public void setHaltestellenName(String haltestellenName) {
        this.haltestellenName = haltestellenName;
    }

    public void setEntfernung(double entfernung) {
        this.entfernung = entfernung;
    }

    public void setProductClassesString(String productClassesString) {
        this.productClassesString = productClassesString;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setDepartures(int departures) {
        this.departures = departures;
    }

    public int getIndex() {
        return index;
    }

    public String getStadtName() {
        return stadtName;
    }

    public String getHaltestellenName() {
        return haltestellenName;
    }

    public double getEntfernung() {
        return entfernung;
    }

    public String getProductClassesString() {
        return productClassesString;
    }

    public String getStationId() {
        return stationId;
    }

    public int getDepartures() {
        return departures;
    }


}

