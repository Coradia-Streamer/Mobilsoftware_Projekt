package com.coradia.mobilsoftware_projekt.objects;

import java.util.HashMap;
import java.util.Map;


public class ProductClassMeaning {

    //Die einzelnen Vekehrsmittel werden unter ProductClass angegeben. Dabei werden Zahlen verwendet, die jeweils für ein Verkehrsmittel stehen. Hier werden die einzelnen Zahlen dem tatsächlichen Verkehrsmittel zugeordnet.
    //Die Bedeutung der einzelnen Zahlen werden unter anderem beim DepartureMonitor mitgeliefert: https://www.bwegt.de/bwegt-efa/XML_DM_REQUEST?commonMacro=dm&typ%20e_dm=any&type_dm=any&name_dm=7003104&mode=direct&limit=1 -> siehe Abschnitt "excludedMeans"
    private static final Map<Integer, String> classMeaning = new HashMap<>();

    static {

        classMeaning.put(0, "Zug");
        classMeaning.put(1, "S-Bahn");
        classMeaning.put(2, "U-Bahn");
        classMeaning.put(3, "Stadtbahn");
        classMeaning.put(4, "Straßenbahn");
        classMeaning.put(5, "Stadtbus");
        classMeaning.put(6, "Regionalbus");
        classMeaning.put(7, "Schnellbus");
        classMeaning.put(8, "Seil-/Zahnradbahn");
        classMeaning.put(9, "Schiff");
        classMeaning.put(10,"Anruf-Linien-Taxi/Rufbus");
        classMeaning.put(11,"Sonstiges");
        classMeaning.put(12,"Flugzeug");
        classMeaning.put(13,"Zug (Nahverkehr");
        classMeaning.put(14,"Zug (Fernverkehr)");
        classMeaning.put(15,"Zug Fernv m Zuschlag");
        classMeaning.put(16,"Zug Fernv m spez Fpr");
        classMeaning.put(17,"SEV");
        classMeaning.put(18,"Zug Shuttle");
        classMeaning.put(19,"Bürgerbus");
    }

    public static String getClassMeaning(int classValue) {
        return classMeaning.getOrDefault(classValue, "Unbekannt");
    }
}
