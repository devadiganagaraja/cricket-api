package com.sport.cricket.cricketapi.domain.response;

import com.cricketfoursix.cricketdomain.common.bbb.InningCommentarySummary;
import com.cricketfoursix.cricketdomain.domain.bbb.OverSummary;

import java.util.*;

public class OverCommentary implements Comparable<OverCommentary> {

    private int overNumber;
    private OverSummary overSummary;
    private TreeMap<Double, String> overRunsSummaryMap = new TreeMap<>();
    private String overRunsSummary = "";
    private Set<BallCommentary> ballCommentarySet = new TreeSet<>();
    private InningCommentarySummary inningCommentarySummary;


    public OverSummary getOverSummary() {
        return overSummary;
    }

    public String getOverRunsSummary() {
        return overRunsSummary;
    }


    public TreeMap<Double, String> getOverRunsSummaryMap() {
        return overRunsSummaryMap;
    }

    public void setOverRunsSummaryMap(TreeMap<Double, String> overRunsSummaryMap) {
        this.overRunsSummaryMap = overRunsSummaryMap;
    }

    public void setOverRunsSummary(String overRunsSummary) {
        this.overRunsSummary = overRunsSummary;
    }

    public void setOverSummary(OverSummary overSummary) {
        this.overSummary = overSummary;
    }

    public Set<BallCommentary> getBallCommentarySet() {
        return ballCommentarySet;
    }

    public void setBallCommentarySet(Set<BallCommentary> ballCommentarySet) {
        this.ballCommentarySet = ballCommentarySet;
    }

    @Override
    public int compareTo(OverCommentary o) {
        return  o.getOverNumber() - this.getOverNumber();
    }

    public int getOverNumber() {
        return overNumber;
    }

    public void setOverNumber(int overNumber) {
        this.overNumber = overNumber;
    }

    public InningCommentarySummary getInningCommentarySummary() {
        return inningCommentarySummary;
    }

    public void setInningCommentarySummary(InningCommentarySummary inningCommentarySummary) {
        this.inningCommentarySummary = inningCommentarySummary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverCommentary that = (OverCommentary) o;
        return overNumber == that.overNumber;
    }

    @Override
    public int hashCode() {

        return Objects.hash(overNumber);
    }


    @Override
    public String toString() {
        return "OverCommentary{" +
                "overNumber=" + overNumber +
                ", overSummary=" + overSummary +
                ", ballCommentarySet=" + ballCommentarySet +
                '}';
    }
}
