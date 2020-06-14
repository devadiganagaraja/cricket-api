package com.sport.cricket.cricketapi.domain.persistance;

import com.sport.cricket.cricketapi.domain.common.Competitor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "matches")
public class MatchAggregate {
    @Id
    private Long id;
    private String name;
    private Date date;
    private Date endDate;
    private String shortName;
    private Competitor competitor1;
    private Competitor competitor2;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
