package com.sport.cricket.cricketapi.domain.response;


import com.cricketfoursix.cricketdomain.common.game.ManOfTheMatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
    private Long id;
    private  long leagueId;
    private String name;
    private  String leagueName;
    private Integer season;
    private Date date;
    private String gameClass;
    private String gameNote;
    private String team1Name;
    private String team2Name;
    private List<String> team1Score = new ArrayList<>();
    private List<String> team2Score = new ArrayList<>();
    private String gameStatus;
    private String toss;
    private String venue;
    private ManOfTheMatch manOfTheMatch;
    private List<ScoreCard> scoreCards;
    private List<Leader> leaders = new ArrayList<>();
    private LiveScore liveScore = new LiveScore();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToss() {
        return toss;
    }

    public void setToss(String toss) {
        this.toss = toss;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public ManOfTheMatch getManOfTheMatch() {
        return manOfTheMatch;
    }

    public void setManOfTheMatch(ManOfTheMatch manOfTheMatch) {
        this.manOfTheMatch = manOfTheMatch;
    }

    public List<ScoreCard> getScoreCards() {
        return scoreCards;
    }

    public void setScoreCards(List<ScoreCard> scoreCards) {
        this.scoreCards = scoreCards;
    }

    private List<String> team1Overs = new ArrayList<>();
    private List<String> team2Overs = new ArrayList<>();

    public List<Leader> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<Leader> leaders) {
        this.leaders = leaders;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getGameClass() {
        return gameClass;
    }

    public void setGameClass(String gameClass) {
        this.gameClass = gameClass;
    }

    public String getGameNote() {
        return gameNote;
    }

    public void setGameNote(String gameNote) {
        this.gameNote = gameNote;
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


    public List<String> getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(List<String> team1Score) {
        this.team1Score = team1Score;
    }

    public List<String> getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(List<String> team2Score) {
        this.team2Score = team2Score;
    }

    public List<String> getTeam1Overs() {
        return team1Overs;
    }

    public void setTeam1Overs(List<String> team1Overs) {
        this.team1Overs = team1Overs;
    }

    public List<String> getTeam2Overs() {
        return team2Overs;
    }

    public void setTeam2Overs(List<String> team2Overs) {
        this.team2Overs = team2Overs;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public LiveScore getLiveScore() {
        return liveScore;
    }

    public void setLiveScore(LiveScore liveScore) {
        this.liveScore = liveScore;
    }
}
