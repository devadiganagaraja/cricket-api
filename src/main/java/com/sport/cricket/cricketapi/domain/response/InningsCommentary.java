package com.sport.cricket.cricketapi.domain.response;

import com.cricketfoursix.cricketdomain.common.bbb.InningCommentarySummary;
import com.cricketfoursix.cricketdomain.domain.bbb.InningSummary;

import java.util.Set;
import java.util.TreeSet;

public class InningsCommentary implements Comparable<InningsCommentary>{

    private InningCommentarySummary inningCommentarySummary;
    private Set<OverCommentary> overCommentarySet = new TreeSet<>();


    public InningCommentarySummary getInningCommentarySummary() {
        return inningCommentarySummary;
    }

    public void setInningCommentarySummary(InningCommentarySummary inningCommentarySummary) {
        this.inningCommentarySummary = inningCommentarySummary;
    }

    public Set<OverCommentary> getOverCommentarySet() {
        return overCommentarySet;
    }

    public void setOverCommentarySet(Set<OverCommentary> overCommentarySet) {
        this.overCommentarySet = overCommentarySet;
    }

    @Override
    public int compareTo(InningsCommentary o) {
        return (o.inningCommentarySummary.getInningsNo()) - this.inningCommentarySummary.getInningsNo();
    }

    @Override
    public String toString() {
        return "InningsCommentary{" +
                "inningSummary=" + inningCommentarySummary +
                ", overCommentarySet=" + overCommentarySet +
                '}';
    }
}
