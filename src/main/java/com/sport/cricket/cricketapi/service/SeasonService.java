package com.sport.cricket.cricketapi.service;

import com.cricketfoursix.cricketdomain.aggregate.GameAggregate;
import com.cricketfoursix.cricketdomain.repository.GameRepository;
import com.sport.cricket.cricketapi.config.LeagueYamlConfig;
import com.sport.cricket.cricketapi.domain.persistance.QSeasonAggregate;
import com.sport.cricket.cricketapi.domain.persistance.SeasonAggregate;
import com.sport.cricket.cricketapi.domain.response.*;
import com.sport.cricket.cricketapi.domain.source.Event;
import com.sport.cricket.cricketapi.domain.source.ItemListing;
import com.sport.cricket.cricketapi.domain.source.Ref;
import com.sport.cricket.cricketapi.repository.SeasonRepository;
import com.sport.cricket.cricketapi.repository.TeamRepository;
import com.sport.cricket.cricketapi.task.SeasonGameRefreshTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
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
    GameRepository gameRepository;

    @Autowired
    LeagueYamlConfig leagueYamlConfig;

    @Autowired
    TeamService teamService;

    @Autowired
    GameService gameService;


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    QSeasonAggregate qSeasonAggregate;


    @Autowired
    TaskExecutor threadPoolTaskExecutor;


    @Autowired
    TeamNameService teamNameService;

    public List<Season> getSeasons(Integer league) {
        List<Season> seasons = new ArrayList<>();
         seasonRepository.findAll(qSeasonAggregate.leagueId.eq(league)).forEach(seasonDb -> {
             Season season = populateDomainSeason(seasonDb);
             seasons.add(season);
         });


        return seasons;
    }


    public Season getSeason(Integer league, Integer season) {
        Optional<SeasonAggregate> seasonDbOpt =  seasonRepository.findById(league+"_"+season);
        if(seasonDbOpt.isPresent())
            return populateDomainSeason(seasonDbOpt.get());
        else
            return null;
    }

    public List<String> getSeasonTeams(Integer league, Integer season){
        List<String> teams = new ArrayList<>();
        String seasonKey = league+"_"+season;
        Optional<SeasonAggregate> seasonAggregateOptional =  seasonRepository.findById(seasonKey);
        if(seasonAggregateOptional.isPresent()) {
            seasonAggregateOptional.get().getTeamIds().forEach( teamId -> {
                teams.add(teamNameService.getTeamNameByTeamId(teamId));
            });


        }
        return teams;
    }

    public List<Game> getSeasonGames(Integer league, Integer season) {

        List<Game> games = new ArrayList<>();
        String seasonKey = league+"_"+season;

        System.out.println("seasonKey==>"+seasonKey);
        Optional<SeasonAggregate> seasonAggregateOpt =  seasonRepository.findById(seasonKey);

        if(seasonAggregateOpt.isPresent()) {
            seasonAggregateOpt.get().getGameIds().forEach(match -> {
                Optional<GameAggregate> matchAggregateOptional = gameRepository.findById(match);

                if (matchAggregateOptional.isPresent()) {

                    games.add(gameService.populateDomainMatch(matchAggregateOptional.get()));

                }
            });
        }
        return games;
    }


    private SeasonAggregate populateDBSeason(Season season) {
        SeasonAggregate seasonDb = new SeasonAggregate();
        seasonDb.setEndDate(season.getEndDate());
        seasonDb.setStartDate(season.getStartDate());
        seasonDb.setName(season.getName());
        seasonDb.setShortName(season.getShortName());
        seasonDb.setYear(season.getYear());
        seasonDb.setLeagueId(Integer.valueOf(season.getId()));
        seasonDb.setLeagueSeasonId(season.getId()+"_"+season.getYear());
        return seasonDb;
    }

    private Season populateDomainSeason(SeasonAggregate seasonAggregate) {
        Season seasonDomain = new Season();
        seasonDomain.setId(seasonAggregate.getLeagueId());
        seasonDomain.setStartDate(seasonAggregate.getStartDate());
        seasonDomain.setEndDate(seasonAggregate.getEndDate());
        seasonDomain.setName(seasonAggregate.getName());
        seasonDomain.setShortName(seasonAggregate.getShortName());
        seasonDomain.setYear(seasonAggregate.getYear());
        return seasonDomain;
    }


    /*
        season will run with a fixed delay of 1 day with initial 1O sec delay
      */
    @Scheduled(fixedDelay = 86400000, initialDelay = 10000)
    private void populateSeasons() {

        leagueYamlConfig.getLeagues().forEach(leagueId -> {

            ItemListing seasonsListing = restTemplate.getForObject("http://new.core.espnuk.org/v2/sports/cricket/leagues/" + leagueId + "/seasons", ItemListing.class);


            if (null != seasonsListing.getItems()) {
                List<Ref> seasons = seasonsListing.getItems();
                if (null != seasons) {
                    seasons.forEach(seasonRef -> {
                        Season seasonResponse = restTemplate.getForObject(
                                seasonRef.get$ref(), Season.class);

                        if (null != seasonResponse) {
                            SeasonAggregate season = populateDBSeason(seasonResponse);
                            populateGames(season);
                            populateTeams(season);
                            seasonRepository.save(season);
                        }

                    });


                }
            }
        });
    }

    private void populateTeams(SeasonAggregate season) {
        ItemListing teamListing = restTemplate.getForObject("http://new.core.espnuk.org/v2/sports/cricket/leagues/" + season.getLeagueId() + "/seasons/"+season.getYear()+"/teams", ItemListing.class);
        if (null != teamListing.getItems()) {
            List<Ref> teams = teamListing.getItems();
            if (null != teams) {
                teams.forEach(teamRef -> {
                    Team team = restTemplate.getForObject(
                            teamRef.get$ref(), Team.class);

                    if (null != team) {
                        teamService.populateDBTeam(team);
                        season.getTeamIds().add(Long.valueOf(team.getId())*13);
                    }
                });
            }
        }
    }

    private void populateGames(SeasonAggregate season) {
        ItemListing eventListing = restTemplate.getForObject("http://new.core.espnuk.org/v2/sports/cricket/leagues/" + season.getLeagueId() + "/seasons/"+season.getYear()+"/events", ItemListing.class);
        if (null != eventListing.getItems()) {
            List<Ref> events = eventListing.getItems();
            if (null != events) {
                events.forEach(eventRef -> {
                    Event event = restTemplate.getForObject(eventRef.get$ref(), Event.class);

                    if (null != event) {
                        threadPoolTaskExecutor.execute(new SeasonGameRefreshTask(event, season,gameService));
                        season.getGameIds().add(event.getId()*13);
                    }
                });
            }
        }
    }


}
