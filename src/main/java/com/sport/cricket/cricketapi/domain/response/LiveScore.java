package com.sport.cricket.cricketapi.domain.response;

public class LiveScore {

    private BattingScoreLeaf striker;
    private BattingScoreLeaf nonStriker;
    private BowlingScoreLeaf bowler;
    private BowlingScoreLeaf otherBowler;


    public BattingScoreLeaf getStriker() {
        return striker;
    }

    public void setStriker(BattingScoreLeaf striker) {
        this.striker = striker;
    }

    public BattingScoreLeaf getNonStriker() {
        return nonStriker;
    }

    public void setNonStriker(BattingScoreLeaf nonStriker) {
        this.nonStriker = nonStriker;
    }

    public BowlingScoreLeaf getBowler() {
        return bowler;
    }

    public void setBowler(BowlingScoreLeaf bowler) {
        this.bowler = bowler;
    }

    public BowlingScoreLeaf getOtherBowler() {
        return otherBowler;
    }

    public void setOtherBowler(BowlingScoreLeaf otherBowler) {
        this.otherBowler = otherBowler;
    }
}
