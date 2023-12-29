package com.coradia.mobilsoftware_projekt.methods;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.List;

public class NextbikeInfo {

    private int index;
    private String name;
    private boolean bike;
    private boolean spot;
    private int bikes;
    private double dist;


    public NextbikeInfo(int index, String name, boolean bike, boolean spot, int bikes, double dist) {
        this.index = index;
        this.name = name;
        this.bike = bike;
        this.spot = spot;
        this.bikes = bikes;
        this.dist = dist;
    }

    public static void loggeNextbikeInfoListe(List<NextbikeInfo> nextbikeInfoList) {
        for (NextbikeInfo daten : nextbikeInfoList) {
            @SuppressLint("DefaultLocale") String logText = String.format(
                    "Index: %d, Name: %s, Ist es ein Fahhrad: %b, Ist es eine Station: %b, Anzahl Bikes: %s, Entfernung: %s",
                    daten.getIndex(),
                    daten.getName(),
                    daten.isBike(),
                    daten.isSpot(),
                    daten.getBikes(),
                    daten.getDist()
            );

            Log.d("Nextbike", logText);
        }
    }

    public static int getTotalBikeCount(List<NextbikeInfo> nextbikeInfoList) {
        int totalBikes = 0;

        for (NextbikeInfo daten : nextbikeInfoList) {
            totalBikes += daten.getBikes();
        }

        return totalBikes;
    }

    public static int getTotalStationCount(List<NextbikeInfo> nextbikeInfoList) {
        int totalStation = 0;

        for (NextbikeInfo daten : nextbikeInfoList) {
            if (daten.isSpot()) {
                totalStation += 1;
            }
        }

        return totalStation;
    }

    public static void leereNextbikeInfoList(List<NextbikeInfo> nextbikeInfoList) {
        nextbikeInfoList.clear();
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public boolean isBike() {
        return bike;
    }

    public boolean isSpot() {
        return spot;
    }

    public int getBikes() {
        return bikes;
    }

    public double getDist() {
        return dist;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBike(boolean bike) {
        this.bike = bike;
    }

    public void setSpot(boolean spot) {
        this.spot = spot;
    }

    public void setBikes(int bikes) {
        this.bikes = bikes;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }
}



