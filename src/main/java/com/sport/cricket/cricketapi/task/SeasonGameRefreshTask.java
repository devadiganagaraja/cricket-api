package com.sport.cricket.cricketapi.task;

import com.sport.cricket.cricketapi.domain.persistance.SeasonAggregate;
import com.sport.cricket.cricketapi.domain.source.Event;
import com.sport.cricket.cricketapi.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeasonGameRefreshTask implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(SeasonGameRefreshTask.class);



    GameService gameService;


    private Event event;

    private SeasonAggregate seasonAggregate;

    public SeasonGameRefreshTask(Event event, SeasonAggregate seasonAggregate, GameService gameService) {
        this.event = event;
        this.seasonAggregate = seasonAggregate;
        this.gameService = gameService;
    }

    @Override
    public void run() {
        gameService.persistGameAggregate(event, seasonAggregate);
    }
}
