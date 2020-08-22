package com.sport.cricket.cricketapi.service;

import com.cricketfoursix.cricketdomain.aggregate.GameAggregate;
import com.cricketfoursix.cricketdomain.aggregate.LeagueIndexAggregate;
import com.cricketfoursix.cricketdomain.aggregate.QGameAggregate;
import com.cricketfoursix.cricketdomain.common.game.GameInfo;
import com.cricketfoursix.cricketdomain.common.game.GameStatus;
import com.cricketfoursix.cricketdomain.common.league.LeagueType;
import com.cricketfoursix.cricketdomain.repository.GameRepository;
import com.cricketfoursix.cricketdomain.repository.LeagueIndexRepository;
import com.sport.cricket.cricketapi.domain.response.home.CricketHome;
import com.sport.cricket.cricketapi.domain.response.league.LeagueDetails;
import com.sport.cricket.cricketapi.domain.response.season.LiveGameInfo;
import com.sport.cricket.cricketapi.domain.response.season.PostGameInfo;
import com.sport.cricket.cricketapi.domain.response.season.PreGameInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CricketHomeService {

    @Autowired
    LeagueIndexRepository leagueIndexRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    QGameAggregate qGameAggregate;

    DateFormat df = new SimpleDateFormat("dd/MM/yy");
    DateFormat timeFormat = new SimpleDateFormat("hh.mm aa");


    @Autowired
    TeamNameService teamNameService;




    public CricketHome populateCricketHomePage() {

        CricketHome cricketHome = new CricketHome();

        populateLeagues(cricketHome);
        populatePostGames(cricketHome);
        populateLiveGames(cricketHome);
        populatePreGames(cricketHome);

        return cricketHome;
    }

    private void populatePreGames(CricketHome cricketHome) {
        Instant now = Instant.now();
        Instant dayAfterToomorow = now.plus(3, ChronoUnit.DAYS);
        Iterable<GameAggregate> gameAggregates = gameRepository.findAll(qGameAggregate.gameInfo.gameStatus.eq(GameStatus.pre).and(qGameAggregate.gameInfo.date.loe(Date.from(dayAfterToomorow))));
        gameAggregates.forEach(gameAggregate -> {
                if(null != gameAggregate.getGameInfo()) {
                    GameInfo gameInfo = gameAggregate.getGameInfo();
                    if(null != gameInfo.getGameClass() && gameInfo.getGameClass().getId() <= 6) {
                        PreGameInfo preGameInfo = new PreGameInfo();
                        preGameInfo.setGameId(gameInfo.getGameId());
                        preGameInfo.setTeam1Name(gameInfo.getTeam1Name());
                        preGameInfo.setTeam2Name(gameInfo.getTeam2Name());
                        preGameInfo.setGameDate(gameInfo.getDate());
                        preGameInfo.setDateStr(df.format(gameInfo.getDate()));
                        preGameInfo.setTimeStr(timeFormat.format(gameInfo.getDate()));
                        preGameInfo.setClassType(gameInfo.getGameClass().getShortName());
                        preGameInfo.setVenue(gameInfo.getVenue());
                        cricketHome.getPreGameInfoList().add(preGameInfo);
                    }
                }
            });
        cricketHome.getPreGameInfoList().sort(Comparator.comparing(preGameInfo -> preGameInfo.getGameDate()));
    }

    private void populateLiveGames(CricketHome cricketHome) {
        Iterable<GameAggregate> gameAggregates = gameRepository.findAll(qGameAggregate.gameInfo.gameStatus.eq(GameStatus.live));


        gameAggregates.forEach(gameAggregate -> {
            if(null != gameAggregate.getGameInfo()) {
                GameInfo gameInfo = gameAggregate.getGameInfo();
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
                cricketHome.getLiveGameInfoList().add(liveGameInfo);
            }
            });
    }

    private void populatePostGames(CricketHome cricketHome) {
        Instant now = Instant.now();
        Instant dayBeforeYesterday = now.minus(3, ChronoUnit.DAYS);

        Iterable<GameAggregate> gameAggregates = gameRepository.findAll(qGameAggregate.gameInfo.gameStatus.eq(GameStatus.post).and(qGameAggregate.gameInfo.endDate.goe(Date.from(dayBeforeYesterday))));

        gameAggregates.forEach(gameAggregate -> {
            if(null != gameAggregate.getGameInfo()) {

                GameInfo gameInfo = gameAggregate.getGameInfo();
                if(null != gameInfo.getGameClass() && gameInfo.getGameClass().getId() <= 6) {

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
                    cricketHome.getPostGameInfoList().add(postGameInfo);
                }
            }
        });
        cricketHome.getPostGameInfoList().sort(Comparator.comparing(postGameInfo -> postGameInfo.getGameDate()));


    }

    private void populateLeagues(CricketHome cricketHome) {
        List<LeagueIndexAggregate> leagueIndexAggregateList = leagueIndexRepository.findAll();
        if(null != leagueIndexAggregateList){
            leagueIndexAggregateList.forEach(leagueIndexAggregate -> {
                LeagueDetails leagueDetails = new LeagueDetails();
                leagueDetails.setId(leagueIndexAggregate.getLeagueId());
                leagueDetails.setName(leagueIndexAggregate.getName());
                leagueDetails.setAbbreviation(leagueIndexAggregate.getAbbreviation());
                if(LeagueType.tournament.equals(leagueIndexAggregate.getLeagueType())){
                    leagueDetails.setTournament(true);
                    cricketHome.getTournaments().add(leagueDetails);
                }else{
                    cricketHome.getTours().add(leagueDetails);
                }
            });
        }
    }
}
