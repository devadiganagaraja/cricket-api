package com.sport.cricket.cricketapi.domain.common;

import com.sport.cricket.cricketapi.domain.source.InningsInfo;

public class InningsScoreCard {
    private InningsInfo inningsInfo;
    private BattingCard battingCard;
    private BowlingCard bowlingCard;

    public InningsInfo getInningsInfo() {
        return inningsInfo;
    }

    public void setInningsInfo(InningsInfo inningsInfo) {
        this.inningsInfo = inningsInfo;
    }

    public BattingCard getBattingCard() {
        return battingCard;
    }

    public void setBattingCard(BattingCard battingCard) {
        this.battingCard = battingCard;
    }

    public BowlingCard getBowlingCard() {
        return bowlingCard;
    }

    public void setBowlingCard(BowlingCard bowlingCard) {
        this.bowlingCard = bowlingCard;
    }
}
