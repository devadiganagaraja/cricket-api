package com.sport.cricket.cricketapi.domain.response;

import com.sport.cricket.cricketapi.domain.common.PlayerStat;

import java.util.Date;
import java.util.List;

public class Player {
    private long id;
    private String name;
    private int age;
    private String battingStyle;
    private String bowlingStyle;
    private String country;
    private String playerType;
    private Date statsLastUpdated;

    private List<PlayerStat> playerStats;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStatsLastUpdated() {
        return statsLastUpdated;
    }

    public void setStatsLastUpdated(Date statsLastUpdated) {
        this.statsLastUpdated = statsLastUpdated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBattingStyle() {
        return battingStyle;
    }

    public void setBattingStyle(String battingStyle) {
        this.battingStyle = battingStyle;
    }

    public String getBowlingStyle() {
        return bowlingStyle;
    }

    public void setBowlingStyle(String bowlingStyle) {
        this.bowlingStyle = bowlingStyle;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public List<PlayerStat> getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(List<PlayerStat> playerStats) {
        this.playerStats = playerStats;
    }



    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", battingStyle='" + battingStyle + '\'' +
                ", bowlingStyle='" + bowlingStyle + '\'' +
                ", country='" + country + '\'' +
                ", playerType='" + playerType + '\'' +
                ", playerStats=" + playerStats +
                '}';
    }
}
