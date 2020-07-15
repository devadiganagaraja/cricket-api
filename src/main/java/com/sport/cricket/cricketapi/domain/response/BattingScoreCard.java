package com.sport.cricket.cricketapi.domain.response;


import java.util.ArrayList;
import java.util.List;

public class BattingScoreCard {
    private List<BattingScoreLeaf> battingScoreLeaves = new ArrayList<>();
    private List<BattingScoreLeaf> yetToBat = new ArrayList<>();


    public List<BattingScoreLeaf> getYetToBat() {
        return yetToBat;
    }

    public void setYetToBat(List<BattingScoreLeaf> yetToBat) {
        this.yetToBat = yetToBat;
    }

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
