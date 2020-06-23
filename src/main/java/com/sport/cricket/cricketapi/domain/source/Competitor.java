package com.sport.cricket.cricketapi.domain.source;

public class Competitor {
    private String homeAway;
    private int id;
    private int order;

    private Ref roster;
    private Ref linescores;

    public String getHomeAway() {
        return homeAway;
    }

    public void setHomeAway(String homeAway) {
        this.homeAway = homeAway;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    public Ref getRoster() {
        return roster;
    }

    public void setRoster(Ref roster) {
        this.roster = roster;
    }

    public Ref getLinescores() {
        return linescores;
    }

    public void setLinescores(Ref linescores) {
        this.linescores = linescores;
    }
}
