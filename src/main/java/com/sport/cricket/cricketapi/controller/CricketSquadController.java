package com.sport.cricket.cricketapi.controller;

import com.cricketfoursix.cricketdomain.aggregate.GameAggregate;
import com.cricketfoursix.cricketdomain.aggregate.GamePlayerPointsAggregate;
import com.cricketfoursix.cricketdomain.aggregate.UserEventSquadAggregate;
import com.cricketfoursix.cricketdomain.common.game.GameInfo;
import com.cricketfoursix.cricketdomain.common.squad.PlayerPoints;
import com.cricketfoursix.cricketdomain.common.squad.UserSquadPlayer;
import com.cricketfoursix.cricketdomain.repository.GamePlayerPointsRepository;
import com.cricketfoursix.cricketdomain.repository.GameRepository;
import com.cricketfoursix.cricketdomain.repository.UserEventSquadRepository;
import com.sport.cricket.cricketapi.domain.response.squad.*;
import com.sport.cricket.cricketapi.service.EventSquadsService;
import com.sport.cricket.cricketapi.service.PlayerNameService;
import com.sport.cricket.cricketapi.service.TeamNameService;
import com.sport.cricket.cricketapi.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@CrossOrigin
public class CricketSquadController {


    private static final Logger logger = LoggerFactory.getLogger(CricketSquadController.class);



    @Autowired
    EventSquadsService eventSquadsService;



    @Autowired
    UserService userService;

    @Autowired
    PlayerNameService playerNameService;

    @Autowired
    TeamNameService teamNameService;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserEventSquadRepository userEventSquadRepository;

    @Autowired
    GamePlayerPointsRepository gamePlayerPointsRepository;

    @CrossOrigin
    @RequestMapping("/bestEleven/leaderboard/games/{gameId}")
    public List<LeaderBoard> leaderBoard(@PathVariable(value="gameId") Long gameId) {
        Optional<GamePlayerPointsAggregate> eventPlayerPointsAggregateOptional = gamePlayerPointsRepository.findById(gameId);

        List<LeaderBoard> leaderBoards = new ArrayList<>();
        if(eventPlayerPointsAggregateOptional.isPresent()) {
            GamePlayerPointsAggregate eventPlayerPointsAggregate = eventPlayerPointsAggregateOptional.get();
            UserEventSquadAggregate userEventSquadAggregateEx = new UserEventSquadAggregate();
            userEventSquadAggregateEx.setGameId(gameId);
            List<UserEventSquadAggregate> userEventSquadAggregateList = userEventSquadRepository.findAll(Example.of(userEventSquadAggregateEx));
            if (null != userEventSquadAggregateList) {
                userEventSquadAggregateList.forEach(userEventSquadAggregate -> {
                    if(null != userEventSquadAggregate && null != userEventSquadAggregate.getUserSquadPlayers()){
                        LeaderBoard leaderBoard = new LeaderBoard();
                        leaderBoard.setUserName(userEventSquadAggregate.getUserName());
                        userEventSquadAggregate.getUserSquadPlayers().forEach(userSquadPlayer ->  {
                            if(eventPlayerPointsAggregate.getPlayerPointsMap().containsKey(playerNameService.getPlayerId(userSquadPlayer.getPlayerName()))) {
                                leaderBoard.setPoints(leaderBoard.getPoints()+ (eventPlayerPointsAggregate.getPlayerPointsMap().get(playerNameService.getPlayerId(userSquadPlayer.getPlayerName())).getPoints()));
                            }
                        });
                        leaderBoards.add(leaderBoard);
                    }

                });
            }

        }
        leaderBoards.sort(Comparator.comparing(LeaderBoard::getPoints, Comparator.reverseOrder()));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        leaderBoards.forEach(leaderBoard -> leaderBoard.setPosition(atomicInteger.getAndIncrement()));

        return leaderBoards;


    }

