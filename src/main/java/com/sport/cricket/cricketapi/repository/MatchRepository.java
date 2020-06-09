package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.persistance.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MatchRepository extends MongoRepository<Match, Long>, QuerydslPredicateExecutor<Match> {
}
