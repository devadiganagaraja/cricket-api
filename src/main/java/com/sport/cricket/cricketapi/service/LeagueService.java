package com.sport.cricket.cricketapi.service;

import com.cricketfoursix.cricketdomain.aggregate.LeagueAggregate;
import com.cricketfoursix.cricketdomain.common.league.LeagueInfo;
import com.cricketfoursix.cricketdomain.common.league.LeagueSeason;
import com.cricketfoursix.cricketdomain.repository.LeagueRepository;
import com.sport.cricket.cricketapi.domain.response.league.LeagueDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class LeagueService {

    @Autowired
    LeagueRepository leagueRepository;


    public List<LeagueDetails> getLeagues() {
        List<LeagueDetails> leagueDetailsList = new ArrayList<>();
        List<com.cricketfoursix.cricketdomain.aggregate.LeagueAggregate> leagueAggregates = leagueRepository.findAll();
        if(null != leagueAggregates){
            leagueAggregates.forEach(leagueAggregate -> {
                LeagueInfo leagueInfo = leagueAggregate.getLeagueInfo();
                if(isTrendingLeague(leagueInfo)){
                    leagueDetailsList.add(populateLeagueDetails(leagueInfo));
                }
            });
        }
        return  leagueDetailsList;
    }

    private LeagueDetails populateLeagueDetails(  LeagueInfo leagueInfo) {
        LeagueDetails leagueDetails = new LeagueDetails();
        leagueDetails.setId(leagueInfo.getLeagueId());
        leagueDetails.setName(leagueInfo.getLeagueName());
        leagueDetails.setAbbreviation(leagueInfo.getAbbreviation());
        leagueDetails.setTournament(leagueInfo.isTournament());
        return  leagueDetails;
    }

    public LeagueDetails getLeagueInfo(Long leagueId) {
        Optional<LeagueAggregate> leagueAggregateOptional = leagueRepository.findById(leagueId);
        if(leagueAggregateOptional.isPresent()){
            LeagueAggregate leagueAggregate =  leagueAggregateOptional.get();
            LeagueInfo leagueInfo = leagueAggregate.getLeagueInfo();
            return populateLeagueDetails(leagueInfo);
        }
        else return null;
    }

    private boolean isTrendingLeague(LeagueInfo leagueInfo) {
        Map<Integer, LeagueSeason> seasonsMap =  leagueInfo.getLeagueSeasonMap();
        Optional<LeagueSeason> leagueSeasonOpt = geltLatestLeagueSeason(seasonsMap);
        if(leagueSeasonOpt.isPresent()){
            LeagueSeason leagueSeason = leagueSeasonOpt.get();
            Date startDate = leagueSeason.getStartDate();
            Date endDate = leagueSeason.getEndDate();

            if(null != startDate ){
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 10);
                if(startDate.compareTo(cal.getTime()) > 0){
                    return false;
                }
            }
            if(null != endDate ){
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -10);
                if(endDate.compareTo(cal.getTime()) < 0){
                    return false;
                }
            }
        }

        return true;
    }



    private Optional<LeagueSeason> geltLatestLeagueSeason(Map<Integer, LeagueSeason> seasonsMap) {
        Optional<LeagueSeason> leagueSeasonOpt = Optional.empty();
        if(null != seasonsMap && seasonsMap.size() > 0 ){
            List<Integer> seasonKeyList =  new ArrayList<>(seasonsMap.keySet());
            Collections.sort(seasonKeyList);
            leagueSeasonOpt = Optional.ofNullable(seasonsMap.get(seasonKeyList.get(seasonKeyList.size() -1)));

        }
        return leagueSeasonOpt;
    }


}
