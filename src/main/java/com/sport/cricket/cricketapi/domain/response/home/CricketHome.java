package com.sport.cricket.cricketapi.domain.response.home;

import com.sport.cricket.cricketapi.domain.response.league.LeagueDetails;
import com.sport.cricket.cricketapi.domain.response.season.LiveGameInfo;
import com.sport.cricket.cricketapi.domain.response.season.PostGameInfo;
import com.sport.cricket.cricketapi.domain.response.season.PreGameInfo;

import java.util.ArrayList;
import java.util.List;

public class CricketHome {
    private List<LeagueDetails> tournaments = new ArrayList<>();
    private List<LeagueDetails> tours = new ArrayList<>();

    private List<PostGameInfo> postGameInfoList = new ArrayList<>();
    private List<PreGameInfo> preGameInfoList = new ArrayList<>();
    private List<LiveGameInfo> liveGameInfoList = new ArrayList<>();

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

    public List<PreGameInfo> getPreGameInfoList() {
        return preGameInfoList;
    }

    public void setPreGameInfoList(List<PreGameInfo> preGameInfoList) {
        this.preGameInfoList = preGameInfoList;
    }

    public List<LiveGameInfo> getLiveGameInfoList() {
        return liveGameInfoList;
    }

    public void setLiveGameInfoList(List<LiveGameInfo> liveGameInfoList) {
        this.liveGameInfoList = liveGameInfoList;
    }
}
