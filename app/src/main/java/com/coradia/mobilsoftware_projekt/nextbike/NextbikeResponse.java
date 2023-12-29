package com.coradia.mobilsoftware_projekt.nextbike;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NextbikeResponse {
    //Zählen von Spots -> schaue ob spot = true
    //Zählen von Bikes -> "bikes" -> Anzahl

    @SerializedName("countries")
    public List<Countries> countriesList;

    public List<Countries> getCountriesList() {
        return countriesList;
    }


}
