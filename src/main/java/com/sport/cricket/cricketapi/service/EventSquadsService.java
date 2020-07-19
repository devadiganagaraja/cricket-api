package com.sport.cricket.cricketapi.service;

import com.cricketfoursix.cricketdomain.aggregate.GameAggregate;
import com.cricketfoursix.cricketdomain.aggregate.LeagueSquadAggregate;
import com.cricketfoursix.cricketdomain.common.game.GameClass;
import com.cricketfoursix.cricketdomain.common.game.GameInfo;
import com.cricketfoursix.cricketdomain.common.squad.Squad;
import com.cricketfoursix.cricketdomain.common.squad.SquadPlayer;
import com.cricketfoursix.cricketdomain.repository.LeagueSquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventSquadsService {


    @Autowired
    LeagueSquadRepository leagueSquadRepository;

    public List<SquadPlayer> getLeagueTeamPlayers(long teamId, GameInfo gameInfo) {
        Optional<LeagueSquadAggregate> leagueSquadAggregateOptional = leagueSquadRepository.findById(String.valueOf(gameInfo.getLeagueId()));
        if(leagueSquadAggregateOptional.isPresent()){
            LeagueSquadAggregate leagueSquadAggregate = leagueSquadAggregateOptional.get();
            if(leagueSquadAggregate.getSquadMap().containsKey(teamId)){
                return leagueSquadAggregate.getSquadMap().get(teamId).getPlayers();
            }
        }

        return new ArrayList<>();

    }

}
