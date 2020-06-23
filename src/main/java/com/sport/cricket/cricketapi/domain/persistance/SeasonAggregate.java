package com.sport.cricket.cricketapi.domain.persistance;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "seasons")
public class SeasonAggregate {
    @Id
    private String leagueSeasonId;
    private Integer year;
    private Integer leagueId;
    private Date startDate;
    private Date endDate;
    private String name;
    private String shortName;

    private Set<Long> teamIds = new HashSet<>();
    private Set<Long> gameIds = new HashSet<>();


    public String getLeagueSeasonId() {
        return leagueSeasonId;
    }

    public void setLeagueSeasonId(String leagueSeasonId) {
        this.leagueSeasonId = leagueSeasonId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public Date getStartDate() {
        return startDate;
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

    public Set<Long> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(Set<Long> teamIds) {
        this.teamIds = teamIds;
    }

    public Set<Long> getGameIds() {
        return gameIds;
    }

    public void setGameIds(Set<Long> gameIds) {
        this.gameIds = gameIds;
    }

    @Override
    public String toString() {
        return "SeasonAggregate{" +
                "leagueSeasonId='" + leagueSeasonId + '\'' +
                ", year=" + year +
                ", leagueId=" + leagueId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", teamIds=" + teamIds +
                ", gameIds=" + gameIds +
                '}';
    }
}
