package com.sport.cricket.cricketapi.domain.response;

import com.sport.cricket.cricketapi.domain.source.InningsInfo;

public class ScoreCard {
    private InningsInfo inningsInfo;
    private BattingScoreCard battingScoreCard;
    private BowlingScoreCard bowlingScoreCard;

    public InningsInfo getInningsInfo() {
        return inningsInfo;
    }

    public void setInningsInfo(InningsInfo inningsInfo) {
        this.inningsInfo = inningsInfo;
    }

    public BattingScoreCard getBattingScoreCard() {
        return battingScoreCard;
    }

    public void setBattingScoreCard(BattingScoreCard battingScoreCard) {
        this.battingScoreCard = battingScoreCard;
    }

    public BowlingScoreCard getBowlingScoreCard() {
        return bowlingScoreCard;
    }

    public void setBowlingScoreCard(BowlingScoreCard bowlingScoreCard) {
        this.bowlingScoreCard = bowlingScoreCard;
    }


    @Override
    public String toString() {
        return "ScoreCard{" +
                "inningsInfo=" + inningsInfo +
                ", battingScoreCard=" + battingScoreCard +
                ", bowlingScoreCard=" + bowlingScoreCard +
                '}';
    }
}
