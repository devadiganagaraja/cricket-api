package com.sport.cricket.cricketapi.service;

import com.sport.cricket.cricketapi.domain.response.Match;
import com.sport.cricket.cricketapi.domain.response.Team;
import com.sport.cricket.cricketapi.domain.source.Event;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    public   com.sport.cricket.cricketapi.persistance.Match populateDBMatch(Match match) {
        com.sport.cricket.cricketapi.persistance.Match matchDb = new com.sport.cricket.cricketapi.persistance.Match();
        matchDb.setName(match.getName());
        matchDb.setId(match.getId());
        matchDb.setDate(match.getDate());
        matchDb.setEndDate(match.getEndDate());
        return matchDb;
    }

    public Match populateDomainMatch(com.sport.cricket.cricketapi.persistance.Match match) {
        Match matchDomain = new Match();
        matchDomain.setName(match.getName());
        matchDomain.setId(match.getId());
        matchDomain.setDate(match.getDate());
        matchDomain.setEndDate(match.getEndDate());
        return matchDomain;
    }

    public com.sport.cricket.cricketapi.persistance.Match populateDBMatch(Event event) {
        com.sport.cricket.cricketapi.persistance.Match matchDb = new com.sport.cricket.cricketapi.persistance.Match();
        matchDb.setName(event.getName());
        matchDb.setId(event.getId());
        matchDb.setDate(event.getDate());
        matchDb.setEndDate(event.getEndDate());
        return matchDb;
    }
}
