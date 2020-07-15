package com.sport.cricket.cricketapi.domain.source;

public class InningsInfo {
    private int runs;
    private int wickets;
    private String overs;
    private String overLimit;
    private String runRate;
    private int target;
    private int legByes;
    private int byes;
    private int noBalls;
    private int wides;
    private int lead;
    private long battingTeamId;
    private String battingTeamName;
    private boolean liveInnings;
    private String inningsName;

    public int getExtras() {
        return extras;
    }

    public void setExtras(int extras) {
        this.extras = extras;
    }

    private int extras;

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public String getOvers() {
        return overs;
    }

    public void setOvers(String overs) {
        this.overs = overs;
    }

    public String getOverLimit() {
        return overLimit;
    }

    public void setOverLimit(String overLimit) {
        this.overLimit = overLimit;
    }

    public String getRunRate() {
        return runRate;
    }

    public void setRunRate(String runRate) {
        this.runRate = runRate;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getLegByes() {
        return legByes;
    }

    public void setLegByes(int legByes) {
        this.legByes = legByes;
    }

    public String getInningsName() {
        return inningsName;
    }

    public void setInningsName(String inningsName) {
        this.inningsName = inningsName;
    }

    public int getByes() {
        return byes;
    }

    public void setByes(int byes) {
        this.byes = byes;
    }

    public int getNoBalls() {
        return noBalls;
    }

    public void setNoBalls(int noBalls) {
        this.noBalls = noBalls;
    }

    public int getWides() {
        return wides;
    }

    public void setWides(int wides) {
        this.wides = wides;
    }

    public int getLead() {
        return lead;
    }

    public void setLead(int lead) {
        this.lead = lead;
    }

    public long getBattingTeamId() {
        return battingTeamId;
    }

    public void setBattingTeamId(long battingTeamId) {
        this.battingTeamId = battingTeamId;
    }

    public String getBattingTeamName() {
        return battingTeamName;
    }

    public void setBattingTeamName(String battingTeamName) {
        this.battingTeamName = battingTeamName;
    }

    public boolean isLiveInnings() {
        return liveInnings;
    }

    public void setLiveInnings(boolean liveInnings) {
        this.liveInnings = liveInnings;
    }
}
