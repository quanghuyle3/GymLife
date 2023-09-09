package com.dbmanagement.GymLife.controller;

import java.util.ArrayList;
import java.util.List;

import com.dbmanagement.GymLife.entity.AccessLog;
import com.dbmanagement.GymLife.webObject.WebAccessLog;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

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

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {

        // remove whitespace - leading and trailing
        // true: trim to null if only whitespace
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        // pass in the customized trimmer editor object to trim all string all string
        // from web
        // request
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/retrieve")
    public String retrieveAllAccessLogs(Model theModel) {

        List<AccessLog> allAccessLog = appDAO.retrieveAllAccessLog();

        theModel.addAttribute("accessLog", allAccessLog);

        return "retrieve/access-log-retrieve";
    }

    @GetMapping("add-access-log")
    public String showAccessLogForm(Model theModel) {
        WebAccessLog webAccessLog = new WebAccessLog();
        webAccessLog.setPreMemberList(memberDAO.retrieveAllMembers());

        theModel.addAttribute("webAccessLog", webAccessLog);

        return "add/access-log-form";
    }

    @PostMapping("access-log-process")
    public String processMembership(@Valid @ModelAttribute("webAccessLog") WebAccessLog theWebAccessLog,
            BindingResult theBindingResult, Model theModel) {

        // form validation
        if (theBindingResult.hasErrors()) {
            theWebAccessLog.setPreMemberList(memberDAO.retrieveAllMembers());
            return "add/access-log-form";
        }

        AccessLog accessLog = new AccessLog();
        accessLog.setDate(theWebAccessLog.getDate());
        accessLog.setMemberId(memberDAO.findMemberById(theWebAccessLog.getMemberId()));
        accessLog.setTimeAccessIn(theWebAccessLog.getTimeAccessIn());
        if (theWebAccessLog.getTimeAccessOut() != null) {
            accessLog.setTimeAccessOut(theWebAccessLog.getTimeAccessOut());

        }

        // save this new membership
        // check if incorrect time format entered from user
        try {
            appDAO.save(accessLog);
        } catch (Exception e) {
            theModel.addAttribute("timeMisformatted",
                    "Error: Conflicted time or time is misformatted. Please try again.");
            theWebAccessLog.setPreMemberList(memberDAO.retrieveAllMembers());
            return "add/access-log-form";
        }

        // add message
        theModel.addAttribute("successMessage", "Successfully added an access log - ID: " + accessLog.getId());

        // retrieve all access log before returning to the retrieve page
        List<AccessLog> allAccessLog = appDAO.retrieveAllAccessLog();
        theModel.addAttribute("accessLog", allAccessLog);

        return "retrieve/access-log-retrieve";
    }

    private List<String> getTimeStringsInADay() {
        List<String> timeList = new ArrayList<>();
        timeList.add("");

        for (int hour = 0; hour < 24; hour++) { // 24 hours in a day
            for (int minute = 0; minute < 60; minute += 30) { // 0 and 30 minutes
                // Format the hour and minute as a time string
                String hourStr = String.format("%02d", hour); // Two-digit hour
                String minuteStr = String.format("%02d", minute); // Two-digit minute

                // Add the time string to the list
                timeList.add(hourStr + ":" + minuteStr);
            }
        }

        return timeList;

    }

}
