package com.sport.cricket.cricketapi.controller;

import com.sport.cricket.cricketapi.service.YoutubeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class YoutubeVideoController {


    @Autowired
    YoutubeApiService youtubeApiService;

    @GetMapping(value = "crawl/{keyword}")
    public String crawlVideo(@PathVariable String keyword) {
         youtubeApiService.getYoutubeVideoList(keyword);

         return "success";
    }
}
