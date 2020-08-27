package com.sport.cricket.cricketapi.domain.response.season;

import com.cricketfoursix.cricketdomain.common.game.GameStatus;

import java.util.Date;

public class LiveGameInfo {

    private long gameId;
    private String team1Name;
    private String team2Name;
    private String ClassType;
    private String team1Score;
    private String team2Score;
    private GameStatus gameStatus;
    private String dateStr;
    private Date gameDate;
    private String note;


    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getClassType() {
        return ClassType;
    }

    public void setClassType(String classType) {
        ClassType = classType;
    }

    public String getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(String team1Score) {
        this.team1Score = team1Score;
    }

    public String getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(String team2Score) {
        this.team2Score = team2Score;
    }


    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
