package com.sport.cricket.cricketapi.domain.source;

public class FeaturedAthlete {
    private String displayName;
    private Long playerId;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
