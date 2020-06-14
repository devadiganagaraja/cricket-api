package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.domain.persistance.SeasonAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SeasonRepository  extends MongoRepository<SeasonAggregate, String>, QuerydslPredicateExecutor<SeasonAggregate> {
}
