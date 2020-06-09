package com.sport.cricket.cricketapi.controller;

import com.sport.cricket.cricketapi.domain.response.*;
import com.sport.cricket.cricketapi.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://www.cricket46.com")
public class SeasonController {

    @Autowired
    SeasonService seasonService;



    @GetMapping("/leagues/{league}/seasons")
    public List<Season> seasons(@PathVariable Integer league) {
        return seasonService.getSeasons(league);
    }


    @GetMapping("/leagues/{league}/seasons/{season}")
    public Season leagueSeason(@PathVariable Integer league, @PathVariable Integer season) {
        return seasonService.getSeason(league, season);
    }

    @GetMapping("/leagues/{league}/seasons/{season}/teams")
    public List<Team> seasonTeams(@PathVariable Integer league, @PathVariable Integer season) {
        return seasonService.getSeasonTeams(league, season);
    }

    @GetMapping("/leagues/{league}/seasons/{season}/matches")
    public List<Match> seasonMatches(@PathVariable Integer league, @PathVariable Integer season) {
        return seasonService.getSeasonMatches(league, season);
    }
}
