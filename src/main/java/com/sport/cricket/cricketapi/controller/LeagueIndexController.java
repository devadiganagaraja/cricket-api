package com.sport.cricket.cricketapi.controller;

import com.sport.cricket.cricketapi.domain.response.league.LeagueIndex;
import com.sport.cricket.cricketapi.service.LeagueIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class LeagueIndexController {

    @Autowired
    LeagueIndexService leagueIndexService;


    @GetMapping("/leagueIndex")
    public LeagueIndex leagues() {
        return leagueIndexService.getLeagueIndices();
    }
}
