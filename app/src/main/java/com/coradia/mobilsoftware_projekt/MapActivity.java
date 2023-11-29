package com.coradia.mobilsoftware_projekt;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.coradia.mobilsoftware_projekt.network.EfaApiClient;
import com.coradia.mobilsoftware_projekt.objects.EfaCoordResponse;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private TextView textView;
    Boolean toggleProgress = FALSE;
    Boolean togglePermission = FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        XYTileSource mapServer = new XYTileSource("MapServer",
                8,
                20,
                256,
                ".png",
                new String[]{"https://tileserver.svprod01.app/styles/default/"}
        );
        String authorizationString = this.getMapServerAuthorizationString(
                "ws2223@hka",
                "LeevwBfDi#2027"
        );
        Configuration
                .getInstance()
                .getAdditionalHttpRequestProperties()
                .put("Authorization", authorizationString);

        textView = findViewById(R.id.score);
        mapView = this.findViewById(R.id.testMap);
        mapView.setTileSource(mapServer);
        // GeoPoint startPoint = new GeoPoint(49.0069, 8.4037);
        IMapController mapController = mapView.getController();
        mapController.setZoom(14.0);
        // mapController.setCenter(startPoint);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        toggleProgress = FALSE;
        progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            while (!toggleProgress) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            progressBar.setVisibility(View.INVISIBLE);
        }).start();

        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                togglePermission = TRUE;
                initialiseLocation();
                Log.i("TAG", "onGranted: executed");
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                togglePermission = FALSE;
                Log.i("TAG", "onDenied: executed");
            }
        });

        this.mapView.addMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                movement();
                toggleProgress = TRUE;
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                //movement();
                return false;
            }
        });
    }

    private void movement() {
        loadClosestStops(mapView.getMapCenter().getLatitude(), mapView.getMapCenter().getLongitude());
    }

    private void loadClosestStops(Double latitude, Double longitude) {
        Call<EfaCoordResponse> efaCall = EfaApiClient
                .getInstance()
                .getClient()
                .loadStopsWithinRadius(
                        EfaApiClient
                                .getInstance()
                                .createCoordinateString(
                                        latitude,
                                        longitude
                                ),
                        400
                );

        efaCall.enqueue(new Callback<EfaCoordResponse>() {
            @Override
            public void onResponse(@NonNull Call<EfaCoordResponse> call, @NonNull Response<EfaCoordResponse> response) {
                Log.d("MapActivity", String.format("Response %d Locations", Objects.requireNonNull(response.body()).getLocations().size()));
                String[] locations = new String[response.body().getLocations().size()];
                for (int i = 0; i < response.body().getLocations().size(); i++) {
                    locations[i] = response.body().getLocations().get(i).name;
                }
                String locationNames = Arrays.toString(locations);
                Log.d("MapActivity", locationNames);
                int size = response.body().getLocations().size();
                if (size == 0) {
                    textView.setText(R.string.bad);
                } else if (size == 1) {
                    textView.setText(R.string.acceptable);
                } else if (size == 2 || size == 3) {
                    textView.setText(R.string.good);
                } else textView.setText(R.string.extraordinary);
            }

            @Override
            public void onFailure(@NonNull Call<EfaCoordResponse> call, @NonNull Throwable t) {
                Log.d("MapActivity", "Failure");
            }
        });
    }

    public void initialiseLocation() {
        LocationListener locationListener = location -> {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            GeoPoint startPoint = new GeoPoint(latitude, longitude);

            IMapController mapController = mapView.getController();
            mapController.setCenter(startPoint);
        };

        LocationManager locationManager = (LocationManager) this.getSystemService(
                Context.LOCATION_SERVICE
        );

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                2000,
                10,
                locationListener
        );
    }

    /** @noinspection SameParameterValue*/
    private String getMapServerAuthorizationString(String username, String password) {
        String authorizationString = String.format("%s:%s", username, password);
        return "Basic " + Base64.encodeToString(authorizationString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
    }
}