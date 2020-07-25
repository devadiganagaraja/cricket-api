package com.sport.cricket.cricketapi.domain.response.league;

import com.cricketfoursix.cricketdomain.common.league.LeagueSeason;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeagueDetails {
    private long id;
    private String abbreviation;
    private String name;

    private boolean tournament;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}

