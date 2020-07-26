package com.sport.cricket.cricketapi.domain.response;

import com.cricketfoursix.cricketdomain.common.bbb.InningCommentarySummary;
import com.cricketfoursix.cricketdomain.domain.bbb.InningSummary;

import java.util.Set;
import java.util.TreeSet;

public class InningsCommentary implements Comparable<InningsCommentary>{

    private Set<OverCommentary> overCommentarySet = new TreeSet<>();

    private int inningsNumber;



    public int getInningsNumber() {
        return inningsNumber;
    }

    public void setInningsNumber(int inningsNumber) {
        this.inningsNumber = inningsNumber;
    }

    public Set<OverCommentary> getOverCommentarySet() {
        return overCommentarySet;
    }

    public void setOverCommentarySet(Set<OverCommentary> overCommentarySet) {
        this.overCommentarySet = overCommentarySet;
    }

    @Override
    public int compareTo(InningsCommentary o) {
        return (o.getInningsNumber()) - this.getInningsNumber();
    }

    @Override
    public String toString() {
        return "InningsCommentary{" +
                "inningsNumber=" + inningsNumber +
                ", overCommentarySet=" + overCommentarySet +
                '}';
    }
}
