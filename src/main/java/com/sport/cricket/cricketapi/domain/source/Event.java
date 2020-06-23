package com.sport.cricket.cricketapi.domain.source;

import java.util.Date;
import java.util.List;

public class Event {
    private long id;
    private Date date;
    private Date endDate;
    private String name;

    private List<Competition> competitions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", endDate=" + endDate +
                ", name='" + name + '\'' +
                ", competitions=" + competitions +
                '}';
    }
}
