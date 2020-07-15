package com.sport.cricket.cricketapi.domain.response;

public class BattingScoreLeaf {
    private String playerName;
    private long playerId;
    private String dismissalText;
    private int runs;
    private int balls;
    private int fours;
    private int sixes;
    private String strikeRate;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getDismissalText() {
        return dismissalText;
    }

    public void setDismissalText(String dismissalText) {
        this.dismissalText = dismissalText;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getBalls() {
        return balls;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public int getFours() {
        return fours;
    }

    public void setFours(int fours) {
        this.fours = fours;
    }

    public int getSixes() {
        return sixes;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }

    public String getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(String strikeRate) {
        this.strikeRate = strikeRate;
    }

    @Override
    public String toString() {
        return "BattingScoreLeaf{" +
                "playerName='" + playerName + '\'' +
                ", dismissalText='" + dismissalText + '\'' +
                ", runs=" + runs +
                ", balls=" + balls +
                ", fours=" + fours +
                ", sixes=" + sixes +
                ", strikeRate='" + strikeRate + '\'' +
                '}';
    }
}
