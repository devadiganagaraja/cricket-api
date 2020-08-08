package com.sport.cricket.cricketapi.domain.response.season;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Season {
    private String name;
    private String shortName;
    private Date startDate;
    private Date endDate;
    private Integer year;
    private Integer id;

    private List<PostGameInfo> postGameInfoList = new ArrayList<>();
    private List<PreGameInfo> preGameInfoList = new ArrayList<>();
    private List<LiveGameInfo> liveGameInfoList = new ArrayList<>();



    private ArrayList<String> teamNames;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public ArrayList<String> getTeamNames() {
        return teamNames;
    }

    public void setTeamNames(ArrayList<String> teamNames) {
        this.teamNames = teamNames;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
