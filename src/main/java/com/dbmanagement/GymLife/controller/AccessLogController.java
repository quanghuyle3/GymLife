package com.dbmanagement.GymLife.controller;

import java.util.List;

import com.dbmanagement.GymLife.entity.AccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;

@Controller
@RequestMapping("/access-log")
public class AccessLogController {

    public AppDAO appDAO;
    public MemberDAO memberDAO;
    public RoleDAO roleDAO;

    @Autowired
    public AccessLogController(AppDAO appDAO, MemberDAO memberDAO, RoleDAO roleDAO) {
        this.appDAO = appDAO;
        this.memberDAO = memberDAO;
        this.roleDAO = roleDAO;
    }

    @GetMapping("/retrieve")
    public String retrieveAllAccessLogs(Model theModel) {

        List<AccessLog> allAccessLog = appDAO.retrieveAllAccessLog();

        theModel.addAttribute("accessLog", allAccessLog);

        return "retrieve/access-log-retrieve";
    }

}
