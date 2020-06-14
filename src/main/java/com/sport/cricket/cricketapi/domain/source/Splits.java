package com.sport.cricket.cricketapi.domain.source;

import java.util.List;

public class Splits {
    private List<Category> categories;
    private Batting batting;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Batting getBatting() {
        return batting;
    }

    public void setBatting(Batting batting) {
        this.batting = batting;
    }
}
