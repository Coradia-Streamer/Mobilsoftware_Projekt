package com.coradia.mobilsoftware_projekt.network;

import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NextbikeApiClient {

    private static NextbikeApiClient instance;
    private final Retrofit retrofit;

    public static NextbikeApiClient getInstance() {
        if (instance == null) {
            instance = new NextbikeApiClient();
        }
        return instance;
    }

    public NextbikeApiClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nextbike.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public String createCoordinateString(double latitude, double longitude) {
        return String.format(Locale.ENGLISH, "%f:%f:WGS84[dd.ddddd]", longitude, latitude);
    }

    public double createLatitude(double latitude) {
        return latitude;

    }

    public double createLongitude(double longitude) {
        return longitude;
    }

    public NextbikeApi getClient() {
        return this.retrofit.create(NextbikeApi.class);
    }
}
