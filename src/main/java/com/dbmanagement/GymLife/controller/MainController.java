package com.dbmanagement.GymLife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // @GetMapping("member")
    // public String showMember() {
    // return "member";
    // }
}
