package com.sport.cricket.cricketapi.domain.common;



public class LiveScoreCard {

    private long striker;
    private long nonStriker;
    private long currentBowler;
    private long previousBowler;


    public long getStriker() {
        return striker;
    }

    public void setStriker(long striker) {
        this.striker = striker;
    }

    public long getNonStriker() {
        return nonStriker;
    }

    public void setNonStriker(long nonStriker) {
        this.nonStriker = nonStriker;
    }

    public long getCurrentBowler() {
        return currentBowler;
    }

    public void setCurrentBowler(long currentBowler) {
        this.currentBowler = currentBowler;
    }

    public long getPreviousBowler() {
        return previousBowler;
    }

    public void setPreviousBowler(long previousBowler) {
        this.previousBowler = previousBowler;
    }
}
