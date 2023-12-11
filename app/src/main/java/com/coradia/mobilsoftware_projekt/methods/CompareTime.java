package com.coradia.mobilsoftware_projekt.methods;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CompareTime {

    public static String compareTime() {
        //Abfrage aktueller Zeit
        Date currentTime = Calendar.getInstance().getTime();
        Log.d("Time","Aktuelle Zeit: "+currentTime);

        //Umwandlung des Datumformates in ISO 8601 -> entspricht in etwa dem Datumsformat vom DepartureMonitor
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.GERMANY);
        String formattedCurrentTime = sdf.format(currentTime);
        Log.d("Time","ISO 8601 Zeit: " + formattedCurrentTime);


        //Erstellung Vergleichszeit (Vergleichszeit gibt Intervall vor)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);

        calendar.add(Calendar.HOUR, 0);//Angabe von zusätzlichen Stunden für die Vergleichszeit
        calendar.add(Calendar.MINUTE,30);//Angabe von zusätzlichen Minuten für die Vergleichszeit
        //-> bspw. Angabe von 0h und 30min bedeutet, dass 30 Minuten zur aktuellen Uhrzeit addiert werden

        //Umwandlung der Vergleichszeit in ISO 8601 -> entspricht in etwa dem Datumsformat vom DepartureMonitor
        Date compareTime = calendar.getTime();
        String formattedCompareTime = sdf.format(compareTime);
        Log.d("Time","Vergleichszeit ISO 8601:" +formattedCompareTime);

                /*
                Instant instant = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    instant = Instant.now();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    System.out.println("Instant: " + DateTimeFormatter.ISO_INSTANT.format(instant));
                }*/

        //Anpassung der ISO 8601 formattierten Vergleichszeit (Ersetzung von +0000 durch Z, notwendig damit .parse(...) funktioniert)
        String target = "+0000";
        String replacement = "Z";
        String ZformattedCompareTime = formattedCompareTime.replace(target,replacement);

        return ZformattedCompareTime;
    }
}
