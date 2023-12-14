package com.coradia.mobilsoftware_projekt.methods;

import java.util.ArrayList;
import java.util.List;

public class Calculater {


    double gradeStopAmount;

    double gradeStopDistance;

    double gradeStopDeparture;

    double gradeStopModules;

    double finalGrade;

    //Notenrechner Haltestellendichte
    public double getGradeStopAmount(int stopAmount) {

        int stopamountnew;

        if (stopAmount > 20) {
            stopamountnew = 20;
        } else {
            stopamountnew = stopAmount;
        }

        double grade = 6 - 5 * stopamountnew / 20;
        return grade;
    }

    //Notenrechner Haltestellenabstand
    public double getGradeStopDistance(double stopDistance) {

        double grade = 1 / 150 * stopDistance - 2 / 3;

        if (grade < 1) {
            grade = 1;
        } else if (grade > 6) {
            grade = 6;

        }

        return grade;

    }

    //Notenrechner Abfahrten
    public double getGradeStopDeparture(double stopDeparture) {

        //WICHTIG: ANZAHL DER HALTESTELLEN AUF MITTELWERT VORHER BRINGEN!!!!!!!!!
        double grade = 6 - 5 * (stopDeparture) / (12);

        if (grade < 1) {
            grade = 1;
        }

        return grade;

    }

    //Notenrechner Verkehrsmittel
    public double getGradeStopModules(int[] productClasses) {

        int bahn = 0;
        int schiene = 0;
        int strasse = 0;
        int ondemand = 0;
        int sonstige = 0;

        for (int i = 0; i < productClasses.length; i++) {
            if (i == 0 && productClasses[i] == 1 || i == 1 && productClasses[i] == 1 || i == 13 && productClasses[i] == 1 || i == 14 && productClasses[i] == 1 || i == 15 && productClasses[i] == 1 || i == 16 && productClasses[i] == 1 || i == 17 && productClasses[i] == 1 || i == 18 && productClasses[i] == 1) {
                bahn++;
            } else if (i == 2 && productClasses[i] == 1 || i == 3 && productClasses[i] == 1 || i == 4 && productClasses[i] == 1 || i == 8 && productClasses[i] == 1) {
                schiene++;
            } else if (i == 5 && productClasses[i] == 1 || i == 6 && productClasses[i] == 1 || i == 7 && productClasses[i] == 1 || i == 9 && productClasses[i] == 1 || i == 19) {
                strasse++;
            } else if (i == 10 && productClasses[i] == 1) {
                ondemand++;
            } else {
                sonstige++;
            }
        }


        bahn = bahn * 4;
        schiene = schiene * 3;
        strasse = strasse * 2;
        ondemand = ondemand * 1;
        sonstige = sonstige * 0;

        int punkte = bahn + schiene + strasse + ondemand;

        double grade = 6 - 5 * (punkte) / (12);

        if (grade < 1) {
            grade = 1;
        }

        return grade;


    }

    public double getFinalGrade(List<StopInfo> stopInfoList) {

        int stopAmount = stopInfoList.size();
        double stopDistance = stopInfoList.get(0).getEntfernung();

        double stopDeparture = 0;

        for (int i = 0; i < stopInfoList.size(); i++) {
            stopDeparture = stopDeparture + stopInfoList.get(i).getDepartures();
        }

        stopDeparture = stopDeparture / stopInfoList.size() * 1 / 2;

        int[] productClasses = new int[20];


        for (int i = 0; i < stopInfoList.size(); i++) {
            int[] productList = stopInfoList.get(i).getProductClasses();

            for (int j = 0; j < productList.length; j++) {
                productClasses[productList[j]] = 1;


            }

        }

        double grade = (getGradeStopAmount(stopAmount) + getGradeStopDistance(stopDistance) + getGradeStopDeparture(stopDeparture) + getGradeStopModules(productClasses)) / 4;

        double grade1 = getGradeStopAmount(stopAmount);
        double grade2 = getGradeStopDistance(stopDistance);
        double grade3 = getGradeStopDeparture(stopDeparture);
        double grade4 = getGradeStopModules(productClasses);

        grade = Math.round(grade * 4) / 4f;

        return grade;
    }


}
