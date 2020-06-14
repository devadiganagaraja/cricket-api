package com.sport.cricket.cricketapi.domain.persistance;

import com.sport.cricket.cricketapi.domain.common.Newslet;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "news")
public class NewsAggregate {
    private Date date;
    private List<Newslet> newslets;

}
