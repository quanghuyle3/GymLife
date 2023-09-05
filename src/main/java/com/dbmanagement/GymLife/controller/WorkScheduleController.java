package com.dbmanagement.GymLife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;
import com.dbmanagement.GymLife.entity.WorkSchedule;

@Controller
@RequestMapping("/work-schedule")
public class WorkScheduleController {

    public AppDAO appDAO;
    public MemberDAO memberDAO;
    public RoleDAO roleDAO;

    @Autowired
    public WorkScheduleController(AppDAO appDAO, MemberDAO memberDAO, RoleDAO roleDAO) {
        this.appDAO = appDAO;
        this.memberDAO = memberDAO;
        this.roleDAO = roleDAO;
    }

    @GetMapping("/retrieve")
    public String retrieveAllWorkSchedule(Model theModel) {

        List<WorkSchedule> allWorkSchedule = appDAO.retrieveAllWorkSchedule();

        theModel.addAttribute("workSchedule", allWorkSchedule);

        return "work-schedule-retrieve";
    }

}
