package com.coradia.mobilsoftware_projekt.network;

import com.coradia.mobilsoftware_projekt.nextbike.NextbikeResponse;
import com.coradia.mobilsoftware_projekt.objects.EfaCoordResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NextbikeApi {

    @GET("maps/nextbike-live.json?distance=1500&bike_distance%20=1500")
    Call<NextbikeResponse> loadBikesWithinRadius(@Query("lat") double latitude, @Query("lng") double longitude);
}
