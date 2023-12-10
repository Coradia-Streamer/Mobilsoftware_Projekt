package com.coradia.mobilsoftware_projekt.objects;

import com.google.gson.annotations.SerializedName;

public class StopEvents {

    @SerializedName("departureTimePlanned")
    public String departureTimePlanned;

    public String getDepartureTimePlanned() {
        return departureTimePlanned;
    }
}
