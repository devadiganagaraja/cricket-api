package com.sport.cricket.cricketapi.domain.persistance;

import com.sport.cricket.cricketapi.domain.common.PlayerStat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "players")
public class PlayerAggregate {
    @Id
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

    public String getName() {
        return name;
    }

    public Date getStatsLastUpdated() {
        return statsLastUpdated;
    }

    public void setStatsLastUpdated(Date statsLastUpdated) {
        this.statsLastUpdated = statsLastUpdated;
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
}
