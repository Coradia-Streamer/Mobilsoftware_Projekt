package com.coradia.mobilsoftware_projekt.network;

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

    public double createLatitude(double latitude) {
        return latitude;

    }


    public NextbikeApi getClient() {
        return this.retrofit.create(NextbikeApi.class);
    }
}
