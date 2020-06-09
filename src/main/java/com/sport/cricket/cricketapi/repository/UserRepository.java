package com.sport.cricket.cricketapi.repository;

import com.sport.cricket.cricketapi.persistance.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends MongoRepository<User, String> , QuerydslPredicateExecutor<User> {
}
