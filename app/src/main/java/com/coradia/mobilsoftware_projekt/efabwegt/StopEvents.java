package com.coradia.mobilsoftware_projekt.efabwegt;

import com.google.gson.annotations.SerializedName;

public class StopEvents {

    @SerializedName("departureTimePlanned")
    public String departureTimePlanned;

    public String getDepartureTimePlanned() {
        return departureTimePlanned;
    }
}
