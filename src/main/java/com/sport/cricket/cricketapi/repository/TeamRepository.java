package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.domain.persistance.TeamAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TeamRepository extends MongoRepository<TeamAggregate, Long>, QuerydslPredicateExecutor<TeamAggregate> {
}
