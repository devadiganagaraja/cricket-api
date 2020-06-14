package com.sport.cricket.cricketapi.repository;


import com.sport.cricket.cricketapi.domain.persistance.NewsAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Date;

public interface NewsRepository extends MongoRepository<NewsAggregate, Date>, QuerydslPredicateExecutor<NewsAggregate> {
}
