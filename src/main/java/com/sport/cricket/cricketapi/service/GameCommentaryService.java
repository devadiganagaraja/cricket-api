package com.sport.cricket.cricketapi.service;

import com.sport.cricket.cricketapi.domain.response.GameCommentary;
import org.springframework.stereotype.Service;

@Service
public class GameCommentaryService {




    public GameCommentary fetchAllCommentary(String gameId) {
        GameCommentary gameCommentary = new GameCommentary();
        return gameCommentary;
    }

}
