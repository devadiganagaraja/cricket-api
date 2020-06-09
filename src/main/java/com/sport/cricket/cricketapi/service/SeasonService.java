package com.sport.cricket.cricketapi.service;

import com.sport.cricket.cricketapi.config.LeagueYamlConfig;
import com.sport.cricket.cricketapi.domain.response.*;
import com.sport.cricket.cricketapi.domain.source.Event;
import com.sport.cricket.cricketapi.domain.source.ItemListing;
import com.sport.cricket.cricketapi.domain.source.Ref;
import com.sport.cricket.cricketapi.persistance.QSeason;
import com.sport.cricket.cricketapi.repository.MatchRepository;
import com.sport.cricket.cricketapi.repository.SeasonRepository;
import com.sport.cricket.cricketapi.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeasonService {

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    TeamRepository teamRepository;


    @Autowired
    MatchRepository matchRepository;

    @Autowired
    LeagueYamlConfig leagueYamlConfig;

    @Autowired
    TeamService teamService;

    @Autowired
    MatchService matchService;


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    QSeason qSeason;

    public List<Season> getSeasons(Integer league) {
        List<Season> seasons = new ArrayList<>();
         seasonRepository.findAll(qSeason.leagueId.eq(league)).forEach(seasonDb -> {

            seasons.add(populateDomainSeason(seasonDb));
                });


        return seasons;
    }


    public Season getSeason(Integer league, Integer season) {
        Optional<com.sport.cricket.cricketapi.persistance.Season> seasonDbOpt =  seasonRepository.findById(league+"_"+season);
        if(seasonDbOpt.isPresent())
            return populateDomainSeason(seasonDbOpt.get());
        else
            return null;
    }

    public List<Team> getSeasonTeams(Integer league, Integer season){
        List<Team> teams = new ArrayList<>();
        String seasonKey = league+"_"+season;
        Optional<com.sport.cricket.cricketapi.persistance.Season> seasonDbOpt =  seasonRepository.findById(seasonKey);
        if(seasonDbOpt.isPresent()) {
            seasonDbOpt.get().getTeams().forEach(team -> {

                Optional<com.sport.cricket.cricketapi.persistance.Team> teamDbOpt = teamRepository.findById(team);

                if (teamDbOpt.isPresent()) {

                    teams.add(teamService.populateDomainTeam(teamDbOpt.get()));
                }
            });


        }
        return teams;
    }

    public List<Match> getSeasonMatches(Integer league, Integer season) {

        List<Match> matches = new ArrayList<>();
        String seasonKey = league+"_"+season;

        System.out.println("seasonKey==>"+seasonKey);
        Optional<com.sport.cricket.cricketapi.persistance.Season> seasonDbOpt =  seasonRepository.findById(seasonKey);

        if(seasonDbOpt.isPresent()) {
            System.out.println("seasonDbOpt==>"+seasonDbOpt.get());
            seasonDbOpt.get().getMatches().forEach(match -> {
                Optional<com.sport.cricket.cricketapi.persistance.Match> matchDbOpt = matchRepository.findById(match);

                if (matchDbOpt.isPresent()) {
                    System.out.println("matchDbOpt==>"+matchDbOpt.get());

                    matches.add(matchService.populateDomainMatch(matchDbOpt.get()));

                }
            });
        }
        return matches;
    }


    private com.sport.cricket.cricketapi.persistance.Season populateDBSeason(Season season) {
        com.sport.cricket.cricketapi.persistance.Season seasonDb = new com.sport.cricket.cricketapi.persistance.Season();
        seasonDb.setEndDate(season.getEndDate());
        seasonDb.setStartDate(season.getStartDate());
        seasonDb.setName(season.getName());
        seasonDb.setShortName(season.getShortName());
        seasonDb.setYear(season.getYear());
        seasonDb.setLeagueId(Integer.valueOf(season.getId()));
        seasonDb.setLeagueSeasonId(season.getId()+"_"+season.getYear());
        return seasonDb;
    }

    private Season populateDomainSeason(com.sport.cricket.cricketapi.persistance.Season season) {
        Season seasonDomain = new Season();
        seasonDomain.setId(season.getLeagueId());
        seasonDomain.setStartDate(season.getStartDate());
        seasonDomain.setEndDate(season.getEndDate());
        seasonDomain.setName(season.getName());
        seasonDomain.setShortName(season.getShortName());
        seasonDomain.setYear(season.getYear());
        return seasonDomain;
    }


    @Scheduled(fixedRate = 500000l)
    private void populateSeasons() {

        leagueYamlConfig.getLeagues().forEach(leagueId -> {

            ItemListing seasonsListing = restTemplate.getForObject("http://new.core.espnuk.org/v2/sports/cricket/leagues/" + leagueId + "/seasons", ItemListing.class);


            if (null != seasonsListing.getItems()) {
                List<Ref> seasons = seasonsListing.getItems();
                if (null != seasons) {
                    seasons.forEach(seasonRef -> {
                        Season season = restTemplate.getForObject(
                                seasonRef.get$ref(), Season.class);

                        if (null != season) {
                            seasonRepository.save(populateDBSeason(season));
                        }

                    });


                }
            }
        });
    }


    @Scheduled(fixedRate = 500000l)
    private void populateTeams() {
        seasonRepository.findAll().forEach(season -> {
            ItemListing teamListing = restTemplate.getForObject("http://new.core.espnuk.org/v2/sports/cricket/leagues/" + season.getLeagueId() + "/seasons/"+season.getYear()+"/teams", ItemListing.class);


            if (null != teamListing.getItems()) {
                List<Ref> teams = teamListing.getItems();
                if (null != teams) {
                    teams.forEach(teamRef -> {
                        Team team = restTemplate.getForObject(
                                teamRef.get$ref(), Team.class);

                        if (null != team) {
                            teamRepository.save(teamService.populateDBTeam(team));
                            season.getTeams().add(Long.valueOf(team.getId()));
                        }
                    });
                }
            }
            seasonRepository.save(season);
        });


    }



    @Scheduled(fixedRate = 500000l)
    private void populateMatches() {
        seasonRepository.findAll().forEach(season -> {
            ItemListing eventListing = restTemplate.getForObject("http://new.core.espnuk.org/v2/sports/cricket/leagues/" + season.getLeagueId() + "/seasons/"+season.getYear()+"/events", ItemListing.class);
            if (null != eventListing.getItems()) {
                List<Ref> events = eventListing.getItems();
                if (null != events) {
                    events.forEach(eventRef -> {
                        Event event = restTemplate.getForObject(eventRef.get$ref(), Event.class);

                        if (null != event) {
                            matchRepository.save(matchService.populateDBMatch(event));
                            season.getMatches().add(event.getId());
                        }
                    });
                }
            }
            seasonRepository.save(season);
        });


    }


}
