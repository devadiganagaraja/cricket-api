package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.persistance.Season;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SeasonRepository  extends MongoRepository<Season, String>, QuerydslPredicateExecutor<Season> {
}
