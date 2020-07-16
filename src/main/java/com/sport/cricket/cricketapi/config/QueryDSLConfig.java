package com.sport.cricket.cricketapi.config;

import com.cricketfoursix.cricketdomain.aggregate.QGameAggregate;
import com.sport.cricket.cricketapi.domain.persistance.*;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDSLConfig {

    @Bean
    public QUserAggregate qUserAggregate(){
        return new QUserAggregate("users");
    }

    @Bean
    public QLeagueAggregate qLeagueAggregate(){
        return new QLeagueAggregate("leagues");
    }

    @Bean
    public QSeasonAggregate qSeasonAggregate(){
        return new QSeasonAggregate("seasons");
    }

    @Bean
    public QTeamAggregate qTeamAggregate(){
        return new QTeamAggregate("teams");
    }



    @Bean
    public QGameAggregate qGameAggregate(){
        return new QGameAggregate("games");
    }


}
