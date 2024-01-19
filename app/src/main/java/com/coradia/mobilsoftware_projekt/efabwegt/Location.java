package com.coradia.mobilsoftware_projekt.efabwegt;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("coord")
    public double[] coordinates;

    @SerializedName("productClasses")
    public int[] productClasses;

    @SerializedName("parent")
    public LocationParent parent;

    @SerializedName("properties")
    public LocationProperties properties;

    @SerializedName("assignedStops")
    public List<LocationAssignedStops> assignedStops;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double[] getCoord() {
        return coordinates;
    }

    public int[] getProductClasses() {
        return productClasses;
    }

    public LocationProperties getProperties() {
        return properties;
    }

    public List<LocationAssignedStops> getAssignedStops() {
        return assignedStops;
    }
}
