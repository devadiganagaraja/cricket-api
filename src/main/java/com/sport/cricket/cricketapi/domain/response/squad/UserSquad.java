package com.sport.cricket.cricketapi.domain.response.squad;

import com.cricketfoursix.cricketdomain.common.squad.UserSquadPlayer;

import java.util.ArrayList;
import java.util.List;

public class UserSquad {

    List<UserSquadPlayer> userSquadPlayers = new ArrayList<>();
    float totalPoints;

    public List<UserSquadPlayer> getUserSquadPlayers() {
        return userSquadPlayers;
    }

    public void setUserSquadPlayers(List<UserSquadPlayer> userSquadPlayers) {
        this.userSquadPlayers = userSquadPlayers;
    }

    public float getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(float totalPoints) {
        this.totalPoints = totalPoints;
    }
}
