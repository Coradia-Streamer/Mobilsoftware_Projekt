package com.coradia.mobilsoftware_projekt;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.coradia.mobilsoftware_projekt.methods.Calculator;
import com.coradia.mobilsoftware_projekt.methods.CompareTime;
import com.coradia.mobilsoftware_projekt.methods.NextbikeInfo;
import com.coradia.mobilsoftware_projekt.methods.PopUp;
import com.coradia.mobilsoftware_projekt.methods.StopInfo;
import com.coradia.mobilsoftware_projekt.network.EfaApiClient;
import com.coradia.mobilsoftware_projekt.network.NextbikeApiClient;
import com.coradia.mobilsoftware_projekt.nextbike.Cities;
import com.coradia.mobilsoftware_projekt.nextbike.Countries;
import com.coradia.mobilsoftware_projekt.nextbike.NextbikeResponse;
import com.coradia.mobilsoftware_projekt.nextbike.Places;
import com.coradia.mobilsoftware_projekt.objects.EfaCoordResponse;
import com.coradia.mobilsoftware_projekt.objects.EfaDepartureMonitor;
import com.coradia.mobilsoftware_projekt.objects.Location;
import com.coradia.mobilsoftware_projekt.objects.LocationParent;
import com.coradia.mobilsoftware_projekt.objects.LocationProperties;
import com.coradia.mobilsoftware_projekt.objects.ProductClassMeaning;
import com.coradia.mobilsoftware_projekt.objects.StopEvents;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private final Context activityContext = MapActivity.this;
    private Intent starterIntent;
    private MapView mapView;
    private TextView textView;
    Boolean toggleProgress = FALSE;
    Boolean togglePermission = FALSE;
    boolean toggleFirst = FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedTheme = sharedPreferences.getInt("SelectedTheme", 0);
        setDynamicTheme(selectedTheme);

        setContentView(R.layout.activity_map);

        boolean togglePopSettings = sharedPreferences.getBoolean("togglePopSettings", false);
        if (togglePopSettings) {
            PopUp popUp = new PopUp();
            popUp.openPopUpWindow(findViewById(R.id.popUp_view), activityContext, sharedPreferences, "MapActivity");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("togglePopSettings", false);
        editor.apply();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Button mainButton = findViewById(R.id.main_button);
        Button mapButton = findViewById(R.id.map_button);
        Button detailsButton = findViewById(R.id.details_button);

        mapButton.setBackgroundColor(Color.parseColor("#EBD11C"));

        mainButton.setOnClickListener(view -> {
            Intent intent = new Intent(MapActivity.this,MainActivity.class);
            starterIntent = intent;
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        detailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MapActivity.this,DetailActivity.class);
            starterIntent = intent;
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        XYTileSource mapServer = new XYTileSource(
                "MapName",
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
        mapController.setZoom(17.0);
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
                if(!toggleFirst) {
                    movement();
                    toggleProgress = TRUE;
                    toggleFirst = TRUE;
                }
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                //movement();
                return false;
            }
        });
    }

    private void setDynamicTheme(int selectedTheme) {
        switch (selectedTheme) {
            case 0:
                MapActivity.this.setTheme(R.style.Theme_Mobilsoftware_Projekt);
                break;
            case 1:
                MapActivity.this.setTheme(R.style.LightTheme);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            PopUp popUp = new PopUp();
            popUp.openPopUpWindow(findViewById(R.id.popUp_view), activityContext, sharedPreferences, "MapActivity");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    Handler h = new Handler();
    Runnable r;
    int delay = 2*1000;
    double savedDistance = 0;
    boolean toggleInstance = FALSE;
    boolean toggleMove = FALSE;
    boolean toggleNoneMove = FALSE;
    GeoPoint reference;
    GeoPoint actual;

    @Override
    protected void onResume() {
        boolean toggleReCreate = sharedPreferences.getBoolean("mapCallReCreate", false);
        if (toggleReCreate && starterIntent != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("mapCallReCreate", false);
            editor.apply();
            Intent intentRe = new Intent(MapActivity.this,MapActivity.class);
            finish();
            startActivity(intentRe);
        } else if (starterIntent == null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("mapCallReCreate", false);
            editor.apply();
        }

        h.postDelayed(r = () -> {
            if (toggleFirst) {
                if (!toggleInstance) {
                    reference = new GeoPoint(mapView.getMapCenter());
                    Log.d("Runnable", "First execution of the distance calculator");
                    toggleInstance = TRUE;
                } else {
                    actual = new GeoPoint(mapView.getMapCenter());
                    double distance = distance(reference, actual);
                    distance = (double) Math.round(distance * 1000) / 1000;
                    savedDistance = savedDistance + distance;
                    Log.d("Runnable", "The distance is " + distance + " and the saved distance is " + savedDistance);
                    if (savedDistance > 0.5) {
                        toggleMove = TRUE;
                        toggleNoneMove = FALSE;
                        delay = 1000;
                    }
                    if (distance == 0) {
                        toggleNoneMove = TRUE;
                        delay = 2*1000;
                    }
                    reference = actual;
                }
                if (toggleMove && toggleNoneMove) {
                    toggleMove = FALSE;
                    savedDistance = 0;
                    movement();
                }
                //Log.d("Runnable", "I'm doing something");
            }
            h.postDelayed(r, delay);
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        h.removeCallbacks(r);
        super.onPause();
    }

    private Double distance(GeoPoint reference, GeoPoint actual) {
        double distance;
        Double dx;
        Double dy;
        double lat;

        lat = (reference.getLatitude() + actual.getLatitude()) / 2 * 0.01745;
        dx = 111.3 * Math.cos(lat) * (reference.getLongitude() - actual.getLongitude());
        dy = 111.3 * (reference.getLatitude() - actual.getLatitude());

        distance = Math.sqrt(dx * dx + dy * dy);

        return distance;
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

    /**
     * @noinspection SameParameterValue
     */
    private String getMapServerAuthorizationString(String username, String password) {
        String authorizationString = String.format("%s:%s", username, password);
        return "Basic " + Base64.encodeToString(authorizationString.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
    }

    //Erzeugung einer Liste mit allen Informationen zu einzelnen Haltstellen und zu den Nextbikes
    private final List<StopInfo> stopInfoList = new ArrayList<>();
    private final List<NextbikeInfo> nextbikeInfoList = new ArrayList<>();

    //Erzeugung einer Liste mit allen Abfahrtszeiten einer Haltestelle
    private final List<String> stopDepartureList = new ArrayList<>();

    private void movement() {
        loadClosestStops(mapView.getMapCenter().getLatitude(), mapView.getMapCenter().getLongitude());
    }

    //Abruf aller Haltestellen in einem bestimmten Radius
    private void loadClosestStops(double latitude, double longitude) {
        textView.setText("Lade Haltestellen");
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
                        1500
                );

        efaCall.enqueue(new Callback<EfaCoordResponse>() {
            @Override
            public void onResponse(@NonNull Call<EfaCoordResponse> call, @NonNull Response<EfaCoordResponse> response) {
                Log.d("MapActivity", String.format("Response %d Locations", Objects.requireNonNull(response.body()).getLocations().size()));
                List<Location> locations = response.body().getLocations();

                //Leerung der StopInfoList bei jedem Aufruf, damit bei Standortänderung die alten Einträge gelöscht werden
                StopInfo.leereStopInfoList(stopInfoList);
                if (stopInfoList.isEmpty()) {
                    Log.d("MapActivity", "StopInfoListe geleert");
                } else
                    Log.d("MapActivity", "StopInfoListe: Leerung schlug fehl!");

                //Aufrufen und Abspeichern von Haltestellenname, Entfernung (in m), Stadtname, productClasses (Verkehrsmittel), StationsID und Abfahrten innerhalb eines bestimmten Intervalls für jede Haltestelle
                for (int i = 0; i < locations.size(); i++) {
                    Location location = locations.get(i);
                    String haltestellenName = location.getName();

                    LocationProperties distance = location.properties;
                    double entfernung = distance.getDistance();

                    LocationParent locationName = location.parent;
                    String stadtName = locationName.getName();

                    int[] productClasses = location.getProductClasses();

                    //Umwandlung der productClass Zahlen in tatsächliche Verkehrsmittel
                    StringBuilder productClassesString = new StringBuilder();
                    for (int classValue : productClasses) {
                        String classMeaning = ProductClassMeaning.getClassMeaning(classValue);
                        productClassesString.append(classMeaning).append(", ");
                    }

                    if (productClassesString.length() > 0) {
                        productClassesString.setLength(productClassesString.length() - 2);
                    }

                    String stationId = location.getId();
                    int departures = 0; //Die Anzahl der Abfahrten wird später separat abgefragt

                    //Abspeichern aller Informationen in der StopInfoList
                    StopInfo stopInfo = new StopInfo(i, stadtName, haltestellenName, entfernung, productClassesString.toString(), stationId, departures, productClasses);
                    stopInfoList.add(stopInfo);
                }


                //Abfrage der Abfahrten
                for (StopInfo daten : stopInfoList) {
                    String stationId = daten.getStationId();
                    requestDeparture(stationId);
                }


                StopInfo.loggeStopInfoListe(stopInfoList);

                loadNextbikeApi(mapView.getMapCenter().getLatitude(), mapView.getMapCenter().getLongitude());



            }


            @Override
            public void onFailure(@NonNull Call<EfaCoordResponse> call, @NonNull Throwable t) {
                Log.e("MapActivity", "Failure");
            }
        });
    }

    private void leereStopDepartureList() {
        stopDepartureList.clear();
    }

    //Abruf aller Abfahrten einer Haltestelle in einem bestimmten Intervall
    private void requestDeparture(String stationID) {

        textView.setText("Lade Abfahrten");
        Call<EfaDepartureMonitor> efaCall = EfaApiClient
                .getInstance()
                .getClient()
                .requestDeparture(stationID);

        efaCall.enqueue(new Callback<EfaDepartureMonitor>() {
            @Override
            public void onResponse(@NonNull Call<EfaDepartureMonitor> call, @NonNull Response<EfaDepartureMonitor> response) {
                Log.d("MapActivity", String.format("Es folgen die Abfahrten der Haltestelle %s", stationID));

                //Leerung der DepartureList bei jedem Aufruf, damit nur die Abfahrten einer einzigen Haltestelle gespeichert werden
                leereStopDepartureList();

                String zFormattedCompareTime = CompareTime.compareTime();
                Log.d("ZformattedCompareTime", zFormattedCompareTime);

                if (Objects.requireNonNull(response.body()).getstopEvents() == null) {
                    Log.d("MapActivity", "keine Abfahrten");
                } else {

                    Log.d("MapActivity", String.format("Response %d Departures", response.body().getstopEvents().size()));
                    List<StopEvents> stopEvents = response.body().getstopEvents();

                    //Abruf und Abspeichern aller Abfahrten an einer Haltestelle
                    for (int i = 0; i < stopEvents.size(); i++) {
                        StopEvents stopEvent = stopEvents.get(i);
                        String departureTimePlanned = stopEvent.getDepartureTimePlanned();

                        Instant instantCompare = Instant.parse(zFormattedCompareTime);
                        Instant instantDeparture = Instant.parse(departureTimePlanned);

                        if (instantCompare.isAfter(instantDeparture)) {
                            Log.d("MapActivity", String.format("Die Abfahrt ist um %s und somit vor %s", departureTimePlanned, zFormattedCompareTime));
                            stopDepartureList.add(departureTimePlanned);
                        }


                    }
                }

                //if (stopInfoList.isEmpty())

                //Abspeichern der ANZAHL aller Abfahrten an einer Haltestelle in die StopInfoList zur passenden Haltestelle
                for (StopInfo stopInfo : stopInfoList) {
                    if (stopInfo.getStationId() != null) {
                        if (stopInfo.getStationId().equals(stationID)) {
                            int departures = stopDepartureList.size();
                            Log.d("MapActivity", String.format("Die Haltestelle %s hat %s Abfahrten", stationID, departures));
                            stopInfo.setDepartures(departures);

                            //Loggen der kompletten StopInfoList nachdem allen Haltestellen die Anzahl der Abfahrten hinzugefügt worden sind
                            int lastindex = stopInfoList.size() - 1;
                            StopInfo stopInfo1 = stopInfoList.get(lastindex);
                            Log.d("MapActivity", "Letzte Station ID: " + stopInfo1.getStationId());
                            if (Objects.equals(stopInfo1.getStationId(), stationID)) {

                                StopInfo.loggeStopInfoListe(stopInfoList);

                                Calculator calculator = new Calculator();
                                String scoreDetails = calculator.getFinalGrade(stopInfoList, nextbikeInfoList, sharedPreferences);
                                StringTokenizer st = new StringTokenizer(scoreDetails, ":");
                                textView.setText(st.nextToken());

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("toggleDetails", true);
                                editor.putString("scoreDetails", st.nextToken());
                                editor.apply();
                            }

                        }
                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<EfaDepartureMonitor> call, @NonNull Throwable t) {
                Log.e("MapActivity", "Fehler beim Aufrufen der API", t);
            }
        });
    }

    //Abruf NextbikeAPI
    private void loadNextbikeApi(double latitude, double longitude) {
        textView.setText("Lade Nextbike Stationen");
        Call<NextbikeResponse> nextbikeResponseCall = NextbikeApiClient
                .getInstance()
                .getClient()
                .loadBikesWithinRadius(
                        NextbikeApiClient
                                .getInstance()
                                .createLatitude(latitude), longitude
                );

        nextbikeResponseCall.enqueue(new Callback<NextbikeResponse>() {



            @Override
            public void onResponse(@NonNull Call<NextbikeResponse> call, @NonNull Response<NextbikeResponse> response) {
                Log.d("Nextbikes", String.format("Response %d", Objects.requireNonNull(response.body()).getCountriesList().size()));
                List<Countries> countries = response.body().getCountriesList();


                //Leerung der StopInfoList bei jedem Aufruf, damit bei Standortänderung die alten Einträge gelöscht werden
                NextbikeInfo.leereNextbikeInfoList(nextbikeInfoList);
                if (nextbikeInfoList.isEmpty()) {
                    Log.d("MapActivity", "NextbikeInfoListe geleert");
                } else
                    Log.d("MapActivity", "NextbikeInfoListe: Leerung schlug fehl!");


                //Aufrufen und Abspeichern von Haltestellenname, Entfernung (in m), Stadtname, productClasses (Verkehrsmittel), StationsID und Abfahrten innerhalb eines bestimmten Intervalls für jede Haltestelle
                for (int i = 0; i < countries.size(); i++) {
                    Countries country = countries.get(i);

                    List<Cities> citiesList = country.getCitiesList();

                    for (int j = 0; j < citiesList.size(); j++) {
                        Cities city = citiesList.get(j);

                        List<Places> placesList = city.getPlacesList();

                        for (int k = 0; k < placesList.size(); k++) {
                            Places places = placesList.get(k);

                            boolean bike = places.isBike();
                            String name = places.getNameNextbike();
                            boolean spot = places.isSpot();
                            int bikes = places.getBikes();
                            double distance = places.getDist();

                            //Abspeichern aller Informationen in der NextbikeInfoListe
                            NextbikeInfo nextbikeInfo = new NextbikeInfo(k, name, bike, spot, bikes, distance);
                            nextbikeInfoList.add(nextbikeInfo);

                        }

                    }
                }

                NextbikeInfo.loggeNextbikeInfoListe(nextbikeInfoList);



            }




            @Override
            public void onFailure(@NonNull Call<NextbikeResponse> call, @NonNull Throwable t) {
                Log.e("Nextbike", "NextbikeAPI fehlgeschlagen");
            }
        });
    }
}