package com.sport.cricket.cricketapi.domain.response;

public class BowlingScoreLeaf {

    private String playerName;
    private int wickets;
    private int runs;
    private String overs;
    private int maidens;
    private String economyRate;

    private long playerId;


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public String getOvers() {
        return overs;
    }

    public void setOvers(String overs) {
        this.overs = overs;
    }

    public int getMaidens() {
        return maidens;
    }

    public void setMaidens(int maidens) {
        this.maidens = maidens;
    }

    public String getEconomyRate() {
        return economyRate;
    }

    public void setEconomyRate(String economyRate) {
        this.economyRate = economyRate;
    }

    @Override
    public String toString() {
        return "BowlingScoreLeaf{" +
                "playerName='" + playerName + '\'' +
                ", wickets=" + wickets +
                ", runs=" + runs +
                ", overs='" + overs + '\'' +
                ", maidens=" + maidens +
                ", economyRate='" + economyRate + '\'' +
                '}';
    }
}
