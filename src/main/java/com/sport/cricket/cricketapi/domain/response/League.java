package com.sport.cricket.cricketapi.domain.response;

public class League {

    private Integer id;
    private String abbreviation;
    private String name;
    private boolean isTournament;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isTournament() {
        return isTournament;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsTournament() {
        return isTournament;
    }

    public void setTournament(boolean tournament) {
        isTournament = tournament;
    }

    @Override
    public String toString() {
        return "League{" +
                "leagueId=" + id +
                ", abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", tournament=" + isTournament +
                '}';
    }
}
