package com.twitchbot.commandadapter;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController("/")
public class WebExample {

    @GetMapping("test")
    public String test(){

        return "test";
    }

    @GetMapping("test2")
    public String test2(){

        return "test2";
    }

    @GetMapping("test3")
    public String test3(){

        return "test3";
    }
}
