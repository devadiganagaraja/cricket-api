package com.sport.cricket.cricketapi.controller;

import com.sport.cricket.cricketapi.domain.response.league.LeagueDetails;
import com.sport.cricket.cricketapi.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
public class LeagueController {


    @Autowired
    LeagueService leagueService;


    @GetMapping("/leagues")
    public List<LeagueDetails> leagues() {
        return leagueService.getLeagues();
    }

    @GetMapping("/leagues/{league}")
    public LeagueDetails league(@PathVariable Long league) {
        return leagueService.getLeagueInfo(league);
    }
}