    @CrossOrigin
    @RequestMapping("/bestEleven/userName/{userName}/games/{gameId}")
    public EventBestEleven getBestEleven(@PathVariable(value="userName") String userName, @PathVariable(value="gameId") Long gameId) {

        EventBestEleven eventBestEleven = new EventBestEleven();
        Optional<GameAggregate> gameAggregateOptional = gameRepository.findById(gameId);
        if(gameAggregateOptional.isPresent()) {
            GameAggregate gameAggregate = gameAggregateOptional.get();
            GameInfo gameInfo = gameAggregate.getGameInfo();
            if (null != gameInfo) {
                logger.info("eventBestEleven==>event :{}", gameInfo);
                eventBestEleven.setGameInfo(gameInfo);
                Squad squad1 = new Squad();
                squad1.setTeamName(teamNameService.getTeamNameByTeamId(gameAggregate.getCompetitor1().getId()));
                long sourceTeam1Id = gameAggregate.getCompetitor1().getId() / 13;

                squad1.setPlayers(eventSquadsService.getLeagueTeamPlayers(sourceTeam1Id, gameInfo));

                eventBestEleven.setSquad1(squad1);
                Squad squad2 = new Squad();
                squad2.setTeamName(teamNameService.getTeamNameByTeamId(gameAggregate.getCompetitor2().getId()));
                long sourceTeam2Id = gameAggregate.getCompetitor2().getId() / 13;
                squad2.setPlayers(eventSquadsService.getLeagueTeamPlayers(sourceTeam2Id, gameInfo));
                eventBestEleven.setSquad2(squad2);

                Optional<UserEventSquadAggregate> userEventSquadAggregateOptional = userEventSquadRepository.findById(userName + ":" + gameId);
                if (userEventSquadAggregateOptional.isPresent()) {
                    UserSquad userSquad = new UserSquad();
                    List<UserSquadPlayer> userSquadPlayers = userEventSquadAggregateOptional.get().getUserSquadPlayers();
                    logger.info("eventPlayerPointsRepository  => gameId :{}", gameId);
                    Optional<GamePlayerPointsAggregate> eventPlayerPointsAggregateOptional = gamePlayerPointsRepository.findById(gameId);
                    if (eventPlayerPointsAggregateOptional.isPresent()) {
                        GamePlayerPointsAggregate eventPlayerPointsAggregate = eventPlayerPointsAggregateOptional.get();
                        Map<Long, PlayerPoints> playerPointsMap = eventPlayerPointsAggregate.getPlayerPointsMap();
                        if (null != playerPointsMap) {
                            userSquadPlayers.stream().forEach(userSquadPlayer -> {

                                logger.info("eventPlayerPointsRepository  => userSquadPlayer.getPlayerName() :{}", userSquadPlayer.getPlayerName());

                                long squadPlayerId = Long.valueOf(userSquadPlayer.getPlayerName().split(":")[1]);
                                if (playerPointsMap.containsKey(squadPlayerId)) {
                                    PlayerPoints playerPoints = playerPointsMap.get(squadPlayerId);
                                    logger.info("eventPlayerPointsRepository  => userSquadPlayer.getPlayerName() :{}, playerPoints :{}", userSquadPlayer.getPlayerName(), playerPoints);
                                    playerPoints.setPoints(userSquadPlayer.isCaptain() ? playerPoints.getPoints() * 3 : userSquadPlayer.isVoiceCaptain() ? playerPoints.getPoints() * 2 : playerPoints.getPoints());
                                    userSquadPlayer.setPoints(playerPoints);
                                    userSquad.setTotalPoints(userSquad.getTotalPoints() + playerPoints.getPoints());
                                }
                            });
                        }
                    }
                    userSquad.setUserSquadPlayers(userSquadPlayers);
                    eventBestEleven.setUserSquad(userSquad);
                }

            }
        }
        return eventBestEleven;
    }


    @CrossOrigin
    @PostMapping("/bestEleven/games/{gameId}")
    public EventBestEleven postBestEleven(@PathVariable(value="gameId") Long gameId, @RequestBody BestEleven bestEleven) {
        EventBestEleven eventBestEleven = new EventBestEleven();
        Optional<GameAggregate> gameAggregateOptional = gameRepository.findById(gameId);
        if(gameAggregateOptional.isPresent()) {
            GameAggregate gameAggregate = gameAggregateOptional.get();
            GameInfo gameInfo = gameAggregate.getGameInfo();
            eventBestEleven.setGameInfo(gameInfo);
            Squad squad1 = new Squad();
            squad1.setTeamName(teamNameService.getTeamNameByTeamId(gameAggregate.getCompetitor1().getId()));
            long sourceTeam1Id = gameAggregate.getCompetitor1().getId() / 13;

            squad1.setPlayers(eventSquadsService.getLeagueTeamPlayers(sourceTeam1Id, gameInfo));

            eventBestEleven.setSquad1(squad1);
            Squad squad2 = new Squad();
            squad2.setTeamName(teamNameService.getTeamNameByTeamId(gameAggregate.getCompetitor2().getId()));
            long sourceTeam2Id = gameAggregate.getCompetitor2().getId() / 13;

            squad2.setPlayers(eventSquadsService.getLeagueTeamPlayers( sourceTeam2Id, gameInfo));
            eventBestEleven.setSquad2(squad2);


            if (userService.authenticateUser(bestEleven.getUserName(), bestEleven.getPassword())) {
                UserSquad userSquad = new UserSquad();
                userSquad.setUserSquadPlayers(new ArrayList<>());
                if (StringUtils.isNotBlank(bestEleven.getPlayerIds())) {
                    Arrays.stream(bestEleven.getPlayerIds().split(","))
                            .forEach(player -> {
                                long playerId = Long.parseLong(player.split(":")[0]);
                                long teamId = Long.parseLong(player.split(":")[1]);
                                UserSquadPlayer userSquadPlayer = new UserSquadPlayer();
                                userSquadPlayer.setPlayerName(playerNameService.getPlayerName(playerId / 13));
                                userSquadPlayer.setTeamName(teamNameService.getTeamName(teamId / 13));
                                userSquadPlayer.setPoints(new PlayerPoints());
                                userSquad.getUserSquadPlayers().add(userSquadPlayer);
                            });

                }
                saveUserEventSquad(bestEleven.getUserName(), gameId, userSquad.getUserSquadPlayers());
                eventBestEleven.setUserSquad(userSquad);
            }
        }
        return eventBestEleven;

    }
    private void saveUserEventSquad(String userName, Long gameId, List<UserSquadPlayer> userSquadPlayers){
        UserEventSquadAggregate userEventSquadAggregate = new UserEventSquadAggregate();
        userEventSquadAggregate.setUserEventId(userName+":"+gameId);
        userEventSquadAggregate.setGameId(gameId);
        userEventSquadAggregate.setUserName(userName);
        userEventSquadAggregate.setUserSquadPlayers(userSquadPlayers);
        userEventSquadRepository.save(userEventSquadAggregate);
    }
}
