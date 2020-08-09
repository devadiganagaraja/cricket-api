package com.sport.cricket.cricketapi.domain.response.league;

import java.util.ArrayList;
import java.util.List;

public class LeagueIndex {
    private List<LeagueDetails> tournaments = new ArrayList<>();
    private List<LeagueDetails> tours = new ArrayList<>();

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
}
