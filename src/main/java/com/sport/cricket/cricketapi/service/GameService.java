package com.sport.cricket.cricketapi.service;

import com.cricketfoursix.cricketdomain.aggregate.GameAggregate;
import com.cricketfoursix.cricketdomain.aggregate.QGameAggregate;
import com.cricketfoursix.cricketdomain.common.game.*;
import com.cricketfoursix.cricketdomain.common.game.Competitor;
import com.cricketfoursix.cricketdomain.repository.GameRepository;
import com.sport.cricket.cricketapi.domain.response.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {

    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    @Autowired
    GameRepository gameRepository;

    @Autowired
    TeamNameService teamNameService;

    @Autowired
    PlayerNameService playerNameService;



    @Autowired
    QGameAggregate qGameAggregate;

    @Autowired
    RestTemplate restTemplate;


    @Autowired
    YoutubeApiService youtubeApiService;



    @Autowired
    Map<String, GameAggregate> liveGamesCache;


    private static DecimalFormat overFormat = new DecimalFormat("#.#");
    private static DecimalFormat econamyRateFormat = new DecimalFormat("#.##");


    public Game populateDomainMatch(GameAggregate gameAggregate) {
        Game game = new Game();
        game.setId(gameAggregate.getId());
        game.setName(gameAggregate.getName());

        GameInfo gameInfo = gameAggregate.getGameInfo();

        if(null != gameInfo) {
            game.setDate(gameInfo.getDate());
            game.setLeagueId(gameInfo.getLeagueId());
            game.setLeagueName(gameInfo.getLeagueName());
            game.setSeason(gameInfo.getSeason());
            game.setGameNote(gameInfo.getNote());
            game.setVenue(gameInfo.getVenue());
            game.setToss(gameInfo.getToss());
            game.setGameStatus(gameInfo.getGameStatus().toString());

            GameSummary gameSummary = gameAggregate.getGameInfo().getGameSummary();
            if(null != gameSummary){
                Award award = gameSummary.getPlayerOfMatch();
                ManOfTheMatch manOfTheMatch = new ManOfTheMatch();
                manOfTheMatch.setPlayerName(playerNameService.getPlayerNameByPlayerId(award.getPlayerId()));
                manOfTheMatch.setTeamName(teamNameService.getTeamNameByTeamId(award.getTeamId()));
                game.setManOfTheMatch(manOfTheMatch);
            }



            Map<Integer, ScoreCard> inningsScorecard = new TreeMap<>();
            if(null != gameAggregate.getCompetitor1()){
                game.setTeam1squad(gameAggregate.getCompetitor1().getSquad());
                Map<Integer, InningsScoreCard> inningsScoreMap = gameAggregate.getCompetitor1().getInningsScores();




                if(null != inningsScoreMap){
                    inningsScoreMap.forEach((innings, inningsScoreCard) -> {

                        if(inningsScorecard.containsKey(innings)){
                            ScoreCard scoreCard = inningsScorecard.get(innings);
                            LiveScoreCard liveScoreCard = gameAggregate.getLiveScoreCard();


                            if(null != inningsScoreCard.getBattingCard()){
                                scoreCard.setInningsInfo(inningsScoreCard.getInningsInfo());
                                scoreCard.setBattingScoreCard(populateBattingScoreCard(inningsScoreCard.getBattingCard()));
                            }
                            if(null != inningsScoreCard.getBowlingCard()){
                                scoreCard.setBowlingScoreCard(populateBowlingScoreCard(inningsScoreCard.getBowlingCard()));
                            }


                        }else{
                            ScoreCard scoreCard = new ScoreCard();
                            if(null != inningsScoreCard.getBattingCard()){
                                scoreCard.setInningsInfo(inningsScoreCard.getInningsInfo());
                                scoreCard.setBattingScoreCard(populateBattingScoreCard(inningsScoreCard.getBattingCard()));
                            }
                            if(null != inningsScoreCard.getBowlingCard()){
                                scoreCard.setBowlingScoreCard(populateBowlingScoreCard(inningsScoreCard.getBowlingCard()));
                            }
                            inningsScorecard.put(innings, scoreCard);
                        }
                    });
                }
            }




            if(null != gameAggregate.getCompetitor2()){
                game.setTeam2squad(gameAggregate.getCompetitor2().getSquad());
                Map<Integer, InningsScoreCard> inningsScoreMap = gameAggregate.getCompetitor2().getInningsScores();
                if(null != inningsScoreMap){
                    inningsScoreMap.forEach((innings, inningsScoreCard) -> {

                        if(inningsScorecard.containsKey(innings)){
                            ScoreCard scoreCard = inningsScorecard.get(innings);
                            if(null != inningsScoreCard.getBattingCard()){
                                scoreCard.setInningsInfo(inningsScoreCard.getInningsInfo());
                                scoreCard.setBattingScoreCard(populateBattingScoreCard(inningsScoreCard.getBattingCard()));
                            }
                            if(null != inningsScoreCard.getBowlingCard()){
                                scoreCard.setBowlingScoreCard(populateBowlingScoreCard(inningsScoreCard.getBowlingCard()));
                            }


                        }else{
                            ScoreCard scoreCard = new ScoreCard();
                            if(null != inningsScoreCard.getBattingCard()){
                                scoreCard.setInningsInfo(inningsScoreCard.getInningsInfo());
                                scoreCard.setBattingScoreCard(populateBattingScoreCard(inningsScoreCard.getBattingCard()));
                            }
                            if(null != inningsScoreCard.getBowlingCard()){
                                scoreCard.setBowlingScoreCard(populateBowlingScoreCard(inningsScoreCard.getBowlingCard()));
                            }
                            inningsScorecard.put(innings, scoreCard);
                        }
                    });
                }
            }


            game.setLiveScore(populateLiveTab((TreeMap<Integer, ScoreCard>) inningsScorecard, gameAggregate.getLiveScoreCard()));


            game.setScoreCards((new ArrayList<>(inningsScorecard.values())));



            inningsScorecard.values().forEach(scoreCard -> {
                if(null != scoreCard.getBattingScoreCard() &&  null != scoreCard.getBattingScoreCard().getBattingScoreLeaves() ) {

                    Leader leader = new Leader();




                    leader.setInningsInfo(scoreCard.getInningsInfo());

                    leader.getBattingLeaders().addAll(scoreCard.getBattingScoreCard().getBattingScoreLeaves().stream()
                            .sorted(Comparator.comparing(BattingScoreLeaf::getRuns, Comparator.reverseOrder()).thenComparing(BattingScoreLeaf::getBalls))
                            .limit(3)
                            .collect(Collectors.toList()));

                    leader.getBowlingLeaders().addAll(scoreCard.getBowlingScoreCard().getBowlingScoreLeaves().stream().filter(bowlingScoreLeaf -> Double.valueOf(bowlingScoreLeaf.getOvers()) >=2)
                            .sorted(Comparator.comparing(BowlingScoreLeaf::getWickets, Comparator.reverseOrder()).thenComparing(BowlingScoreLeaf::getRuns))
                            .limit(3)
                            .collect(Collectors.toList()));
                    game.getLeaders().add(leader);


                }


            });


        }

        GameClass gameClass = gameAggregate.getGameInfo().getGameClass();
        if(null != gameInfo) {
            game.setGameClass(gameClass.getShortName());
        }

        Competitor competitor1 = gameAggregate.getCompetitor1();
        if(null != competitor1) {
            game.setTeam1Name(teamNameService.getTeamNameByTeamId(competitor1.getId()));

            if(null != competitor1.getInningsScores() && competitor1.getInningsScores().size() > 0 ) {
                competitor1.getInningsScores().values().stream().filter(inningsScoreCard -> inningsScoreCard.getInningsInfo() != null)

                        .forEach(inningsScoreCard -> {
                    if (inningsScoreCard.getInningsInfo() != null) {
                        InningsInfo inningsInfo = inningsScoreCard.getInningsInfo();
                        game.getTeam1Score().add(formatScore(inningsInfo));
                        game.getTeam1Overs().add(inningsInfo.getOvers());
                    }


                });
            }

        }

        Competitor competitor2 = gameAggregate.getCompetitor2();
        if(null != competitor2) {
            game.setTeam2Name(teamNameService.getTeamNameByTeamId(competitor2.getId()));

            if(null != competitor2.getInningsScores() && competitor2.getInningsScores().size() > 0 ) {

                competitor2.getInningsScores().values().stream().filter(inningsScoreCard -> inningsScoreCard.getInningsInfo() != null).forEach( inningsScoreCard -> {
                    InningsInfo inningsInfo = inningsScoreCard.getInningsInfo();
                    if (inningsScoreCard.getInningsInfo() != null) {
                        game.getTeam2Score().add(formatScore(inningsInfo));
                        game.getTeam2Overs().add(inningsInfo.getOvers());
                    }


                });
            }
        }


        youtubeApiService.getYoutubeVideoList(game.getLeagueName());
        return game;
    }

    private LiveScore populateLiveTab(TreeMap<Integer, ScoreCard> inningsScorecard, LiveScoreCard liveScoreCard) {
        LiveScore liveScore = new LiveScore();

        if(null != inningsScorecard && inningsScorecard.size() > 0){
            ScoreCard latestScoreCard = inningsScorecard.lastEntry().getValue();
            if(null != latestScoreCard){
                if(null != latestScoreCard.getBattingScoreCard().getBattingScoreLeaves()){
                    Optional<BattingScoreLeaf> strikerOptional =  latestScoreCard.getBattingScoreCard().getBattingScoreLeaves().stream().filter(battingScoreLeaf -> battingScoreLeaf.getPlayerId() == liveScoreCard.getStriker())
                            .findFirst();
                    if(strikerOptional.isPresent()){
                        liveScore.setStriker(strikerOptional.get());
                    }

                    Optional<BattingScoreLeaf> nonStrikerOptional =  latestScoreCard.getBattingScoreCard().getBattingScoreLeaves().stream().filter(battingScoreLeaf -> battingScoreLeaf.getPlayerId() == liveScoreCard.getNonStriker())
                            .findFirst();
                    if(nonStrikerOptional.isPresent()){
                        liveScore.setNonStriker(nonStrikerOptional.get());
                    }
                }

                if(null != latestScoreCard.getBowlingScoreCard().getBowlingScoreLeaves()){
                    Optional<BowlingScoreLeaf> bowlerOptional =  latestScoreCard.getBowlingScoreCard().getBowlingScoreLeaves().stream().filter(bowlingScoreLeaf -> bowlingScoreLeaf.getPlayerId() == liveScoreCard.getCurrentBowler())
                            .findFirst();
                    if(bowlerOptional.isPresent()){
                        liveScore.setBowler(bowlerOptional.get());
                    }

                    Optional<BowlingScoreLeaf> otherBowlerOptional =  latestScoreCard.getBowlingScoreCard().getBowlingScoreLeaves().stream().filter(bowlingScoreLeaf -> bowlingScoreLeaf.getPlayerId() == liveScoreCard.getPreviousBowler())
                            .findFirst();
                    if(otherBowlerOptional.isPresent()){
                        liveScore.setOtherBowler(otherBowlerOptional.get());
                    }
                }
            }
        }

        return liveScore;

    }


    private BowlingScoreCard populateBowlingScoreCard(BowlingCard bowlingCard) {
        BowlingScoreCard battingScoreCard = new BowlingScoreCard();
        if(null != bowlingCard.getBowlerCardSet()) {
            bowlingCard.getBowlerCardSet().stream().sorted().forEach(bowlerCard -> {
                battingScoreCard.getBowlingScoreLeaves().add(populateBowlingScoreLeaf(bowlerCard));
            });

        }

        return battingScoreCard;


    }

    private BowlingScoreLeaf populateBowlingScoreLeaf(BowlerCard bowlerCard) {
        BowlingScoreLeaf bowlingScoreLeaf = new BowlingScoreLeaf();
        if(null != bowlerCard){
            bowlingScoreLeaf.setPlayerId(bowlerCard.getPlayerId());
            bowlingScoreLeaf.setOvers(bowlerCard.getOvers());
            bowlingScoreLeaf.setMaidens(Integer.valueOf(bowlerCard.getMaidens()));
            bowlingScoreLeaf.setRuns(Integer.valueOf(bowlerCard.getConceded()));
            bowlingScoreLeaf.setPlayerName(playerNameService.getPlayerNameByPlayerId(bowlerCard.getPlayerId()));
            bowlingScoreLeaf.setWickets(Integer.valueOf(bowlerCard.getWickets()));
            bowlingScoreLeaf.setEconomyRate(econamyRateFormat.format(bowlingScoreLeaf.getRuns()/Double.valueOf(bowlingScoreLeaf.getOvers())));
        }

        return bowlingScoreLeaf;
    }

    private BattingScoreCard populateBattingScoreCard(BattingCard battingCard) {
        BattingScoreCard battingScoreCard = new BattingScoreCard();
        if(null != battingCard.getBatsmanCardSet()) {
            battingCard.getBatsmanCardSet().stream().sorted().filter(batsmanCard -> batsmanCard.isBatted()).forEach(batsmanCard -> {
                battingScoreCard.getBattingScoreLeaves().add(populateBattingScoreLeaf(batsmanCard));
            });
            battingCard.getBatsmanCardSet().stream().sorted().filter(batsmanCard -> ! batsmanCard.isBatted()).forEach(batsmanCard -> {
                battingScoreCard.getYetToBat().add(populateBattingScoreLeaf(batsmanCard));
            });

        }

        return battingScoreCard;
    }

    private BattingScoreLeaf populateBattingScoreLeaf(BatsmanCard batsmanCard) {

        BattingScoreLeaf battingScoreLeaf = new BattingScoreLeaf();
        if(null != batsmanCard){
            battingScoreLeaf.setPlayerId(batsmanCard.getPlayerId());
            battingScoreLeaf.setBalls(Integer.valueOf(batsmanCard.getBalls()));
            battingScoreLeaf.setDismissalText(batsmanCard.getBattingDescription());
            battingScoreLeaf.setRuns(Integer.valueOf(batsmanCard.getRuns()));
            battingScoreLeaf.setPlayerName(playerNameService.getPlayerNameByPlayerId(batsmanCard.getPlayerId()));
            battingScoreLeaf.setFours(Integer.valueOf(batsmanCard.getFours()));
            battingScoreLeaf.setSixes(Integer.valueOf(batsmanCard.getSixes()));
            battingScoreLeaf.setStrikeRate(battingScoreLeaf.getRuns() > 0 ? econamyRateFormat.format((double)((double)battingScoreLeaf.getRuns()/battingScoreLeaf.getBalls()*100)) :"" );
            if(StringUtils.isBlank(battingScoreLeaf.getStrikeRate())){
                battingScoreLeaf.setStrikeRate(battingScoreLeaf.getBalls() > 0 ? "0.00" : "-");
            }
        }

        return battingScoreLeaf;

    }

    private String formatScore(InningsInfo score) {
        StringBuilder scoreStr = new StringBuilder();
        scoreStr.append(score.getRuns());
        if(score.getWickets() != 10){
            scoreStr.append("/").append(score.getWickets());
        }
        return scoreStr.toString();
    }


    public Set<Game> getGames(String deltaDays) {
        int delta = 0;
        try {
            delta = Integer.parseInt(deltaDays);
        }catch (Exception e){
            log.error("deltaDays is not parsable to int : {}", e.getMessage());
        }

        Set<Game> games =  new TreeSet<>();

        Date date = org.apache.commons.lang.time.DateUtils.addDays(new Date(), delta);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        date = calendar.getTime();

        Iterable<GameAggregate> aggregateIterable = gameRepository.findAll((qGameAggregate.gameInfo.date.lt(date).and(qGameAggregate.gameInfo.endDate.goe(date)))
                .or(qGameAggregate.gameInfo.date.eq(date)));
        if(null != aggregateIterable){
            aggregateIterable.forEach(gameAggregate -> {
                games.add(populateDomainMatch(gameAggregate));
            });
        }
        return games;

    }

    public Game getGame(Long gameId) {
        Game game = new Game();

        Optional<GameAggregate> gameAggregateOptional = gameRepository.findById(gameId);
        if(gameAggregateOptional.isPresent()){

            GameAggregate gameAggregate = gameAggregateOptional.get();

            return populateDomainMatch(gameAggregate);

        }



        return game;
    }




}
