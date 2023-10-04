package com.dbmanagement.GymLife.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;
import com.dbmanagement.GymLife.entity.WorkSchedule;
import com.dbmanagement.GymLife.webObject.WebWorkSchedule;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    public String retrieveAllWorkSchedule(Model theModel, String successMessage, String successfulUpdate,
            String successfulDelete) {

        List<WorkSchedule> allWorkSchedule = appDAO.retrieveAllWorkSchedule();

        theModel.addAttribute("workSchedule", allWorkSchedule);
        if (successMessage != null) {
            theModel.addAttribute("successMessage", successMessage);
        } else if (successfulUpdate != null) {
            theModel.addAttribute("successfulUpdate", successfulUpdate);
        } else if (successfulDelete != null) {
            theModel.addAttribute("successfulDelete", successfulDelete);
        }

        return "retrieve/work-schedule-retrieve";
    }

    @GetMapping("/schedule-shift")
    public String showWorkScheduleForm(Model theModel) {

        WebWorkSchedule webWorkSchedule = new WebWorkSchedule();
        webWorkSchedule.setPreStaffList(memberDAO.retrieveAllStaffsWithoutOwner());
        webWorkSchedule.setPreTimeStrings(getTimeStringsInADay());

        theModel.addAttribute("webWorkSchedule", webWorkSchedule);
        return "add/work-schedule-form";
    }

    @PostMapping("/schedule-process")
    public String processSchedule(@Valid @ModelAttribute("webWorkSchedule") WebWorkSchedule theWebWorkSchedule,
            BindingResult theBindingResult, Model theModel) {

        // form validation
        if (theBindingResult.hasErrors()) {
            System.out.println(theBindingResult.getAllErrors());
            // set the list of all staffs and time strings again before returning the view
            theWebWorkSchedule.setPreStaffList(memberDAO.retrieveAllStaffsWithoutOwner());
            theWebWorkSchedule.setPreTimeStrings(getTimeStringsInADay());

            return "add/work-schedule-form";
        }
        WorkSchedule ws = new WorkSchedule();
        ws.setStaffId(memberDAO.findMemberById(theWebWorkSchedule.getStaffId()));
        ws.setWorkDate(theWebWorkSchedule.getWorkDate());
        ws.setTimeStart(theWebWorkSchedule.getTimeStart());
        ws.setTimeEnd(theWebWorkSchedule.getTimeEnd());

        // save the shift
        // check for violation constraint
        try {
            appDAO.save(ws);
        } catch (JpaSystemException e) {
            theModel.addAttribute("violationConstraint", "Error: There is conflict in time/date. Please try again.");

            // set the list of all staffs and time strings again before returning the view
            theWebWorkSchedule.setPreStaffList(memberDAO.retrieveAllStaffsWithoutOwner());
            theWebWorkSchedule.setPreTimeStrings(getTimeStringsInADay());
            return "add/work-schedule-form";
        }

        // add message
        String successMessage = "Successfully scheduled a shift - ID: " + ws.getId();

        return retrieveAllWorkSchedule(theModel, successMessage, null, null);
    }

    @GetMapping("/update")
    public String updateAWorkSchedule(@RequestParam("wsId") int wsId, Model theModel) {

        // retrieve the work schedule need to be updated
        WorkSchedule theWorkSchedule = appDAO.findWorkScheduleById(wsId);

        // convert work schedule to webWorkSchedule to display in template
        WebWorkSchedule webWorkSchedule = new WebWorkSchedule(theWorkSchedule,
                memberDAO.retrieveAllStaffsWithoutOwner(), this.getTimeStringsInADay());

        // add to model attribute to display in view
        theModel.addAttribute("webWorkSchedule", webWorkSchedule);

        return "update/work-schedule-update";
    }

    @PostMapping("/update/process")
    public String processUpdate(@Valid @ModelAttribute("webWorkSchedule") WebWorkSchedule theWebWorkSchedule,
            BindingResult theBindingResult, Model theModel) {

        // form validation - only if form sent has any field with type text

        // retrieve the current work schedule
        WorkSchedule ws = appDAO.findWorkScheduleById(theWebWorkSchedule.getId());

        // it should have a variable String error var to pass in constructor,
        // if don't have, we can reuse another String var to hold message
        // which happening in this case
        if (ws == null) {
            String successfulDelete = "Sorry... This work schedule doesn't exist anymore.";
            return retrieveAllWorkSchedule(theModel, null, null, successfulDelete);
        }

        // update with new info
        ws.setStaffId(memberDAO.findMemberById(theWebWorkSchedule.getStaffId()));
        ws.setWorkDate(theWebWorkSchedule.getWorkDate());
        ws.setTimeStart(theWebWorkSchedule.getTimeStart());
        ws.setTimeEnd(theWebWorkSchedule.getTimeEnd());

        // save the shift
        // check for violation constraint
        try {
            appDAO.update(ws);
        } catch (JpaSystemException e) {
            theModel.addAttribute("violationConstraint", "Error: There is conflict in time/date. Please try again.");

            // set the list of all staffs and time strings again before returning the view
            theWebWorkSchedule.setPreStaffList(memberDAO.retrieveAllStaffsWithoutOwner());
            theWebWorkSchedule.setPreTimeStrings(getTimeStringsInADay());
            return "update/work-schedule-update";
        }

        // add message
        String successfulUpdate = "Successfully updated a shift - ID: " + ws.getId();

        return retrieveAllWorkSchedule(theModel, null, successfulUpdate, null);
    }

    @GetMapping("/delete")
    public String deleteAWorkSchedule(@RequestParam("wsId") int wsId, Model theModel) {

        appDAO.deleteWorkScheduleById(wsId);

        String successfulDelete = "Successfully deleted schedule ID: " + wsId;
        return retrieveAllWorkSchedule(theModel, null, null, successfulDelete);
    }

    private List<String> getTimeStringsInADay() {
        List<String> timeList = new ArrayList<>();

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
