package com.sport.cricket.cricketapi.domain.response;

import java.util.ArrayList;
import java.util.List;

public class BowlingScoreCard {

    private List<BowlingScoreLeaf> bowlingScoreLeaves = new ArrayList<>();

    public List<BowlingScoreLeaf> getBowlingScoreLeaves() {
        return bowlingScoreLeaves;
    }

    public void setBowlingScoreLeaves(List<BowlingScoreLeaf> bowlingScoreLeaves) {
        this.bowlingScoreLeaves = bowlingScoreLeaves;
    }


    @Override
    public String toString() {
        return "BowlingScoreCard{" +
                "bowlingScoreLeaves=" + bowlingScoreLeaves +
                '}';
    }
}
