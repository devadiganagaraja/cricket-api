package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.domain.persistance.UserAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends MongoRepository<UserAggregate, String> , QuerydslPredicateExecutor<UserAggregate> {
}
