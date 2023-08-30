package com.dbmanagement.GymLife.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.entity.Member;

@Controller
public class MainController {

    private AppDAO appDAO;
    private MemberDAO memberDAO;

    @Autowired
    public MainController(AppDAO appDAO, MemberDAO memberDAO) {
        this.appDAO = appDAO;
        this.memberDAO = memberDAO;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/template")
    public String templatePage() {
        return "template";
    }

    @GetMapping("/dashboard")
    public String showDashBoard() {
        return "dashboard";
    }

    @GetMapping("/my-profile/view")
    public String showMyProfile(Principal principal, Model theModel) {
        // retrieve the current member info
        Member theMember = memberDAO.findByUserName(principal.getName());

        // set the member to model attribute
        theModel.addAttribute("member", theMember);

        return "my-profile";
    }

}
