package com.sport.cricket.cricketapi.controller;

import com.sport.cricket.cricketapi.domain.response.Greeting;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@CrossOrigin(origins = "http://www.cricket46.com")
public class HelloController {


    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        throw new RuntimeException("Exceptions");
        //return new Greeting("Hello world");
    }

}
