package com.sport.cricket.cricketapi.domain.response.league;

import com.cricketfoursix.cricketdomain.common.league.ChildLeague;
import com.cricketfoursix.cricketdomain.common.league.LeagueSeason;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeagueDetails {
    private long id;
    private String abbreviation;
    private String name;

    private boolean tournament;

    private Set<ChildLeague> childLeagues = new HashSet<>();



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

    public Set<ChildLeague> getChildLeagues() {
        return childLeagues;
    }

    public void setChildLeagues(Set<ChildLeague> childLeagues) {
        this.childLeagues = childLeagues;
    }
}

