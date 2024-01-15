package com.coradia.mobilsoftware_projekt.nextbike;

import com.google.gson.annotations.SerializedName;

public class Places {
    //Ist es ein Bike?
    @SerializedName("bike")
    public boolean bike;

    @SerializedName("name")
    public String nameNextbike;

    //Ist es ein Spot?
    @SerializedName("spot")
    public boolean spot;

    @SerializedName("bikes")
    public int bikes;

    @SerializedName("dist")
    public double dist;

    public boolean isBike() {
        return bike;
    }

    public String getNameNextbike() {
        return nameNextbike;
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
}
