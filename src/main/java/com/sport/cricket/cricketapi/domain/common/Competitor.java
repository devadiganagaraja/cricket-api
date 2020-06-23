package com.sport.cricket.cricketapi.domain.common;


import java.util.HashMap;
import java.util.Map;

public class Competitor {
    private Long id;
    private String lineScoreRef;
    private String rosterRef;

    private Map<Integer, InningsScoreCard> inningsScores = new HashMap<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getLineScoreRef() {
        return lineScoreRef;
    }

    public void setLineScoreRef(String lineScoreRef) {
        this.lineScoreRef = lineScoreRef;
    }

    public String getRosterRef() {
        return rosterRef;
    }

    public void setRosterRef(String rosterRef) {
        this.rosterRef = rosterRef;
    }


    public Map<Integer, InningsScoreCard> getInningsScores() {
        return inningsScores;
    }

    public void setInningsScores(Map<Integer, InningsScoreCard> inningsScores) {
        this.inningsScores = inningsScores;
    }


    @Override
    public String toString() {
        return "Competitor{" +
                "id=" + id +
                '}';
    }
}
