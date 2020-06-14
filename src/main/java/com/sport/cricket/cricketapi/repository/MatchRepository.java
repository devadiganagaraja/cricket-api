package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.domain.persistance.MatchAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MatchRepository extends MongoRepository<MatchAggregate, Long>, QuerydslPredicateExecutor<MatchAggregate> {
}
