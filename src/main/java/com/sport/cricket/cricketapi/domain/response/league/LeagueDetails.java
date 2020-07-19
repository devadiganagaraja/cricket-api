package com.sport.cricket.cricketapi.domain.response.league;

import com.cricketfoursix.cricketdomain.common.league.LeagueSeason;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeagueDetails {
    private Long leagueId;
    private String leagueName;
    private List<LeagueSeason> leagueSeasons;

    public Long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public List<LeagueSeason> getLeagueSeasons() {
        return leagueSeasons;
    }

    public void setLeagueSeasons(List<LeagueSeason> leagueSeasons) {
        this.leagueSeasons = leagueSeasons;
    }
}

