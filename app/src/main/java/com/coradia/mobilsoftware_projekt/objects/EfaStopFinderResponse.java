package com.coradia.mobilsoftware_projekt.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EfaStopFinderResponse {

    @SerializedName("version")
    public String version;

    @SerializedName("locations")
    public List<Location> locations;

    public String getVersion() {
        return version;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
