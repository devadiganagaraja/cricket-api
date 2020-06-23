package com.sport.cricket.cricketapi.domain.common;

import java.util.Date;

public class GameInfo {
    private String name;
    private long leagueId;
    private String leagueName;
    private int season;
    private Date date;
    private Date endDate;
    private GameStatus gameStatus;
    private String venue;

    public String getToss() {
        return toss;
    }

    public void setToss(String toss) {
        this.toss = toss;
    }


    private String note;
    private String toss;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(long leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
