package com.coradia.mobilsoftware_projekt.objects;

import com.google.gson.annotations.SerializedName;

public class LocationProperties {

    @SerializedName("distance")
    public double distance;

    @SerializedName("stopId")
    public String stopID;

    public double getDistance() {
        return distance;
    }

    public String getStopID() { return stopID;}
}
