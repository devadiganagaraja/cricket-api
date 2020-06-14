package com.sport.cricket.cricketapi.controller;


import com.sport.cricket.cricketapi.domain.response.Player;
import com.sport.cricket.cricketapi.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("/players/{playerId}")
    public Player league(@PathVariable Long playerId) {

        return playerService.getPlayerDetails(playerId);
    }
}
