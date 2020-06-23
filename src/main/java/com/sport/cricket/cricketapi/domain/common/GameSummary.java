package com.sport.cricket.cricketapi.domain.common;

public class GameSummary {

    private Award playerOfMatch;
    private Award playerOfSeries;
    private String seriesResult;
    private String toss;


    public Award getPlayerOfMatch() {
        return playerOfMatch;
    }

    public void setPlayerOfMatch(Award playerOfMatch) {
        this.playerOfMatch = playerOfMatch;
    }

    public Award getPlayerOfSeries() {
        return playerOfSeries;
    }

    public void setPlayerOfSeries(Award playerOfSeries) {
        this.playerOfSeries = playerOfSeries;
    }

    public String getSeriesResult() {
        return seriesResult;
    }

    public void setSeriesResult(String seriesResult) {
        this.seriesResult = seriesResult;
    }

    public String getToss() {
        return toss;
    }

    public void setToss(String toss) {
        this.toss = toss;
    }
}
