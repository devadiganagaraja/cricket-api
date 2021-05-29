package com.sport.cricket.cricketapi.controller;

import com.sport.cricket.cricketapi.domain.games.cricketcards.PlayerGameInfo;
import com.sport.cricket.cricketapi.service.CricketCardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CricketCardGameController {



    @Autowired
    CricketCardGameService cricketCardGameService;

    @GetMapping("/games/{gameId}/players/{playerId}")
    public PlayerGameInfo getGamePageInfo(@PathVariable("gameId") long gameId, @PathVariable("playerId") long playerId){
        return cricketCardGameService.getGamePageInfo(gameId, playerId);
    }


}
