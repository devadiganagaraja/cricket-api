package com.sport.cricket.cricketapi.domain.response.home;

import com.cricketfoursix.cricketdomain.common.article.CricketArticle;
import com.sport.cricket.cricketapi.domain.response.league.LeagueDetails;
import com.sport.cricket.cricketapi.domain.response.season.LiveGameInfo;
import com.sport.cricket.cricketapi.domain.response.season.PostGameInfo;
import com.sport.cricket.cricketapi.domain.response.season.ScheduledGameInfo;

import java.util.ArrayList;
import java.util.List;

public class CricketHome {
    private List<LeagueDetails> tournaments = new ArrayList<>();
    private List<LeagueDetails> tours = new ArrayList<>();

    private List<PostGameInfo> postGameInfoList = new ArrayList<>();
    private List<ScheduledGameInfo> scheduledGameInfoList = new ArrayList<>();
    private List<LiveGameInfo> liveGameInfoList = new ArrayList<>();

    private List<CricketArticle> cricketArticles = new ArrayList<>();


    public List<CricketArticle> getCricketArticles() {
        return cricketArticles;
    }

    public void setCricketArticles(List<CricketArticle> cricketArticles) {
        this.cricketArticles = cricketArticles;
    }

    public List<LeagueDetails> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<LeagueDetails> tournaments) {
        this.tournaments = tournaments;
    }

    public List<LeagueDetails> getTours() {
        return tours;
    }

    public void setTours(List<LeagueDetails> tours) {
        this.tours = tours;
    }

    public List<PostGameInfo> getPostGameInfoList() {
        return postGameInfoList;
    }

    public void setPostGameInfoList(List<PostGameInfo> postGameInfoList) {
        this.postGameInfoList = postGameInfoList;
    }

    public List<ScheduledGameInfo> getScheduledGameInfoList() {
        return scheduledGameInfoList;
    }

    public void setScheduledGameInfoList(List<ScheduledGameInfo> scheduledGameInfoList) {
        this.scheduledGameInfoList = scheduledGameInfoList;
    }

    public List<LiveGameInfo> getLiveGameInfoList() {
        return liveGameInfoList;
    }

    public void setLiveGameInfoList(List<LiveGameInfo> liveGameInfoList) {
        this.liveGameInfoList = liveGameInfoList;
    }
}
