package com.sport.cricket.cricketapi.controller;

import com.sport.cricket.cricketapi.domain.response.League;
import com.sport.cricket.cricketapi.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://cricket64.com")
public class LeagueController {

    @Autowired
    LeagueService leagueService;

    @GetMapping("/leagues")
    public List<League> leagues() {
        return leagueService.getLeagues();
    }

    @GetMapping("/leagues/{league}")
    public League league(@PathVariable Integer league) {
        return leagueService.getLeague(league);
    }
}
