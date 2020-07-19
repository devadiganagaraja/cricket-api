package com.sport.cricket.cricketapi.domain.response.squad;


import com.cricketfoursix.cricketdomain.common.game.GameInfo;

public class EventBestEleven {
    private GameInfo gameInfo;
    private Squad squad1;
    private Squad squad2;
    private UserSquad userSquad;

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public Squad getSquad1() {
        return squad1;
    }

    public void setSquad1(Squad squad1) {
        this.squad1 = squad1;
    }

    public Squad getSquad2() {
        return squad2;
    }

    public void setSquad2(Squad squad2) {
        this.squad2 = squad2;
    }

    public UserSquad getUserSquad() {
        return userSquad;
    }

    public void setUserSquad(UserSquad userSquad) {
        this.userSquad = userSquad;
    }
}
