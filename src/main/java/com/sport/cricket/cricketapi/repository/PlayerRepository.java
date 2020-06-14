package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.domain.persistance.PlayerAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PlayerRepository  extends MongoRepository<PlayerAggregate, Long>, QuerydslPredicateExecutor<PlayerAggregate> {
}
