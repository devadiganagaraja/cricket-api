package com.sport.cricket.cricketapi.controller;

import com.cricketfoursix.cricketdomain.common.game.GameStatus;
import com.sport.cricket.cricketapi.domain.response.Game;
import com.sport.cricket.cricketapi.domain.response.GameCommentary;
import com.sport.cricket.cricketapi.service.GameCommentaryService;
import com.sport.cricket.cricketapi.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    GameCommentaryService gameCommentaryService;


    @RequestMapping("/games")
    public Set<Game> getGames(@RequestParam(required = false, name = "days") String days) {
        return gameService.getGames(days);
    }

    @GetMapping("/games/{gameId}")
    public Game game(@PathVariable Long gameId, @RequestParam(required = false, defaultValue = "false") boolean refresh) {
        Game game =  gameService.getGame(gameId);
        if(!GameStatus.pre.equals(game.getGameStatus()))
            game.setGameCommentary(gameCommentaryService.fetchAllBallDetails(gameId));
        return game;
    }
}
