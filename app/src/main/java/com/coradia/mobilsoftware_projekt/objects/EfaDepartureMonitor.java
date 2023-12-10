package com.coradia.mobilsoftware_projekt.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EfaDepartureMonitor {


    @SerializedName("version")
    public String version;

    //@SerializedName("locations")
    //public List<Location> locations;

    @SerializedName("stopEvents")
    public List<StopEvents> stopEvents;


    public String getVersion() {
        return version;
    }

    /*public List<Location> getLocations() {
        return locations;
    }*/

    public List<StopEvents> getstopEvents() {
        return stopEvents;
    };
}

