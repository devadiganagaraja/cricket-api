package com.sport.cricket.cricketapi.domain.response;

import java.util.Set;
import java.util.TreeSet;

public class GameCommentary {
    private String eventId;
    private Set<InningsCommentary> inningsCommentary = new TreeSet<>();
}
