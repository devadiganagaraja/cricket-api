package com.sport.cricket.cricketapi.domain.source;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Competition {
    private String note;

    @JsonProperty("class")
    private EventClass eventClass;

    private Venue venue;

    private List<Competitor> competitors;

    private Ref status;

    private Ref details;


    private List<Note>  notes;

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public EventClass getEventClass() {
        return eventClass;
    }

    public void setEventClass(EventClass eventClass) {
        this.eventClass = eventClass;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }


    public List<Competitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<Competitor> competitors) {
        this.competitors = competitors;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "note='" + note + '\'' +
                ", eventClass=" + eventClass +
                ", venue=" + venue +
                ", competitors=" + competitors +
                ", status=" + status +
                ", details=" + details +
                ", notes=" + notes +
                '}';
    }

    public Ref getStatus() {
        return status;
    }

    public void setStatus(Ref status) {
        this.status = status;
    }

    public Ref getDetails() {
        return details;
    }

    public void setDetails(Ref details) {
        this.details = details;
    }
}
