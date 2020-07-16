package com.sport.cricket.cricketapi.controller;

import com.sport.cricket.cricketapi.domain.response.GameCommentary;
import com.sport.cricket.cricketapi.service.GameCommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin
public class GameCommentaryController {

    @Autowired
    GameCommentaryService gameCommentaryService;



    public GameCommentary getEventComms(@PathVariable(value="gameId") String gameId) {
        return gameCommentaryService.fetchAllCommentary(gameId);
    }
}
