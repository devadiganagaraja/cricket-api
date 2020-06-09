package com.sport.cricket.cricketapi.domain.response;

public class Team {

    private String id;
    private String name;
    private String abbreviation;
    private boolean isNational;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public boolean getIsNational() {
        return isNational;
    }

    public void setNational(boolean national) {
        isNational = national;
    }
}
