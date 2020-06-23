package com.sport.cricket.cricketapi.domain.persistance;

import com.sport.cricket.cricketapi.domain.common.Competitor;
import com.sport.cricket.cricketapi.domain.common.GameClass;
import com.sport.cricket.cricketapi.domain.common.GameSummary;
import com.sport.cricket.cricketapi.domain.common.GameInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "games")
public class GameAggregate {
    @Id
    private Long id;

    private GameClass gameClass;

    private GameInfo gameInfo;

    private Date lastUpdated;

    private GameSummary gameSummary;

    private Competitor competitor1;
    private Competitor competitor2;

    private String gameStatusApiRef;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameClass getGameClass() {
        return gameClass;
    }

    public void setGameClass(GameClass gameClass) {
        this.gameClass = gameClass;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public GameSummary getGameSummary() {
        return gameSummary;
    }

    public void setGameSummary(GameSummary gameSummary) {
        this.gameSummary = gameSummary;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    public Competitor getCompetitor1() {
        return competitor1;
    }

    public void setCompetitor1(Competitor competitor1) {
        this.competitor1 = competitor1;
    }

    public Competitor getCompetitor2() {
        return competitor2;
    }

    public void setCompetitor2(Competitor competitor2) {
        this.competitor2 = competitor2;
    }

    public String getGameStatusApiRef() {
        return gameStatusApiRef;
    }

    public void setGameStatusApiRef(String gameStatusApiRef) {
        this.gameStatusApiRef = gameStatusApiRef;
    }

    @Override
    public String toString() {
        return "GameAggregate{" +
                "id=" + id +
                ", gameClass=" + gameClass +
                ", gameInfo=" + gameInfo +
                ", lastUpdated=" + lastUpdated +
                ", gameSummary=" + gameSummary +
                ", competitor1=" + competitor1 +
                ", competitor2=" + competitor2 +
                '}';
    }
}
