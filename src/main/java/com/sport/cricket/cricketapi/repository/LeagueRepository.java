package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.domain.persistance.LeagueAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LeagueRepository extends MongoRepository<LeagueAggregate, Integer>, QuerydslPredicateExecutor<LeagueAggregate> {
}
