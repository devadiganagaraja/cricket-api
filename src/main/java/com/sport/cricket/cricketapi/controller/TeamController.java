package com.sport.cricket.cricketapi.controller;

import com.sport.cricket.cricketapi.domain.response.Season;
import com.sport.cricket.cricketapi.domain.response.Team;
import com.sport.cricket.cricketapi.service.SeasonService;
import com.sport.cricket.cricketapi.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping("/teams")
    public List<Team> teams() {
        return teamService.getTeams();
    }
}
