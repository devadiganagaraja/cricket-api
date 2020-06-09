package com.sport.cricket.cricketapi.config;

import com.sport.cricket.cricketapi.persistance.QLeague;
import com.sport.cricket.cricketapi.persistance.QSeason;
import com.sport.cricket.cricketapi.persistance.QUser;
import com.sport.cricket.cricketapi.persistance.QTeam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDSLConfig {

    @Bean
    public QUser qUser(){
        return new QUser("users");
    }

    @Bean
    public QLeague qLeague(){
        return new QLeague("leagues");
    }

    @Bean
    public QSeason qSeason(){
        return new QSeason("seasons");
    }

    @Bean
    public QTeam qTeam(){
        return new QTeam("teams");
    }




}
