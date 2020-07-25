package com.sport.cricket.cricketapi.service;

import com.cricketfoursix.cricketdomain.aggregate.BBBAggregate;
import com.cricketfoursix.cricketdomain.repository.BallRepository;
import com.sport.cricket.cricketapi.domain.response.BallCommentary;
import com.sport.cricket.cricketapi.domain.response.GameCommentary;
import com.sport.cricket.cricketapi.domain.response.InningsCommentary;
import com.sport.cricket.cricketapi.domain.response.OverCommentary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

                Optional<InningsCommentary> inningsCommentaryOptional = gameCommentary.getInningsCommentary().stream().filter(inningsCommentary -> inningsCommentary.getInningCommentarySummary().getInningsNo() == bbbAggregate.getBallSummary().getInningsNo()).findFirst();

                if (inningsCommentaryOptional.isPresent()) {
                    InningsCommentary inningsCommentary = inningsCommentaryOptional.get();


                    if (inningsCommentary.getInningCommentarySummary().getOversUnique() < bbbAggregate.getInningCommentarySummary().getOversUnique()) {
                        inningsCommentary.setInningCommentarySummary(bbbAggregate.getInningCommentarySummary());
                    }
                    Optional<OverCommentary> overCommentaryOptional = inningsCommentary.getOverCommentarySet().stream().filter(overCommentary -> overCommentary.getOverSummary().getOverNo() == bbbAggregate.getOverSummary().getOverNo()).findFirst();


                    if (overCommentaryOptional.isPresent()) {
                        OverCommentary overCommentary = overCommentaryOptional.get();

                        if (overCommentary.getOverSummary().getOversUnique() <= bbbAggregate.getOverSummary().getOversUnique()) {
                            overCommentary.setOverSummary(bbbAggregate.getOverSummary());
                        }
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
        inningsCommentary.setInningCommentarySummary(bbbAggregate.getInningCommentarySummary());
        createOverCommentary(bbbAggregate, inningsCommentary);
        gameCommentary.getInningsCommentary().add(inningsCommentary);
    }

    private void createOverCommentary(BBBAggregate bbbAggregate, InningsCommentary inningsCommentary) {
        OverCommentary overCommentary = new OverCommentary();
        BallCommentary ballCommentary = createBallCommentary(bbbAggregate);
        overCommentary.getBallCommentarySet().add(ballCommentary);
        overCommentary.setOverNumber(bbbAggregate.getOverSummary().getOverNo());
        overCommentary.setOverSummary(bbbAggregate.getOverSummary());
        inningsCommentary.getOverCommentarySet().add(overCommentary);
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
