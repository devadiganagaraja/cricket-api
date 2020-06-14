package com.sport.cricket.cricketapi.service;

import com.sport.cricket.cricketapi.config.LeagueYamlConfig;
import com.sport.cricket.cricketapi.domain.persistance.LeagueAggregate;
import com.sport.cricket.cricketapi.domain.response.League;
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

        Optional<LeagueAggregate> leagueDbOpt = leagueRepository.findById(league);

        if(leagueDbOpt.isPresent()){
            return populateDomainLeague(leagueDbOpt.get());
        }else
            return null;
    }


    private League populateDomainLeague(LeagueAggregate leagueAggregate) {
        League leagueDomain = new League();
        leagueDomain.setId(leagueAggregate.getLeagueId());
        leagueDomain.setName(leagueAggregate.getName());
        leagueDomain.setAbbreviation(leagueAggregate.getAbbreviation());
        leagueDomain.setTournament(leagueAggregate.isTournament());
        return leagueDomain;
    }


    private LeagueAggregate populateDBLeague(League league) {
        LeagueAggregate leagueAggregate = new LeagueAggregate();
        leagueAggregate.setLeagueId(league.getId());
        leagueAggregate.setName(league.getName());
        leagueAggregate.setAbbreviation(league.getAbbreviation());
        leagueAggregate.setTournament(league.isTournament());
        return leagueAggregate;
    }


    @Scheduled(fixedRate = 500000l)
    private void populateLeagues() {

        leagueYamlConfig.getLeagues().forEach(leagueId ->{

            League league = restTemplate.getForObject("http://new.core.espnuk.org/v2/sports/cricket/leagues/"+leagueId, League.class);

            leagueRepository.save(populateDBLeague(league));

        });


    }



}
