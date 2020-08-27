package com.sport.cricket.cricketapi.controller;

import com.sport.cricket.cricketapi.domain.response.Greeting;
import com.sport.cricket.cricketapi.domain.response.home.CricketHome;
import com.sport.cricket.cricketapi.service.CricketHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HomeController {


    @Autowired
    CricketHomeService cricketHomeService;


    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        throw new RuntimeException("Exceptions");
        //return new Greeting("Hello world");
    }



    @GetMapping("/home")
    public CricketHome home() {
        return cricketHomeService.populateCricketHomePage();
    }

}
