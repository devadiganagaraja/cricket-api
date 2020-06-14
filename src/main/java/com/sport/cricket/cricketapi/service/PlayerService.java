package com.sport.cricket.cricketapi.service;

import com.sport.cricket.cricketapi.domain.common.PlayerStat;
import com.sport.cricket.cricketapi.domain.persistance.PlayerAggregate;
import com.sport.cricket.cricketapi.domain.response.Player;
import com.sport.cricket.cricketapi.domain.source.Athlete;
import com.sport.cricket.cricketapi.domain.source.AthleteStat;
import com.sport.cricket.cricketapi.domain.source.Category;
import com.sport.cricket.cricketapi.domain.source.Style;
import com.sport.cricket.cricketapi.repository.PlayerRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;


    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    TeamNameService teamNameService;

    @Autowired
    PlayerNameService playerNameService;

    public Player getPlayerDetails(long playerId){
        Player player = getPlayerInfo(playerId);
        return player;
    }

    public Player populatePlayer(PlayerAggregate playerAggregate){
        Player player = new Player();
        player.setId(playerAggregate.getId());
        player.setName(playerAggregate.getName());
        player.setAge(playerAggregate.getAge());
        player.setBattingStyle(playerAggregate.getBattingStyle());
        player.setBowlingStyle(playerAggregate.getBowlingStyle());
        player.setCountry(playerAggregate.getCountry());
        player.setBowlingStyle(playerAggregate.getBowlingStyle());
        player.setPlayerType(playerAggregate.getPlayerType());
        player.setStatsLastUpdated(playerAggregate.getStatsLastUpdated());
        player.setPlayerStats(playerAggregate.getPlayerStats());
        return player;
    }


    public Player getPlayerInfo(long playerId){

        Optional<PlayerAggregate> athleteAggregateOpt =  playerRepository.findById(playerId);
        if(athleteAggregateOpt.isPresent()){
            PlayerAggregate playerAggregate = athleteAggregateOpt.get();
            Player player =   populatePlayer(playerAggregate);


            if(null != player.getPlayerStats() && player.getPlayerStats().size() > 0 && null != player.getStatsLastUpdated()){
                LocalDate lastUpdateDate = player.getStatsLastUpdated().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                LocalDate today = new Date().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if(lastUpdateDate.isEqual(today)){
                    System.out.println("getting playerStats from cache");
                    return player;
                }

            }
            List<PlayerStat> playerStats = getPlayerStats(playerId);
            player.setPlayerStats(playerStats);
            playerAggregate.setPlayerStats(playerStats);
            playerAggregate.setStatsLastUpdated(new Date());
            playerRepository.save(playerAggregate);
            return player;
        }else {

            PlayerAggregate playerAggregate = new PlayerAggregate();
            playerAggregate.setId(playerId);
            long sourcePlayerId = playerId / 13;
            String ref = "http://core.espnuk.org/v2/sports/cricket/athletes/" + sourcePlayerId;
            Athlete athlete = restTemplate.getForObject(ref, Athlete.class);
            if(null != athlete.getPosition()) {
                playerAggregate.setPlayerType(getBattingStyle(athlete.getPosition().getName()));
            }
            playerAggregate.setName(playerNameService.getPlayerName(sourcePlayerId));
            playerAggregate.setAge(athlete.getAge());

            playerAggregate.setBattingStyle(getBattingStyle(athlete.getStyles()));
            playerAggregate.setBowlingStyle(getBowlingStyle(athlete.getStyles()));

            playerAggregate.setCountry(teamNameService.getTeamName(athlete.getCountry()));
            playerAggregate.setPlayerStats(getPlayerStats(playerId));
            playerAggregate.setStatsLastUpdated(new Date());
            playerRepository.save(playerAggregate);
            return populatePlayer(playerAggregate);
        }
    }


    public List<PlayerStat> getPlayerStats(long playerId){

        List<PlayerStat> playerStats = new ArrayList<>();
        long sourcePlayerId = playerId / 13;

        String testStatRef  =  "http://core.espnuk.org/v2/sports/cricket/athletes/"+sourcePlayerId+"/statistics?internationalClassId=1";
        String odiStatRef  =  "http://core.espnuk.org/v2/sports/cricket/athletes/"+sourcePlayerId+"/statistics?internationalClassId=2";
        String t20StatRef  =  "http://core.espnuk.org/v2/sports/cricket/athletes/"+sourcePlayerId+"/statistics?internationalClassId=3";

        AthleteStat athleteTestStat = restTemplate.getForObject(testStatRef, AthleteStat.class);
        playerStats.add(getAthleteStats(athleteTestStat, "Test"));

        AthleteStat athleteOdiStat = restTemplate.getForObject(odiStatRef, AthleteStat.class);
        playerStats.add(getAthleteStats(athleteOdiStat, "ODI"));

        AthleteStat athleteT20Stat = restTemplate.getForObject(t20StatRef, AthleteStat.class);
        playerStats.add(getAthleteStats(athleteT20Stat, "T20"));

        return playerStats;

    }



    private PlayerStat getAthleteStats(AthleteStat athleteStat, String format) {
        PlayerStat playerStat = new PlayerStat();
        playerStat.setFormatName(format);
        if(null != athleteStat && null != athleteStat.getSplits() && null != athleteStat.getSplits().getCategories()){
            Category category = athleteStat.getSplits().getCategories().get(0);
            if(null != category.getStats()){
                category.getStats().stream().forEach(stat -> {
                    switch (stat.getName()) {

                        case "matches":
                            playerStat.setMatches(stat.getDisplayValue());
                            break;

                        case "runs":
                            playerStat.setRuns(stat.getDisplayValue());
                            break;

                        case "battingAverage":
                            playerStat.setBattingAverage(stat.getDisplayValue());
                            break;

                        case "highScore":
                            playerStat.setHighScore(stat.getDisplayValue());
                            break;

                        case "battingStrikeRate":
                            playerStat.setBattingStrikeRate(stat.getDisplayValue());
                            break;

                        case "wickets":
                            playerStat.setWickets(stat.getDisplayValue());
                            break;

                        case "bowlingAverage":
                            playerStat.setBowlingAverage(stat.getDisplayValue());
                            break;

                        case "bowlingStrikeRate":
                            playerStat.setBowlingStrikeRate(stat.getDisplayValue());
                            break;

                        case "bestBowlingFigures":
                            playerStat.setBestBowling(stat.getDisplayValue());
                            break;

                        case "economyRate":
                            playerStat.setEconomyRate(stat.getDisplayValue());
                            break;

                    }
                });

            }
        }
        return playerStat;


    }


    private String getBattingStyle(List<Style> styles) {
        StringBuilder battingStyle = new StringBuilder();
        styles.forEach(style -> {
            if("batting".equalsIgnoreCase(style.getType())){
                battingStyle.append(style.getDescription());
                return;
            }

        });
        return battingStyle.toString();
    }

    public static  String getBattingStyle(String type) {
        if(StringUtils.isNotBlank(type)){
            if(type.toLowerCase().contains("batsman")) return "Batsman";
            if(type.toLowerCase().contains("bowler")) return "Bowler";
            if(type.toLowerCase().contains("wicketkeeper")) return "Wicketkeeper";

        }
        return "Allrounder";

    }

    private String getBowlingStyle(List<Style> styles) {
        StringBuilder bowlingStyle = new StringBuilder();
        styles.forEach(style -> {
            if("bowling".equalsIgnoreCase(style.getType())){

                if(style.getDescription().toLowerCase().contains("right")) bowlingStyle.append("Right Arm ");
                else if(style.getDescription().toLowerCase().contains("left")) bowlingStyle.append("Left Arm ");

                if(style.getDescription().toLowerCase().contains("leg")) bowlingStyle.append("Leg Spin ");
                else if(style.getDescription().toLowerCase().contains("off")) bowlingStyle.append("Off Spin ");
                else if(style.getDescription().toLowerCase().contains("fast-medium")) bowlingStyle.append("Fast ");
                else if(style.getDescription().toLowerCase().contains("medium-fast")) bowlingStyle.append("Medium Fast ");
                else if(style.getDescription().toLowerCase().contains("slow")) bowlingStyle.append("Spin");
                else if(style.getDescription().toLowerCase().contains("fast")) bowlingStyle.append("Fast");

                return;
            }
        });
        return bowlingStyle.toString();
    }
}
