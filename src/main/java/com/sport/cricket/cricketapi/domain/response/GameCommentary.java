package com.sport.cricket.cricketapi.domain.response;

import java.util.Set;
import java.util.TreeSet;

public class GameCommentary {
    private Long eventId;
    private Set<InningsCommentary> inningsCommentary = new TreeSet<>();

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Set<InningsCommentary> getInningsCommentary() {
        return inningsCommentary;
    }

    public void setInningsCommentary(Set<InningsCommentary> inningsCommentary) {
        this.inningsCommentary = inningsCommentary;
    }
}
