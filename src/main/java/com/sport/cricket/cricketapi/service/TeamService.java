package com.sport.cricket.cricketapi.service;

import com.sport.cricket.cricketapi.domain.response.Team;
import com.sport.cricket.cricketapi.persistance.QTeam;
import com.sport.cricket.cricketapi.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    QTeam qTeam;



    public List<Team> getTeams(){
        return teamRepository.findAll().stream().map(team -> populateDomainTeam(team)).collect(Collectors.toList());
    }


    public   com.sport.cricket.cricketapi.persistance.Team populateDBTeam(Team team) {
        com.sport.cricket.cricketapi.persistance.Team teamDb = new com.sport.cricket.cricketapi.persistance.Team();
        teamDb.setName(team.getName());
        teamDb.setAbbreviation(team.getAbbreviation());
        teamDb.setId(Long.valueOf(team.getId()));
        teamDb.setNational(team.getIsNational());
        return teamDb;
    }

    public Team populateDomainTeam(com.sport.cricket.cricketapi.persistance.Team team) {
        Team teamDomain = new Team();
        teamDomain.setName(team.getName());
        teamDomain.setAbbreviation(team.getAbbreviation());
        teamDomain.setId(String.valueOf(team.getId()));
        teamDomain.setNational(team.isNational());
        return teamDomain;
    }


}
