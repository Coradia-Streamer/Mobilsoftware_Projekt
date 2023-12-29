package com.coradia.mobilsoftware_projekt.nextbike;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cities {
    @SerializedName("places")
    public List<Places> placesList;

    public List<Places> getPlacesList() {
        return placesList;
    }
}
