package com.sport.cricket.cricketapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sport.cricket.cricketapi.config.LeagueYamlConfig;
import com.sport.cricket.cricketapi.domain.response.League;
import com.sport.cricket.cricketapi.domain.response.User;
import com.sport.cricket.cricketapi.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeagueService {


    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    LeagueYamlConfig leagueYamlConfig;


    @Autowired
    RestTemplate restTemplate;

    public List<League> getLeagues(){
        return leagueRepository.findAll().stream().map(league -> populateDomainLeague(league)).collect(Collectors.toList());
    }

    public League getLeague(Integer league) {

        Optional<com.sport.cricket.cricketapi.persistance.League> leagueDbOpt = leagueRepository.findById(league);

        if(leagueDbOpt.isPresent()){
            return populateDomainLeague(leagueDbOpt.get());
        }else
            return null;
    }


    private League populateDomainLeague(com.sport.cricket.cricketapi.persistance.League league) {
        League leagueDomain = new League();
        leagueDomain.setId(league.getLeagueId());
        leagueDomain.setName(league.getName());
        leagueDomain.setAbbreviation(league.getAbbreviation());
        leagueDomain.setTournament(league.isTournament());
        return leagueDomain;
    }


    private com.sport.cricket.cricketapi.persistance.League populateDBLeague(League league) {
        com.sport.cricket.cricketapi.persistance.League leagueDB = new com.sport.cricket.cricketapi.persistance.League();
        leagueDB.setLeagueId(league.getId());
        leagueDB.setName(league.getName());
        leagueDB.setAbbreviation(league.getAbbreviation());
        leagueDB.setTournament(league.isTournament());
        return leagueDB;
    }


    @Scheduled(fixedRate = 500000l)
    private void populateLeagues() {

        leagueYamlConfig.getLeagues().forEach(leagueId ->{

            League league = restTemplate.getForObject("http://new.core.espnuk.org/v2/sports/cricket/leagues/"+leagueId, League.class);

            leagueRepository.save(populateDBLeague(league));

        });


    }



}
