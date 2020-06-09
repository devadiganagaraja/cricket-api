package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.persistance.League;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LeagueRepository extends MongoRepository<League, Integer>, QuerydslPredicateExecutor<League> {
}
