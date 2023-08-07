package com.dbmanagement.GymLife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.entity.Member;

@Controller
@RequestMapping("/member")
public class MemberController {

    private MemberDAO memberDAO;

    @Autowired
    public MemberController(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @GetMapping("/retrieve")
    public String retrieveAllMembers(Model theModel) {
        List<Member> allMembers = memberDAO.retrieveAllMembers();

        // add this object attribute values to spring model attribute
        theModel.addAttribute("members", allMembers);
        return "member";
    }

}
