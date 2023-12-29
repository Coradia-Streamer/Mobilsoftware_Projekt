package com.coradia.mobilsoftware_projekt.nextbike;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Countries {
    @SerializedName("cities")
    public List<Cities> citiesList;

    public List<Cities> getCitiesList() {
        return citiesList;
    }
}
