package com.sport.cricket.cricketapi.config;

import com.cricketfoursix.cricketdomain.aggregate.QGameAggregate;


import com.cricketfoursix.cricketdomain.aggregate.QLeagueAggregate;
import com.cricketfoursix.cricketdomain.aggregate.QTeamAggregate;
import com.cricketfoursix.cricketdomain.aggregate.QUserAggregate;
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
    public QTeamAggregate qTeamAggregate(){
        return new QTeamAggregate("teams");
    }



    @Bean
    public QGameAggregate qGameAggregate(){
        return new QGameAggregate("games");
    }


}
