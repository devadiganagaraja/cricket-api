package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.domain.persistance.GameAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GameRepository extends MongoRepository<GameAggregate, Long>, QuerydslPredicateExecutor<GameAggregate> {
}
