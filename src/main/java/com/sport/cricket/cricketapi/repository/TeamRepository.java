package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.persistance.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TeamRepository extends MongoRepository<Team, Long>, QuerydslPredicateExecutor<Team> {
}
