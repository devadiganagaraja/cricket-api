package com.sport.cricket.cricketapi.service;

import com.sport.cricket.cricketapi.domain.source.Athlete;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class PlayerNameService {

    ConcurrentMap<Long,String> playerNameCache = new ConcurrentHashMap<>();

    RestTemplate restTemplate = new RestTemplate();

    public long getPlayerId(long sourcePlayerId){
        return sourcePlayerId*13;

    }

    public long getPlayerId(String playerName){
        if(StringUtils.isNotBlank(playerName)){
            return  Long.valueOf(playerName.split(":")[1]);
        }
        return 0;

    }


    public String getPlayerNameByPlayerId(Long playerId){
        try {

            if(playerNameCache.containsKey(playerId)){
                return playerNameCache.get(playerId);
            }else{
                Athlete athlete = restTemplate.getForObject("http://core.espnuk.org/v2/sports/cricket/athletes/"+(playerId/13), Athlete.class);
                String displayNameWithId = athlete.getDisplayName()+":"+(playerId);
                if(null != athlete.getPosition()){
                    displayNameWithId  = displayNameWithId.concat(":").concat(PlayerService.getBattingStyle(athlete.getPosition().getName()));
                }
                playerNameCache.putIfAbsent(playerId, displayNameWithId);
                return displayNameWithId;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "null:0:null";
    }

    public String getPlayerName(Long sourcePlayerName){
        long playerId = getPlayerId(sourcePlayerName);
        try {

            if(playerNameCache.containsKey(playerId)){
                return playerNameCache.get(playerId);
            }else{
                Athlete athlete = restTemplate.getForObject("http://core.espnuk.org/v2/sports/cricket/athletes/"+sourcePlayerName, Athlete.class);
                String displayNameWithId = athlete.getDisplayName()+":"+(playerId);
                if(null != athlete.getPosition()){
                    displayNameWithId  = displayNameWithId.concat(":").concat(PlayerService.getBattingStyle(athlete.getPosition().getName()));
                }
                playerNameCache.putIfAbsent(playerId, displayNameWithId);
                return displayNameWithId;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "null:0:null";
    }

}
