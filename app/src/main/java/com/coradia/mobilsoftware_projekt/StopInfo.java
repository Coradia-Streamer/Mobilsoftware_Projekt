package com.coradia.mobilsoftware_projekt;

public class StopInfo {

    private int index;
    private String stadt_Name;
    private String haltestellen_Name;
    private double entfernung;
    private String productClassesString;
    private String stationid;


    public StopInfo(int index, String stadt_Name, String haltestellen_Name, double entfernung, String productClassesString, String stationid) {
        this.index = index;
        this.stadt_Name = stadt_Name;
        this.haltestellen_Name = haltestellen_Name;
        this.entfernung = entfernung;
        this.productClassesString = productClassesString;
        this.stationid = stationid;
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



}

