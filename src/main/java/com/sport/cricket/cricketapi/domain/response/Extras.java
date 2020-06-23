package com.sport.cricket.cricketapi.domain.response;

public class Extras {
    private int total;
    private int byes;
    private int legbyes;
    private int wides;
    private int noballs;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getByes() {
        return byes;
    }

    public void setByes(int byes) {
        this.byes = byes;
    }

    public int getLegbyes() {
        return legbyes;
    }

    public void setLegbyes(int legbyes) {
        this.legbyes = legbyes;
    }

    public int getWides() {
        return wides;
    }

    public void setWides(int wides) {
        this.wides = wides;
    }

    public int getNoballs() {
        return noballs;
    }

    public void setNoballs(int noballs) {
        this.noballs = noballs;
    }


    @Override
    public String toString() {
        return "Extras{" +
                "total=" + total +
                ", byes=" + byes +
                ", legbyes=" + legbyes +
                ", wides=" + wides +
                ", noballs=" + noballs +
                '}';
    }
}
