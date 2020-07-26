package com.sport.cricket.cricketapi.service;

import com.cricketfoursix.cricketdomain.aggregate.BBBAggregate;
import com.cricketfoursix.cricketdomain.domain.bbb.BallSummary;
import com.cricketfoursix.cricketdomain.domain.bbb.Dismissal;
import com.cricketfoursix.cricketdomain.repository.BallRepository;
import com.sport.cricket.cricketapi.domain.response.BallCommentary;
import com.sport.cricket.cricketapi.domain.response.GameCommentary;
import com.sport.cricket.cricketapi.domain.response.InningsCommentary;
import com.sport.cricket.cricketapi.domain.response.OverCommentary;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class GameCommentaryService {

    @Autowired
    BallRepository ballRepository;


    public GameCommentary fetchAllBallDetails(Long gameId) {


        List<BBBAggregate> bbbAggregates = ballRepository.findByEventId(gameId);
        GameCommentary gameCommentary = new GameCommentary();
        gameCommentary.setEventId(gameId);

        if (null != bbbAggregates) {
            bbbAggregates.forEach(bbbAggregate -> {

                Optional<InningsCommentary> inningsCommentaryOptional = gameCommentary.getInningsCommentary().stream().filter(inningsCommentary -> inningsCommentary.getInningsNumber() == bbbAggregate.getBallSummary().getInningsNo()).findFirst();

                if (inningsCommentaryOptional.isPresent()) {
                    InningsCommentary inningsCommentary = inningsCommentaryOptional.get();


                    inningsCommentary.setInningsNumber(bbbAggregate.getBallSummary().getInningsNo());

                    inningsCommentary.setInningsNumber(bbbAggregate.getBallSummary().getInningsNo());
                    Optional<OverCommentary> overCommentaryOptional = inningsCommentary.getOverCommentarySet().stream().filter(overCommentary -> overCommentary.getOverSummary().getOverNo() == bbbAggregate.getOverSummary().getOverNo()).findFirst();


                    if (overCommentaryOptional.isPresent()) {
                        OverCommentary overCommentary = overCommentaryOptional.get();

                        if (overCommentary.getOverSummary().getOversUnique() <= bbbAggregate.getOverSummary().getOversUnique()) {
                            overCommentary.setOverSummary(bbbAggregate.getOverSummary());
                        }
                        if(bbbAggregate.getOverSummary().isComplete())
                            overCommentary.setInningCommentarySummary(bbbAggregate.getInningCommentarySummary());
                        overCommentary.setOverRunsSummary(populateOverRunsSummary(bbbAggregate, overCommentary.getOverRunsSummaryMap()));


                        overCommentary.getBallCommentarySet().add(createBallCommentary(bbbAggregate));
                    } else {
                        createOverCommentary(bbbAggregate, inningsCommentary);
                    }

                } else {
                    createInningsCommentary(bbbAggregate, gameCommentary);
                }
            });
        }

        return gameCommentary;

    }


    private void createInningsCommentary(BBBAggregate bbbAggregate, GameCommentary gameCommentary) {
        InningsCommentary inningsCommentary = new InningsCommentary();
        inningsCommentary.setInningsNumber(bbbAggregate.getBallSummary().getInningsNo());
        createOverCommentary(bbbAggregate, inningsCommentary);
        gameCommentary.getInningsCommentary().add(inningsCommentary);
    }

    private void createOverCommentary(BBBAggregate bbbAggregate, InningsCommentary inningsCommentary) {
        OverCommentary overCommentary = new OverCommentary();
        BallCommentary ballCommentary = createBallCommentary(bbbAggregate);
        overCommentary.getBallCommentarySet().add(ballCommentary);
        overCommentary.setOverNumber(bbbAggregate.getOverSummary().getOverNo());
        overCommentary.setOverSummary(bbbAggregate.getOverSummary());
        overCommentary.setOverRunsSummary(populateOverRunsSummary(bbbAggregate, overCommentary.getOverRunsSummaryMap()));
        if(bbbAggregate.getOverSummary().isComplete())
            overCommentary.setInningCommentarySummary(bbbAggregate.getInningCommentarySummary());
        inningsCommentary.getOverCommentarySet().add(overCommentary);

    }

    private String populateOverRunsSummary(BBBAggregate bbbAggregate, TreeMap<Double, String> overRunsSummaryMap) {
        if(null != bbbAggregate) {
            BallSummary ballSummary = bbbAggregate.getBallSummary();
            Dismissal dismissal = bbbAggregate.getDismissalSummary();


            StringBuilder ball = new StringBuilder("<span><span class='runSummaryPadding'>");


            if(dismissal.isDismissal()) {
                    ball.append("<span class='runSummaryColorRed'>");

                    ball.append("W");

                    ball.append("</span>");
                    if (ballSummary.getRuns() > 0) {
                        ball.append(" +").append(ballSummary.getRuns());
                    }
                }else {
                    if (ballSummary.getRuns() > 0) {
                        if(ballSummary.getRuns() >=4 && ballSummary.getRuns() < 6) {
                            ball.append("<span class='runSummaryColorGreen'>");
                        }
                        if(ballSummary.getRuns() >= 6) {
                            ball.append("<span class='runSummaryColorPurple'>");
                        }
                        ball.append(ballSummary.getRuns());
                        if(ballSummary.getRuns() >=4) {
                            ball.append("</span>");
                        }
                    }
                    if (ballSummary.isWide()) {
                        ball.append("Wd");
                    } else if (ballSummary.isNoBall()) {
                        ball.append("Nb");
                    } else if (ballSummary.isLegByes()) {
                        ball.append("Lb");
                    } else if (ballSummary.isByes()) {
                        ball.append("B");
                    }
                }
            ball.append(ball.length() <= 38 ? "0</span></span>": "</span></span>");

            overRunsSummaryMap.put(ballSummary.getOverUnique(), ball.toString());
            }


        return overRunsSummaryMap.values().stream().collect(Collectors.joining(" "));
    }

    private BallCommentary createBallCommentary(BBBAggregate bbbAggregate) {
        BallCommentary ballCommentary = new BallCommentary();
        ballCommentary.setBallId(bbbAggregate.getBallId());
        ballCommentary.setBallSummary(bbbAggregate.getBallSummary());
        ballCommentary.setBatsmanSummary(bbbAggregate.getBatsmanSummary());
        ballCommentary.setBowlerSummary(bbbAggregate.getBowlerSummary());
        ballCommentary.setOtherBatsmanSummary(bbbAggregate.getOtherBatsmanSummary());
        ballCommentary.setDismissalSummary(bbbAggregate.getDismissalSummary());
        return ballCommentary;
    }


}
