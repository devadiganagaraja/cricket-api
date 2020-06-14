package com.sport.cricket.cricketapi.service;

import com.sport.cricket.cricketapi.domain.persistance.MatchAggregate;
import com.sport.cricket.cricketapi.domain.response.Match;
import com.sport.cricket.cricketapi.domain.response.Team;
import com.sport.cricket.cricketapi.domain.source.Event;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    public   MatchAggregate populateMatchAggregate(Match match) {
        MatchAggregate matchAggregate = new MatchAggregate();
        matchAggregate.setName(match.getName());
        matchAggregate.setId(match.getId());
        matchAggregate.setDate(match.getDate());
        matchAggregate.setEndDate(match.getEndDate());
        return matchAggregate;
    }

    public Match populateDomainMatch(MatchAggregate matchAggregate) {
        Match matchDomain = new Match();
        matchDomain.setName(matchAggregate.getName());
        matchDomain.setId(matchAggregate.getId());
        matchDomain.setDate(matchAggregate.getDate());
        matchDomain.setEndDate(matchAggregate.getEndDate());
        return matchDomain;
    }

    public MatchAggregate populateMatchAggregate(Event event) {
        MatchAggregate matchAggregate = new MatchAggregate();
        matchAggregate.setName(event.getName());
        matchAggregate.setId(event.getId());
        matchAggregate.setDate(event.getDate());
        matchAggregate.setEndDate(event.getEndDate());
        return matchAggregate;
    }
}
