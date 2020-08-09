package com.sport.cricket.cricketapi.service;

import com.cricketfoursix.cricketdomain.aggregate.LeagueIndexAggregate;
import com.cricketfoursix.cricketdomain.common.league.LeagueType;
import com.cricketfoursix.cricketdomain.repository.LeagueIndexRepository;
import com.sport.cricket.cricketapi.domain.response.league.LeagueDetails;
import com.sport.cricket.cricketapi.domain.response.league.LeagueIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueIndexService {
    @Autowired
    LeagueIndexRepository leagueIndexRepository;

    public LeagueIndex getLeagueIndices() {
        LeagueIndex leagueIndex = new LeagueIndex();

        List<LeagueIndexAggregate> leagueIndexAggregateList = leagueIndexRepository.findAll();
        if(null != leagueIndexAggregateList){
            leagueIndexAggregateList.forEach(leagueIndexAggregate -> {
                LeagueDetails leagueDetails = new LeagueDetails();
                leagueDetails.setId(leagueIndexAggregate.getLeagueId());
                leagueDetails.setName(leagueIndexAggregate.getName());
                leagueDetails.setAbbreviation(leagueIndexAggregate.getAbbreviation());
                if(LeagueType.tournament.equals(leagueIndexAggregate.getLeagueType())){
                    leagueDetails.setTournament(true);
                    leagueIndex.getTournaments().add(leagueDetails);
                }else{
                    leagueIndex.getTours().add(leagueDetails);
                }
            });
        }
        return leagueIndex;
    }
}
