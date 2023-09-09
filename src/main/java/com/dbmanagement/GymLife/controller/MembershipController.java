package com.dbmanagement.GymLife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;
import com.dbmanagement.GymLife.entity.Membership;
import com.dbmanagement.GymLife.webObject.WebMembership;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/memberships")
public class MembershipController {

    public AppDAO appDAO;
    public MemberDAO memberDAO;
    public RoleDAO roleDAO;

    @Autowired
    public MembershipController(AppDAO appDAO, MemberDAO memberDAO, RoleDAO roleDAO) {
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
    public String retrieveAllMemberships(Model theModel) {

        List<Membership> allMemberships = appDAO.retrieveAllMembership();

        theModel.addAttribute("memberships", allMemberships);

        return "retrieve/memberships-retrieve";
    }

    @GetMapping("add-membership")
    public String showMembershipForm(Model theModel) {
        WebMembership webMembership = new WebMembership();
        theModel.addAttribute("webMembership", webMembership);

        return "add/membership-form";
    }

    @PostMapping("membership-process")
    public String processMembership(@Valid @ModelAttribute("webMembership") WebMembership theWebMembership,
            BindingResult theBindingResult, Model theModel) {

        // form validation
        if (theBindingResult.hasErrors()) {
            return "add/membership-form";
        }

        // check if this membership name exists
        Membership exist = appDAO.findMembershipByName(theWebMembership.getTypeName());
        if (exist != null) {
            theModel.addAttribute("existingError", "Error: This membership name has been existed.");
            WebMembership webMembership = new WebMembership();
            theModel.addAttribute("webMembership", webMembership);
            return "add/membership-form";
        }

        Membership membership = new Membership();

        try {
            membership.setTypeName(theWebMembership.getTypeName());
            membership.setPrice(Double.parseDouble(theWebMembership.getPrice()));
        } catch (NumberFormatException e) {
            theModel.addAttribute("formatError", "Error: Incorrect price format.");
            WebMembership webMembership = new WebMembership();
            theModel.addAttribute("webMembership", webMembership);
            return "add/membership-form";
        }

        // save this new membership
        appDAO.save(membership);

        // add message
        theModel.addAttribute("successMessage", "Successfully added a new Membership - ID: " + membership.getId());

        // retrieve all memberships before returning to the retrieve page
        List<Membership> allMemberships = appDAO.retrieveAllMembership();
        theModel.addAttribute("memberships", allMemberships);

        return "retrieve/memberships-retrieve";
    }

    @GetMapping("delete")
    public String deleteAMembership(@RequestParam("membershipId") int membershipId, Model theModel) {

        appDAO.deleteMembershipById(membershipId);

        // retrieve all memberships before returning to the retrieve page
        List<Membership> allMemberships = appDAO.retrieveAllMembership();
        theModel.addAttribute("memberships", allMemberships);

        String message = "Successfully deleted membership ID: " + membershipId;
        theModel.addAttribute("successfulDelete", message);

        return "retrieve/memberships-retrieve";
    }

}
