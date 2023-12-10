package com.coradia.mobilsoftware_projekt.network;

import com.coradia.mobilsoftware_projekt.objects.EfaCoordResponse;
import com.coradia.mobilsoftware_projekt.objects.EfaDepartureMonitor;
import com.coradia.mobilsoftware_projekt.objects.EfaStopFinderResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EfaApi {
    @GET("bwegt-efa/XML_COORD_REQUEST?commonMacro=coord&outputFormat=rapidJSON&coordOutputFormat=WGS84[dd.ddddd]&type_1=STOP&inclFilter=1")
    Call<EfaCoordResponse> loadStopsWithinRadius(@Query("coord") String coordinateString, @Query("radius_1") int radius);

    @GET("bwegt-efa/XML_STOPFINDER_REQUEST?commonMacro=stopfinder&outputFormat=rapidJSON&type_sf=any")
    Call<EfaStopFinderResponse> requestStation(@Query("name_sf") String StationID);

    @GET("bwegt-efa/XML_DM_REQUEST?commonMacro=dm&type_dm=any&mode=direct&outputFormat=rapidJSON&locationServerActive=1&useAllStops=1&lsShowTrainsExplicit=1&useProxFootSearch=0&limit=20")
    Call<EfaDepartureMonitor> requestDeparture(@Query("name_dm") String StationID);
}

