package com.sport.cricket.cricketapi.domain.response;


import java.util.ArrayList;
import java.util.List;

public class BattingScoreCard {
    private List<BattingScoreLeaf> battingScoreLeaves = new ArrayList<>();


    public List<BattingScoreLeaf> getBattingScoreLeaves() {
        return battingScoreLeaves;
    }

    public void setBattingScoreLeaves(List<BattingScoreLeaf> battingScoreLeaves) {
        this.battingScoreLeaves = battingScoreLeaves;
    }

    @Override
    public String toString() {
        return "BattingScoreCard{" +
                "battingScoreLeaves=" + battingScoreLeaves +
                '}';
    }
}
