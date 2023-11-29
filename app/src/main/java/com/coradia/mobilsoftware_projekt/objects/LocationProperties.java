package com.coradia.mobilsoftware_projekt.objects;

import com.google.gson.annotations.SerializedName;

public class LocationProperties {
    @SerializedName("distance")
    public double distance;

    public Double getDistance() {
        return distance;
    }
}