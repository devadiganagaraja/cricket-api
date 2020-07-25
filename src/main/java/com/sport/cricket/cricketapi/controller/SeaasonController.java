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
@CrossOrigin
        //(origins = "http://www.cricket46.com")
public class SeaasonController {

    @Autowired
    SeasonService seasonService;



    @GetMapping("/leagues/{league}/seasons")
    public List<Season> seasons(@PathVariable Long league) {
        return seasonService.getSeasons(league);
    }


    @GetMapping("/leagues/{league}/seasons/{season}")
    public Season leagueSeason(@PathVariable Long league, @PathVariable Integer season) {
        return seasonService.getSeason(league, season);
    }

    @GetMapping("/leagues/{league}/seasons/{season}/teams")
    public List<String> seasonTeams(@PathVariable Long league, @PathVariable Integer season) {
        return seasonService.getSeasonTeams(league, season);
    }

    @GetMapping("/leagues/{league}/seasons/{season}/games")
    public List<Game> seasonGames(@PathVariable Long league, @PathVariable Integer season) {
        return seasonService.getSeasonGames(league, season);
    }
}
