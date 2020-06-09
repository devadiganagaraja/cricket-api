package com.sport.cricket.cricketapi.domain.source;

import com.sport.cricket.cricketapi.domain.source.Ref;

import java.util.List;

public class ItemListing {

    public List<Ref> items;

    private int count;


    public List<Ref> getItems() {
        return items;
    }

    public void setItems(List<Ref> items) {
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
