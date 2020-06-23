package com.sport.cricket.cricketapi.service;

import com.sport.cricket.cricketapi.domain.persistance.QTeamAggregate;
import com.sport.cricket.cricketapi.domain.persistance.TeamAggregate;
import com.sport.cricket.cricketapi.domain.response.Team;
import com.sport.cricket.cricketapi.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    QTeamAggregate qTeamAggregate;



    public List<Team> getTeams(){
        return teamRepository.findAll().stream().map(team -> populateDomainTeam(team)).collect(Collectors.toList());
    }


    public void populateDBTeam(Team team) {

        Optional<TeamAggregate> teamAggregateOptional = teamRepository.findById(Long.valueOf(team.getId()));
        if(!teamAggregateOptional.isPresent()) {
            TeamAggregate teamDb = new TeamAggregate();
            teamDb.setName(team.getName());
            teamDb.setAbbreviation(team.getAbbreviation());
            teamDb.setId(Long.valueOf(team.getId()));
            teamDb.setNational(team.getIsNational());
        }
    }

    public Team populateDomainTeam(TeamAggregate teamAggregate) {
        Team teamDomain = new Team();
        teamDomain.setName(teamAggregate.getName());
        teamDomain.setAbbreviation(teamAggregate.getAbbreviation());
        teamDomain.setId(String.valueOf(teamAggregate.getId()));
        teamDomain.setNational(teamAggregate.isNational());
        return teamDomain;
    }


}
