package com.sport.cricket.cricketapi.domain.response;

import java.util.Objects;

public class BallCommentary implements Comparable<BallCommentary> {
    private String ballNumber;
    private String ballText;
    private String BallSymbol;


    public String getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber(String ballNumber) {
        this.ballNumber = ballNumber;
    }

    public String getBallText() {
        return ballText;
    }

    public void setBallText(String ballText) {
        this.ballText = ballText;
    }

    public String getBallSymbol() {
        return BallSymbol;
    }

    public void setBallSymbol(String ballSymbol) {
        BallSymbol = ballSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BallCommentary that = (BallCommentary) o;
        return Objects.equals(ballNumber, that.ballNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ballNumber);
    }

    @Override
    public int compareTo(BallCommentary o) {
        return o.ballNumber.compareTo(this.ballNumber);
    }

    @Override
    public String toString() {
        return "BallCommentary{" +
                "ballNumber='" + ballNumber + '\'' +
                '}';
    }

}
