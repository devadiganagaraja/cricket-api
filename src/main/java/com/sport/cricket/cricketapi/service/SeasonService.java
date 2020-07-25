package com.sport.cricket.cricketapi.service;


import com.cricketfoursix.cricketdomain.aggregate.LeagueAggregate;
import com.cricketfoursix.cricketdomain.common.league.LeagueSeason;
import com.cricketfoursix.cricketdomain.repository.LeagueRepository;
import com.sport.cricket.cricketapi.domain.response.Game;
import com.sport.cricket.cricketapi.domain.response.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeasonService {


    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    TeamNameService teamNameService;





    public List<Season> getSeasons(Long league) {
        List<Season> seasonList = new ArrayList<>();
        Optional<LeagueAggregate> leagueAggregateOpt = leagueRepository.findById(league);
        if(leagueAggregateOpt.isPresent()){
            leagueAggregateOpt.get().getLeagueInfo().getLeagueSeasonMap().values().stream().forEach(leagueSeason -> {
                Season season = new Season();
                season.setYear(Integer.valueOf(leagueSeason.getLeagueYear()));
                season.setId(league.intValue());
                season.setName(leagueSeason.getName());
                season.setStartDate(leagueSeason.getStartDate());
                season.setEndDate(leagueSeason.getEndDate());
                //TODO season.setTeamNames(leagueSeason.getTeams().);
                seasonList.add(season);

            });
        }
        return seasonList;
    }

    public Season getSeason(Long league, Integer seasonId) {
        Optional<LeagueAggregate> leagueAggregateOpt = leagueRepository.findById(league);
        if(leagueAggregateOpt.isPresent()){
            Optional<Integer> seasonIdOptional = leagueAggregateOpt.get().getLeagueInfo().getLeagueSeasonMap().keySet().stream()
                    .filter(season -> season.equals(seasonId)).findFirst();

            if(seasonIdOptional.isPresent()) {
                LeagueSeason leagueSeason = leagueAggregateOpt.get().getLeagueInfo().getLeagueSeasonMap().get(seasonIdOptional.get());


                Season season = new Season();
                season.setYear(Integer.valueOf(leagueSeason.getLeagueYear()));
                season.setId(league.intValue());
                season.setName(leagueSeason.getName());
                season.setStartDate(leagueSeason.getStartDate());
                season.setEndDate(leagueSeason.getEndDate());
                //TODO season.setTeamNames(leagueSeason.getTeams().);
                return season;
            }

        }
        return null;
    }

    public List<String> getSeasonTeams(Long league, Integer seasonYear) {
        List<String> teams = new ArrayList<>();

        Optional<LeagueAggregate> leagueAggregateOpt = leagueRepository.findById(league);
        if(leagueAggregateOpt.isPresent()){
            Optional<Integer> seasonIdOptional = leagueAggregateOpt.get().getLeagueInfo().getLeagueSeasonMap().keySet().stream()
                    .filter(season -> season.equals(seasonYear)).findFirst();

            if(seasonIdOptional.isPresent()) {
                LeagueSeason leagueSeason = leagueAggregateOpt.get().getLeagueInfo().getLeagueSeasonMap().get(seasonIdOptional.get());

                leagueSeason.getTeams().stream().forEach(leagueTeam -> {

                    teams.add(teamNameService.getTeamNameByTeamId(leagueTeam.getId()));

                });
            }

        }

        return teams;
    }

    public List<Game> getSeasonGames(Long league, Integer seasonYear) {
        List<Game> games = new ArrayList<>();

        Optional<LeagueAggregate> leagueAggregateOpt = leagueRepository.findById(league);
        if(leagueAggregateOpt.isPresent()){
            Optional<Integer> seasonIdOptional = leagueAggregateOpt.get().getLeagueInfo().getLeagueSeasonMap().keySet().stream()
                    .filter(season -> season.equals(seasonYear)).findFirst();

            if(seasonIdOptional.isPresent()) {
                LeagueSeason leagueSeason = leagueAggregateOpt.get().getLeagueInfo().getLeagueSeasonMap().get(seasonIdOptional.get());
                leagueSeason.getEventSet().stream().forEach(gameInfo -> {
                    Game game = new Game();
                    game.setName(gameInfo.getName());
                    game.setId(gameInfo.getGameId());
                    game.setLeagueId(gameInfo.getLeagueId());
                    game.setSeason(gameInfo.getSeason());
                    game.setGameStatus(gameInfo.getGameStatus().toString());
                    game.setDate(gameInfo.getDate());
                    game.setTeam1Name( teamNameService.getTeamNameByTeamId(gameInfo.getCompetitor1()));
                    game.setTeam2Name( teamNameService.getTeamNameByTeamId(gameInfo.getCompetitor2()));
                    game.setGameClass(gameInfo.getGameClass().getName());
                    games.add((game));

                });
            }

        }

        return games;
    }
}
