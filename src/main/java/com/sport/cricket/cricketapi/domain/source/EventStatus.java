package com.sport.cricket.cricketapi.domain.source;

import java.util.List;

public class EventStatus {
    private List<FeaturedAthletes> featuredAthletes;
    private String summary;

    private EventStatusType type;

    public List<FeaturedAthletes> getFeaturedAthletes() {
        return featuredAthletes;
    }

    public void setFeaturedAthletes(List<FeaturedAthletes> featuredAthletes) {
        this.featuredAthletes = featuredAthletes;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public EventStatusType getType() {
        return type;
    }

    public void setType(EventStatusType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EventStatus{" +
                "featuredAthletes=" + featuredAthletes +
                ", summary='" + summary + '\'' +
                ", type=" + type +
                '}';
    }
}
