package com.coradia.mobilsoftware_projekt.methods;

import java.util.List;

public class Calculator {

    //Notenrechner ÖPNV:
    //Notenrechner Haltestellendichte
    public double getGradeStopAmount(int stopAmount) {
        int stopamountnew = Math.min(stopAmount, 20);
        return 6 - 5.0 * stopamountnew / 20;
    }

    //Notenrechner Haltestellenabstand
    public double getGradeStopDistance(double stopDistance) {
        double grade = 1.0 / 150 * stopDistance - 2.0 / 3;
        return castGrade(grade);
    }

    //Notenrechner Abfahrten
    public double getGradeStopDeparture(double stopDeparture) {
        double grade = 6 - 5.0 * (stopDeparture) / (12);
        return castGrade(grade);
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

        //Ansatz für Präferenzen
        bahn = bahn * 4;
        schiene = schiene * 3;
        strasse = strasse * 2;
        ondemand = ondemand * 1;
        sonstige = sonstige * 0;

        int punkte = bahn + schiene + strasse + ondemand;

        double grade = 6 - 5.0 * (punkte) / (12);
        return castGrade(grade);
    }


    //Notenrechner Nextbikes:
    //Notenrechner Anzahl Fahrräder
    public double getGradeBikeAmount(int bikeAmount) {
        double grade = 6 - 5.0 * bikeAmount / 125;
        return castGrade(grade);
    }

    //Notenrechner Anzahl Station
    public double getGradeBikeStationAmount(int stationAmount) {
        double grade = 6 - 5.0 * stationAmount / 7;
        return castGrade(grade);
    }

    //Notenrechner Abstand Fahrrad/Station
    public double getGradeBikeDistance(double bikeDistance) {
        double grade = 1.0/190 * bikeDistance + 14.0/19;
        return castGrade(grade);
    }


    //Notenrechner Finale MobileScore Note
    public String getFinalGrade(List<StopInfo> stopInfoList, List<NextbikeInfo> nextbikeInfoList) {

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

            for (int j : productList) {
                productClasses[j] = 1;
            }
        }

        int bikeAmount = NextbikeInfo.getTotalBikeCount(nextbikeInfoList);
        int bikeStationAmount = NextbikeInfo.getTotalStationCount(nextbikeInfoList);

        double bikeDistance;
        if (nextbikeInfoList.size() != 0) {
            bikeDistance = nextbikeInfoList.get(0).getDist();
        } else {
            bikeDistance = Double.MAX_VALUE;
        }


        double bikegrade1 = getGradeBikeAmount(bikeAmount);
        double bikegrade2 = getGradeBikeStationAmount(bikeStationAmount);
        double bikegrade3 = getGradeBikeDistance(bikeDistance);
        double bikegrade = (bikegrade1 + bikegrade2 + bikegrade3) / 3;

        double grade1 = getGradeStopAmount(stopAmount);
        double grade2 = getGradeStopDistance(stopDistance);
        double grade3 = getGradeStopDeparture(stopDeparture);
        double grade4 = getGradeStopModules(productClasses);
        double putGrade = (grade1 + grade2 + grade3 + grade4) / 4;

        double finalGrade = Math.round((putGrade + bikegrade) * 2) / 4f;

        return finalGrade + ":" + finalGrade + ";" + putGrade + ";" + grade1 + "," + grade2 + "," + grade3 + "," + grade4 + ";" + bikegrade + ";" + bikegrade1 + "," + bikegrade2 + "," + bikegrade3;
    }

    public double castGrade(double grade) {
        if (grade < 1) {
            grade = 1;
        } else if (grade > 6) {
            grade = 6;
        }
        return grade;
    }
}