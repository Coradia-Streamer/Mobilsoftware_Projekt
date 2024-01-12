package com.coradia.mobilsoftware_projekt.methods;

import java.util.List;

public class Calculator {


    double gradeStopAmount;

    double gradeStopDistance;

    double gradeStopDeparture;

    double gradeStopModules;

    double gradeBikeAmount;

    double gradeBikeStationAmount;

    double gradeBikeDistance;

    double finalGrade;

    //Notenrechner ÖPNV:
    //Notenrechner Haltestellendichte
    public double getGradeStopAmount(int stopAmount) {

        int stopamountnew;

        if (stopAmount > 20) {
            stopamountnew = 20;
        } else {
            stopamountnew = stopAmount;
        }

        double grade = 6 - 5.0 * stopamountnew / 20;
        return grade;
    }

    //Notenrechner Haltestellenabstand
    public double getGradeStopDistance(double stopDistance) {

        double grade = 1.0 / 150 * stopDistance - 2.0 / 3;

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
        double grade = 6 - 5.0 * (stopDeparture) / (12);

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

        double grade = 6 - 5.0 * (punkte) / (12);

        if (grade < 1) {
            grade = 1;
        }

        return grade;


    }

    //Notenrechner Nextbikes:
    //Notenrechner Anzahl Fahrräder
    public double getGradeBikeAmount(int bikeAmount) {

        double grade = 6 - 5.0 * bikeAmount / 125;

        if (grade < 1) {
            grade = 1;
        } else if (grade > 6) {
            grade = 6;

        }

        return grade;
    }


    //Notenrechner Anzahl Station
    public double getGradeBikeStationAmount(int stationAmount) {

        double grade = 6 - 5.0 * stationAmount / 7;

        if (grade < 1) {
            grade = 1;
        } else if (grade > 6) {
            grade = 6;

        }

        return grade;
    }

    //Notenrechner Abstand Fahrrad/Station
    public double getGradeBikeDistance(double bikeDistance) {

        double grade = 1.0/190 * bikeDistance + 14.0/19;

        if (grade < 1) {
            grade = 1;
        } else if (grade > 6) {
            grade = 6;

        }

        return grade;
    }

    public double getGradeBikeComplete(List<NextbikeInfo> nextbikeInfoList) {

        int bikeAmount = NextbikeInfo.getTotalBikeCount(nextbikeInfoList);
        int bikeStationAmount = NextbikeInfo.getTotalStationCount(nextbikeInfoList);
        double bikeDistance = nextbikeInfoList.get(0).getDist();

        double bikegrade = (getGradeBikeAmount(bikeAmount) + getGradeBikeStationAmount(bikeStationAmount) + getGradeBikeDistance(bikeDistance)) / 3;

        double bikegrade1 = getGradeBikeAmount(bikeAmount);
        double bikegrade2 = getGradeBikeStationAmount(bikeStationAmount);
        double bikegrade3 = getGradeBikeDistance(bikeDistance);

        return bikegrade;
    }




    public double getFinalGrade(List<StopInfo> stopInfoList, List<NextbikeInfo> nextbikeInfoList) {

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


        double putGrade = (getGradeStopAmount(stopAmount) + getGradeStopDistance(stopDistance) + getGradeStopDeparture(stopDeparture) + getGradeStopModules(productClasses)) / 4;

        int bikeAmount = NextbikeInfo.getTotalBikeCount(nextbikeInfoList);
        int bikeStationAmount = NextbikeInfo.getTotalStationCount(nextbikeInfoList);

        double bikeDistance;
        if (nextbikeInfoList.size() != 0) {
            bikeDistance = nextbikeInfoList.get(0).getDist();
        } else {
            bikeDistance = Double.MAX_VALUE;
        }

        double bikegrade = (getGradeBikeAmount(bikeAmount) + getGradeBikeStationAmount(bikeStationAmount) + getGradeBikeDistance(bikeDistance)) / 3;

        double bikegrade1 = getGradeBikeAmount(bikeAmount);
        double bikegrade2 = getGradeBikeStationAmount(bikeStationAmount);
        double bikegrade3 = getGradeBikeDistance(bikeDistance);

        double grade1 = getGradeStopAmount(stopAmount);
        double grade2 = getGradeStopDistance(stopDistance);
        double grade3 = getGradeStopDeparture(stopDeparture);
        double grade4 = getGradeStopModules(productClasses);

        double grade = (putGrade + bikegrade) / 2;

        grade = Math.round(grade * 4) / 4f;

        return grade;
    }


}
