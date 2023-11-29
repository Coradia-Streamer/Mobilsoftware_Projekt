package com.coradia.mobilsoftware_projekt.objects;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("coord")
    public double[] coord;

    @SerializedName("productClasses")
    private int[] productClasses;

    @SerializedName("properties")
    private LocationProperties properties;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double[] getCoord() {
        return coord;
    }

    public int[] getProductClasses() {
        return productClasses;
    }

    public LocationProperties getProperties() {
        return properties;
    }
}