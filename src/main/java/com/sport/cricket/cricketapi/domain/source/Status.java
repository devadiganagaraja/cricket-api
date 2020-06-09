package com.sport.cricket.cricketapi.domain.source;

import java.util.List;

public class Status {
    private String summary;
    private Type type;
    private Integer dayNumber;
    private Integer period;

    private List<FeaturedAthlete> featuredAthletes;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public List<FeaturedAthlete> getFeaturedAthletes() {
        return featuredAthletes;
    }

    public void setFeaturedAthletes(List<FeaturedAthlete> featuredAthletes) {
        this.featuredAthletes = featuredAthletes;
    }
}
