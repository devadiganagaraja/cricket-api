package com.sport.cricket.cricketapi.domain.response;


import com.cricketfoursix.cricketdomain.common.game.InningsInfo;

import java.util.ArrayList;
import java.util.List;

public class Leader {


    private List<BattingScoreLeaf> battingLeaders = new ArrayList<>();
    private List<BowlingScoreLeaf> bowlingLeaders = new ArrayList<>();


    private InningsInfo inningsInfo;

    public InningsInfo getInningsInfo() {
        return inningsInfo;
    }

    public void setInningsInfo(InningsInfo inningsInfo) {
        this.inningsInfo = inningsInfo;
    }

    public List<BattingScoreLeaf> getBattingLeaders() {
        return battingLeaders;
    }

    public void setBattingLeaders(List<BattingScoreLeaf> battingLeaders) {
        this.battingLeaders = battingLeaders;
    }

    public List<BowlingScoreLeaf> getBowlingLeaders() {
        return bowlingLeaders;
    }

    public void setBowlingLeaders(List<BowlingScoreLeaf> bowlingLeaders) {
        this.bowlingLeaders = bowlingLeaders;
    }
}
