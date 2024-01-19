package com.coradia.mobilsoftware_projekt.efabwegt;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationAssignedStops {


    @SerializedName("properties")
    public List<LocationAssignedStopsProperties> locationAssignedStopsProperties;

    public List<LocationAssignedStopsProperties> getLocationAssignedStopsProperties() {
        return locationAssignedStopsProperties;
    }
}