package com.sport.cricket.cricketapi.persistance;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "leagues")
public class League {
    @Id
    private Integer leagueId;
    private String abbreviation;
    private String name;
    private boolean tournament;


    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTournament() {
        return tournament;
    }

    public void setTournament(boolean tournament) {
        this.tournament = tournament;
    }


    @Override
    public String toString() {
        return "League{" +
                "leagueId=" + leagueId +
                ", abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", tournament=" + tournament +
                '}';
    }
}
