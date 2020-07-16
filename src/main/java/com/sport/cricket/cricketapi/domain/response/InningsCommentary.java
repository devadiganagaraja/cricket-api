package com.sport.cricket.cricketapi.domain.response;

import java.util.Set;
import java.util.TreeSet;

public class InningsCommentary implements Comparable<InningsCommentary>{

    private InningCommentarySummary inningCommentarySummary;
    private Set<OverCommentary> overCommentarySet = new TreeSet<>();

    @Override
    public int compareTo(InningsCommentary o) {
        return (o.inningCommentarySummary.getInningsNo()) - this.inningCommentarySummary.getInningsNo();
    }

    @Override
    public String toString() {
        return "InningsCommentary{" +
                "inningCommentarySummary=" + inningCommentarySummary +
                ", overCommentarySet=" + overCommentarySet +
                '}';
    }
}
