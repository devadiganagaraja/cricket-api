package com.sport.cricket.cricketapi.service;

import com.cricketfoursix.cricketdomain.aggregate.GameAggregate;
import com.cricketfoursix.cricketdomain.aggregate.QGameAggregate;
import com.cricketfoursix.cricketdomain.common.game.*;
import com.cricketfoursix.cricketdomain.common.game.Competitor;
import com.cricketfoursix.cricketdomain.repository.GameRepository;
import com.sport.cricket.cricketapi.domain.persistance.SeasonAggregate;
import com.sport.cricket.cricketapi.domain.response.*;
import com.sport.cricket.cricketapi.domain.source.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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

            GameSummary gameSummary = gameAggregate.getGameSummary();
            if(null != gameSummary){
                Award award = gameSummary.getPlayerOfMatch();
                ManOfTheMatch manOfTheMatch = new ManOfTheMatch();
                manOfTheMatch.setPlayerName(playerNameService.getPlayerNameByPlayerId(award.getPlayerId()));
                manOfTheMatch.setTeamName(teamNameService.getTeamNameByTeamId(award.getTeamId()));
                game.setManOfTheMatch(manOfTheMatch);
            }



            Map<Integer, ScoreCard> inningsScorecard = new TreeMap<>();
            if(null != gameAggregate.getCompetitor1()){
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

        GameClass gameClass = gameAggregate.getGameClass();
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

    public void persistGameAggregate(Event event, SeasonAggregate season) {
        Optional<GameAggregate> gameAggregateOpt = gameRepository.findById(event.getId()*13);
        if(!gameAggregateOpt.isPresent()) {
            GameAggregate gameAggregate = new GameAggregate();
            gameAggregate.setId(event.getId()*13);
            gameAggregate.setName(event.getName());
            populateGameInfo(event, season, gameAggregate);
            populateGameClassVenueAndMatchNote(event, gameAggregate);
            populateCompetitors(event, gameAggregate);

            populateGameAggregate(gameAggregate);


            gameRepository.save(gameAggregate);
        }
    }

    private void populateCompetitors(Event event, GameAggregate gameAggregate) {
        if(null != event.getCompetitions().get(0).getCompetitors()) {
            event.getCompetitions().get(0).getCompetitors().forEach(competitor -> {
                if(competitor.getOrder() == 1){
                    Competitor competitor1 = new Competitor();
                    competitor1.setId(Long.valueOf(competitor.getId())*13);
                    competitor1.setLineScoreRef(competitor.getLinescores().get$ref());
                    competitor1.setRosterRef(competitor.getRoster().get$ref());
                    gameAggregate.setCompetitor1(competitor1);
                }else{
                    Competitor competitor2 = new Competitor();
                    competitor2.setId(Long.valueOf(competitor.getId())*13);
                    competitor2.setLineScoreRef(competitor.getLinescores().get$ref());
                    competitor2.setRosterRef(competitor.getRoster().get$ref());
                    gameAggregate.setCompetitor2(competitor2);
                }
            });
        }
    }

    private void populateGameInfo(Event event, SeasonAggregate season, GameAggregate gameAggregate) {
        GameInfo gameInfo = new GameInfo();
        gameInfo.setName(event.getName());
        gameInfo.setDate(event.getDate());
        gameInfo.setEndDate(event.getEndDate());
        gameInfo.setLeagueId(season.getLeagueId());
        gameInfo.setSeason(season.getYear());
        gameInfo.setLeagueName(season.getName());
        gameAggregate.setGameInfo(gameInfo);
    }

    private void populateGameClassVenueAndMatchNote(Event event, GameAggregate gameAggregate) {
        if(null != event.getCompetitions() && event.getCompetitions().size() > 0) {
            Competition competition = event.getCompetitions().get(0);
            gameAggregate.setGameStatusApiRef(competition.getStatus().get$ref());
            populateGameClass(gameAggregate, competition);
            Venue venue = event.getCompetitions().get(0).getVenue();
            if(null != venue) {
                gameAggregate.getGameInfo().setVenue(venue.getFullName());
            }
            gameAggregate.getGameInfo().setNote(competition.getNote());
        }
    }

    private void populateGameClass(GameAggregate gameAggregate, Competition competition) {
        EventClass eventClass = competition.getEventClass();
        if(null != eventClass) {
            GameClass gameClass = new GameClass();
            gameClass.setId(Integer.valueOf(Integer.valueOf(eventClass.getInternationalClassId()) > 0 ? eventClass.getInternationalClassId() : eventClass.getGeneralClassId()));
            gameClass.setName(eventClass.getName());
            gameClass.setShortName(eventClass.getEventType());
            gameAggregate.setGameClass(gameClass);
        }
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

    public Game getGame(Long gameId, boolean refreshData) {
        Game game = new Game();

        Optional<GameAggregate> gameAggregateOptional = gameRepository.findById(gameId);
        if(gameAggregateOptional.isPresent()){

            GameAggregate gameAggregate = gameAggregateOptional.get();
            if(refreshData) {
                populateGameAggregate(gameAggregate);
            }
            return populateDomainMatch(gameAggregate);

        }



        return game;
    }

    private void populateGameAggregate(GameAggregate gameAggregate) {
        Event event = restTemplate.getForObject("http://new.core.espnuk.org/v2/sports/cricket/events/" + (gameAggregate.getId()/13) , Event.class);

        log.info("event::::{},event");

        if (null != event && null != event.getCompetitions() && event.getCompetitions().size() > 0) {
            Competition competition = event.getCompetitions().get(0);

            log.info("competition::::{},competition");
            populateTossNote(gameAggregate, competition);
        }
        populateGameStatus(gameAggregate);
        gameRepository.save(gameAggregate);
    }

    private void populateTossNote(GameAggregate gameAggregate, Competition competition) {
        if(null != competition.getNotes()){
            Optional<Note> noteOptional = competition.getNotes().stream().filter(note -> note.getType().equalsIgnoreCase("toss")).findFirst();
            if(noteOptional.isPresent()){
                Note note = noteOptional.get();
                log.info("note::::{},note");

                String toss = note.getText();
                log.info("toss::::{},toss");
                if(StringUtils.isNotBlank(toss)){
                    String [] tossArray = toss.split(",");
                    if(tossArray.length > 0) {
                        gameAggregate.getGameInfo().setToss(tossArray[0] + " won the toss.");
                        if(tossArray.length > 1) {
                            if(tossArray[1].toLowerCase().contains("bat")) {
                                gameAggregate.getGameInfo().setToss(gameAggregate.getGameInfo().getToss() + " Opted to Bat.");
                            }else{
                                gameAggregate.getGameInfo().setToss(gameAggregate.getGameInfo().getToss()+  " Opted to Bowl.");
                            }

                        }
                    }

                }

            }
        }
    }

    private void populateGameStatus(GameAggregate gameAggregate) {
        EventStatus eventStatus = restTemplate.getForObject(gameAggregate.getGameStatusApiRef(), EventStatus.class);
        log.info("eventStatus:: {}",eventStatus);


        if (null != eventStatus) {

            populateGameStatusType(gameAggregate, eventStatus);
            populateAwards(gameAggregate, eventStatus);
            populateLineScores(gameAggregate);

            populateRoster(gameAggregate);
        }
    }

    private void populateTeamLinscore(Competitor competitor){
        CompetitorLineScores competitorLineScores = restTemplate.getForObject(competitor.getLineScoreRef(), CompetitorLineScores.class);
        if (null != competitorLineScores && null != competitorLineScores.getItems()) {
            competitorLineScores.getItems().stream().forEach(competitorLineScore -> {
                if (competitorLineScore.isBatting()) {
                    LineScoreStatistics competitorLineScoreStats = restTemplate.getForObject(competitorLineScore.getStatistics().get$ref(), LineScoreStatistics.class);
                    if (null != competitorLineScoreStats && null != competitorLineScoreStats.getSplits() && null != competitorLineScoreStats.getSplits().getCategories() && competitorLineScoreStats.getSplits().getCategories().size() > 0) {
                        Category BattingStatsCategory = competitorLineScoreStats.getSplits().getCategories().get(0);
                        if (null != BattingStatsCategory && null != BattingStatsCategory.getStats()) {
                            InningsInfo inningsInfo = new InningsInfo();
                            inningsInfo.setBattingTeamName(teamNameService.getTeamNameByTeamId(competitor.getId()));
                            inningsInfo.setBattingTeamId(competitor.getId());

                            populateInningsName(competitorLineScore, inningsInfo);

                            BattingStatsCategory.getStats().forEach(stat -> {
                                switch (stat.getName()) {
                                    case "runs":
                                        inningsInfo.setRuns(Integer.valueOf(stat.getDisplayValue()));
                                        break;
                                    case "target":
                                        inningsInfo.setTarget(Integer.valueOf(stat.getDisplayValue()));
                                        break;
                                    case "wickets":
                                        inningsInfo.setWickets(Integer.valueOf(stat.getDisplayValue()));
                                        break;
                                    case "overLimit":
                                        inningsInfo.setOverLimit(stat.getDisplayValue());
                                        break;
                                    case "runRate":
                                        inningsInfo.setRunRate(stat.getDisplayValue());
                                        break;
                                    case "legbyes":
                                        inningsInfo.setLegByes(Integer.valueOf(stat.getDisplayValue()));
                                        break;
                                    case "byes":
                                        inningsInfo.setByes(Integer.valueOf(stat.getDisplayValue()));
                                        break;
                                    case "wides":
                                        inningsInfo.setWides(Integer.valueOf(stat.getDisplayValue()));
                                        break;
                                    case "noballs":
                                        inningsInfo.setNoBalls(Integer.valueOf(stat.getDisplayValue()));
                                        break;
                                    case "lead":
                                        inningsInfo.setLead(Integer.valueOf(stat.getDisplayValue()));

                                    case "overs":
                                        inningsInfo.setOvers(stat.getDisplayValue());
                                        break;

                                    case "liveCurrent":
                                        inningsInfo.setLiveInnings(stat.getDisplayValue().equals("1")?true:false);
                                        break;

                                }
                            });
                            inningsInfo.setExtras(inningsInfo.getByes()+inningsInfo.getLegByes()+inningsInfo.getWides()+inningsInfo.getNoBalls());
                            if(competitor.getInningsScores().containsKey(competitorLineScore.getPeriod())) {
                                competitor.getInningsScores().get(competitorLineScore.getPeriod()).setInningsInfo(inningsInfo);
                            }else{
                                InningsScoreCard inningsScoreCard = new InningsScoreCard();
                                inningsScoreCard.setInningsInfo(inningsInfo);
                                competitor.getInningsScores().put(competitorLineScore.getPeriod(), inningsScoreCard);
                            }

                        }

                    }

                }
            });
        }


    }

    private void populateInningsName(CompetitorLineScore competitorLineScore, InningsInfo inningsInfo) {
        switch (competitorLineScore.getPeriod()) {

            case 1:
                inningsInfo.setInningsName("1st innings");
                break;


            case 2:
                inningsInfo.setInningsName("2nd innings");
                break;

            case 3:
                inningsInfo.setInningsName("3rd innings");
                break;

            case 4:
                inningsInfo.setInningsName("4th innings");
                break;

            default:
                inningsInfo.setInningsName("Extra innings");
        }
    }

    private void populateRoster(GameAggregate gameAggregate) {
        if(gameAggregate.getGameInfo().getGameStatus().equals(GameStatus.post) || gameAggregate.getGameInfo().getGameStatus().equals(GameStatus.live)){

            LiveScoreCard liveScoreCard = gameAggregate.getLiveScoreCard();
            if(null == liveScoreCard) gameAggregate.setLiveScoreCard(new LiveScoreCard());

            populateCompetitorRoster(gameAggregate.getCompetitor1(),gameAggregate.getLiveScoreCard());
            populateCompetitorRoster(gameAggregate.getCompetitor2(), gameAggregate.getLiveScoreCard());
        }
    }

    private void populateCompetitorRoster(Competitor competitor, LiveScoreCard liveScoreCard) {
        if(null != competitor) {
            Roster roster = restTemplate.getForObject(competitor.getRosterRef(), Roster.class);
            if (null != roster && null != roster.getEntries()) {
                AtomicInteger unknownRosterIndex = new AtomicInteger(101);

                roster.getEntries().forEach(playerRoster -> {
                    log.debug("playerRoster :{}", playerRoster);

                    if("striker".equalsIgnoreCase(playerRoster.getActiveName())){
                        liveScoreCard.setStriker(playerRoster.getPlayerId()*13);
                    }

                    if("non-striker".equalsIgnoreCase(playerRoster.getActiveName())){
                        liveScoreCard.setNonStriker(playerRoster.getPlayerId()*13);
                    }

                    if("current bowler".equalsIgnoreCase(playerRoster.getActiveName())){
                        liveScoreCard.setCurrentBowler(playerRoster.getPlayerId()*13);
                    }

                    if("previous bowler".equalsIgnoreCase(playerRoster.getActiveName())){
                        liveScoreCard.setPreviousBowler(playerRoster.getPlayerId()*13);
                    }


                    RosterLineScores rosterLineScores = restTemplate.getForObject(playerRoster.getLinescores().get$ref(), RosterLineScores.class);

                    if (null != rosterLineScores.getItems() && rosterLineScores.getItems().size() > 0) {
                        log.debug("playerRoster1 :{}", playerRoster);
                        rosterLineScores.getItems().forEach(rosterLineScore -> {
                            log.debug("playerRoster2 :{}, linescore {}", playerRoster, rosterLineScore);


                            LineScoreStatistics stats = restTemplate.getForObject(rosterLineScore.getStatistics(), LineScoreStatistics.class);

                            InningsScoreCard inningsScoreCard;
                            if (competitor.getInningsScores().containsKey(rosterLineScore.getPeriod())) {
                                inningsScoreCard = competitor.getInningsScores().get(rosterLineScore.getPeriod());
                            } else {
                                inningsScoreCard = new InningsScoreCard();

                                competitor.getInningsScores().put(rosterLineScore.getPeriod(), inningsScoreCard);
                            }
                            if (rosterLineScore.isBatting()) {
                                log.debug("playerRoster3 :{}, linescore {}", playerRoster, rosterLineScore);

                                BattingCard battingCard;
                                if (null == inningsScoreCard.getBattingCard()) {
                                    battingCard = new BattingCard();
                                    battingCard.setBatsmanCardSet(new TreeSet<>());
                                    inningsScoreCard.setBattingCard(battingCard);
                                } else {
                                    battingCard = inningsScoreCard.getBattingCard();
                                }
                                BatsmanCard batsmanCard = new BatsmanCard();
                                batsmanCard.setPlayerId(playerRoster.getPlayerId() * 13);
                                batsmanCard.setPlayerName(playerNameService.getPlayerName(playerRoster.getPlayerId()));

                                try {
                                    stats.getSplits().getCategories().get(0).getStats().stream().forEach(stat -> {
                                        switch (stat.getName()) {
                                            case "ballsFaced":
                                                batsmanCard.setBalls(stat.getDisplayValue());
                                                break;

                                            case "batted":
                                                batsmanCard.setBatted(stat.getDisplayValue().equals("1") ? true : false);
                                                break;

                                            case "outs":
                                                batsmanCard.setOut(stat.getDisplayValue().equals("1") ? true : false);
                                                break;

                                            case "runs":
                                                batsmanCard.setRuns(stat.getDisplayValue());
                                                break;

                                            case "fours":
                                                batsmanCard.setFours(stat.getDisplayValue());
                                                break;

                                            case "sixes":
                                                batsmanCard.setSixes(stat.getDisplayValue());
                                                break;
                                        }
                                    });
                                    if (null != stats.getSplits().getBatting() && null != stats.getSplits().getBatting().getOutDetails() && null != stats.getSplits().getBatting().getOutDetails().getShortText())
                                        batsmanCard.setBattingDescription(stats.getSplits().getBatting().getOutDetails().getShortText().replace("&dagger;", "").replace("&amp;", "&"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                batsmanCard.setPosition(rosterLineScore.getOrder() > 0 && batsmanCard.isBatted() ? rosterLineScore.getOrder() : unknownRosterIndex.incrementAndGet());
                                log.debug("playerRoster4 :{}, linescore {}, batsmanCard {}", playerRoster, rosterLineScore, batsmanCard);

                                battingCard.getBatsmanCardSet().add(batsmanCard);
                            } else {
                                BowlingCard bowlingCard;
                                if (null == inningsScoreCard.getBowlingCard()) {
                                    bowlingCard = new BowlingCard();
                                    bowlingCard.setBowlerCardSet(new TreeSet<>());
                                    inningsScoreCard.setBowlingCard(bowlingCard);
                                } else {
                                    bowlingCard = inningsScoreCard.getBowlingCard();
                                }
                                BowlerCard bowlerCard = new BowlerCard();
                                bowlerCard.setPlayerId(playerRoster.getPlayerId() * 13);
                                bowlerCard.setPlayerName(playerNameService.getPlayerName(playerRoster.getPlayerId()));
                                try {
                                    stats.getSplits().getCategories().get(0).getStats().stream().forEach(stat -> {
                                        switch (stat.getName()) {
                                            case "overs":
                                                bowlerCard.setOvers(stat.getDisplayValue());
                                                break;

                                            case "bowled":
                                                bowlerCard.setBowled(stat.getDisplayValue().equals("1") ? true : false);
                                                break;

                                            case "conceded":
                                                bowlerCard.setConceded(stat.getDisplayValue());
                                                break;

                                            case "maidens":
                                                bowlerCard.setMaidens(stat.getDisplayValue());
                                                break;

                                            case "noballs":
                                                bowlerCard.setNoballs(stat.getDisplayValue());
                                                break;
                                            case "wides":
                                                bowlerCard.setWides(stat.getDisplayValue());
                                                break;

                                            case "byes":
                                                bowlerCard.setByes(stat.getDisplayValue());
                                                break;

                                            case "legbyes":
                                                bowlerCard.setLegbyes(stat.getDisplayValue());
                                                break;

                                            case "wickets":
                                                bowlerCard.setWickets(stat.getDisplayValue());
                                                break;

                                            case "stumped":
                                                bowlerCard.setStumped(stat.getDisplayValue());
                                                break;

                                            case "caught":
                                                bowlerCard.setCaught(stat.getDisplayValue());
                                                break;
                                        }
                                    });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                bowlerCard.setLive(playerRoster.getActiveName());
                                bowlerCard.setPosition(rosterLineScore.getOrder());
                                if (bowlerCard.isBowled())
                                    bowlingCard.getBowlerCardSet().add(bowlerCard);
                            }
                        });
                    }

                });
            }
        }
    }

    private void populateLineScores(GameAggregate gameAggregate) {


        populateTeamLinscore(gameAggregate.getCompetitor1());
        populateTeamLinscore(gameAggregate.getCompetitor2());
    }


    private void populateAwards(GameAggregate gameAggregate, EventStatus eventStatus) {
        if(gameAggregate.getGameInfo().getGameStatus().equals(GameStatus.post)) {
            if (null != eventStatus.getFeaturedAthletes()) {
                eventStatus.getFeaturedAthletes().forEach(featuredAthletes -> {
                    Award award = new Award();
                    award.setPlayerId(Long.valueOf(featuredAthletes.getPlayerId()) * 13);
                    award.setTeamId(Long.valueOf(featuredAthletes.getTeam().get$ref().split("teams/")[1]) * 13);
                    if (null == gameAggregate.getGameSummary())
                        gameAggregate.setGameSummary(new GameSummary());
                    if ("Player Of The Match".equalsIgnoreCase(featuredAthletes.getDisplayName())) {
                        gameAggregate.getGameSummary().setPlayerOfMatch(award);
                    } else if ("Player Of The Series".equalsIgnoreCase(featuredAthletes.getDisplayName())) {
                        gameAggregate.getGameSummary().setPlayerOfSeries(award);
                    }
                });
            }
        }
    }
    private void populateGameStatusType(GameAggregate gameAggregate, EventStatus eventStatus) {

        if(null !=eventStatus.getType()){
            EventStatusType eventStatusType = eventStatus.getType();
            log.info("eventStatusType:: {}", eventStatusType);
            GameStatus gameStatus = GameStatus.cancled;
            if("post".equalsIgnoreCase(eventStatusType.getState())){
                gameStatus = GameStatus.post;
            }else if("pre".equalsIgnoreCase(eventStatusType.getState())){
                gameStatus = GameStatus.pre;
            }else if("in".equalsIgnoreCase(eventStatusType.getState())){
                gameStatus = GameStatus.live;
            }else if("scheduled".equalsIgnoreCase(eventStatusType.getState())){
                gameStatus = GameStatus.future;
            }

            gameAggregate.getGameInfo().setGameStatus(gameStatus);
        }
    }
}
