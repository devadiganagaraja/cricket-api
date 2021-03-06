package com.sport.cricket.cricketapi.service;


import com.cricketfoursix.cricketdomain.aggregate.LeagueAggregate;
import com.cricketfoursix.cricketdomain.common.league.LeagueSeason;
import com.cricketfoursix.cricketdomain.repository.LeagueRepository;
import com.sport.cricket.cricketapi.domain.response.Game;
import com.sport.cricket.cricketapi.domain.response.season.LiveGameInfo;
import com.sport.cricket.cricketapi.domain.response.season.PostGameInfo;
import com.sport.cricket.cricketapi.domain.response.season.ScheduledGameInfo;
import com.sport.cricket.cricketapi.domain.response.season.Season;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SeasonService {


    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    TeamNameService teamNameService;


    DateFormat df = new SimpleDateFormat("dd/MM/yy");
    DateFormat timeFormat = new SimpleDateFormat("hh.mm aa");





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

            Map<Integer, LeagueSeason> seasonMap = leagueAggregateOpt.get().getLeagueInfo().getLeagueSeasonMap();
            Optional<Integer> seasonIdOptional = null;
            if(seasonId > 0){
                seasonIdOptional = seasonMap.keySet().stream()
                    .filter(season -> season.equals(seasonId)).findFirst();
            }else{
                seasonIdOptional = Optional.ofNullable(Collections.max(seasonMap.keySet()));
            }

            if(seasonIdOptional.isPresent()) {
                LeagueSeason leagueSeason = leagueAggregateOpt.get().getLeagueInfo().getLeagueSeasonMap().get(seasonIdOptional.get());

                Season season = new Season();
                season.setYear(Integer.valueOf(leagueSeason.getLeagueYear()));
                season.setId(league.intValue());
                season.setName(leagueSeason.getName());
                season.setStartDate(leagueSeason.getStartDate());
                season.setEndDate(leagueSeason.getEndDate());
                //TODO season.setTeamNames(leagueSeason.getTeams().);

                populateSeasonPostGameInfo(leagueSeason, season);
                populateSeasonScheduledGameInfo(leagueSeason, season);
                populateSeasonLiveGameInfo(leagueSeason, season);
                season.setSeasons(new ArrayList<>(seasonMap.keySet()));
                season.setBattingLeaders(leagueSeason.getBattingLeaders());
                season.setBowlingLeaders(leagueSeason.getBowlingLeaders());
                season.setTeamGroups(leagueSeason.getTeamGroups());
                return season;
            }

        }
        return null;
    }

    private void populateSeasonPostGameInfo(LeagueSeason leagueSeason, Season season) {

        if(null != leagueSeason.getPostGames()){

            leagueSeason.getPostGames().forEach(gameInfo -> {

                PostGameInfo postGameInfo = new PostGameInfo();
                postGameInfo.setGameId(gameInfo.getGameId());
                postGameInfo.setTeam1Name(gameInfo.getTeam1Name());
                postGameInfo.setTeam2Name(gameInfo.getTeam2Name());
                postGameInfo.setGameDate(gameInfo.getDate());
                postGameInfo.setDateStr(df.format(gameInfo.getDate()));
                postGameInfo.setTeam1Score(gameInfo.getTeam1Score());
                postGameInfo.setTeam2Score(gameInfo.getTeam2Score());
                postGameInfo.setClassType(gameInfo.getGameClass().getShortName());
                postGameInfo.setNote(gameInfo.getNote());
                season.getPostGameInfoList().add(postGameInfo);
            });
            season.getPostGameInfoList().sort(Comparator.comparing(postGameInfo -> postGameInfo.getGameDate(), Comparator.reverseOrder()));
        }

    }


    private void populateSeasonLiveGameInfo(LeagueSeason leagueSeason, Season season) {

        if(null != leagueSeason.getLiveGames()){
                leagueSeason.getLiveGames().forEach(gameInfo -> {
                    LiveGameInfo liveGameInfo = new LiveGameInfo();
                    liveGameInfo.setGameId(gameInfo.getGameId());
                    liveGameInfo.setTeam1Name(gameInfo.getTeam1Name());
                    liveGameInfo.setTeam2Name(gameInfo.getTeam2Name());
                    liveGameInfo.setGameDate(gameInfo.getDate());
                    liveGameInfo.setDateStr(df.format(gameInfo.getDate()));
                    liveGameInfo.setTeam1Score(gameInfo.getTeam1Score());
                    liveGameInfo.setTeam2Score(gameInfo.getTeam2Score());
                    liveGameInfo.setClassType(gameInfo.getGameClass().getShortName());
                    liveGameInfo.setNote(gameInfo.getNote());
                    liveGameInfo.setGameStatus(gameInfo.getGameStatus());
                    season.getLiveGameInfoList().add(liveGameInfo);
            });
            populateSeasonTodayNotStartedGameInfo(leagueSeason, season);
            season.getLiveGameInfoList().sort(Comparator.comparing(postGameInfo -> postGameInfo.getGameDate()));
        }

    }



    private void populateSeasonTodayNotStartedGameInfo(LeagueSeason leagueSeason, Season season) {

        if(null != leagueSeason.getNextGames()){

            leagueSeason.getNextGames().forEach(gameInfo -> {
                if(DateUtils.isSameDay( gameInfo.getDate(), new Date())) {
                    LiveGameInfo liveGameInfo = new LiveGameInfo();
                    liveGameInfo.setGameId(gameInfo.getGameId());
                    liveGameInfo.setTeam1Name(gameInfo.getTeam1Name());
                    liveGameInfo.setTeam2Name(gameInfo.getTeam2Name());
                    liveGameInfo.setGameDate(gameInfo.getDate());
                    liveGameInfo.setDateStr(df.format(gameInfo.getDate()));
                    liveGameInfo.setGameStatus(gameInfo.getGameStatus());
                    liveGameInfo.setClassType(gameInfo.getGameClass().getShortName());
                    liveGameInfo.setNote(gameInfo.getNote());
                    season.getLiveGameInfoList().add(liveGameInfo);
                }
            });
        }

    }


    private void populateSeasonScheduledGameInfo(LeagueSeason leagueSeason, Season season) {

        if(null != leagueSeason.getNextGames()){

            leagueSeason.getNextGames().forEach(gameInfo -> {
                if(!DateUtils.isSameDay( gameInfo.getDate(), new Date())) {
                    ScheduledGameInfo scheduledGameInfo = new ScheduledGameInfo();
                    scheduledGameInfo.setGameId(gameInfo.getGameId());
                    scheduledGameInfo.setTeam1Name(gameInfo.getTeam1Name());
                    scheduledGameInfo.setTeam2Name(gameInfo.getTeam2Name());
                    scheduledGameInfo.setGameDate(gameInfo.getDate());
                    scheduledGameInfo.setDateStr(df.format(gameInfo.getDate()));
                    scheduledGameInfo.setTimeStr(timeFormat.format(gameInfo.getDate()));
                    scheduledGameInfo.setClassType(gameInfo.getGameClass().getShortName());
                    scheduledGameInfo.setVenue(gameInfo.getVenue());
                    season.getScheduledGameInfoList().add(scheduledGameInfo);
                }
            });
            season.getScheduledGameInfoList().sort(Comparator.comparing(scheduledGameInfo -> scheduledGameInfo.getGameDate()));
        }

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
