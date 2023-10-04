package com.dbmanagement.GymLife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;
import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.entity.Role;
import com.dbmanagement.GymLife.service.UserService;
import com.dbmanagement.GymLife.webObject.WebMember;
import com.dbmanagement.GymLife.webObject.WebStaff;

import java.security.Principal;
import java.util.Collection;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UpdateController {

    private AppDAO appDAO;
    private MemberDAO memberDAO;
    private RoleDAO roleDAO;
    private UserService userService;

    @Autowired
    public UpdateController(AppDAO appDAO, MemberDAO memberDAO, RoleDAO roleDAO, UserService userService) {
        this.appDAO = appDAO;
        this.memberDAO = memberDAO;
        this.roleDAO = roleDAO;
        this.userService = userService;
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

    @GetMapping("/my-profile/update")
    public String showUpdateProfile(Principal principal, Model theModel) {
        // retrieve the member info
        Member theMember = memberDAO.findByUserName(principal.getName());

        if (theMember.checkContainARole(roleDAO.findRoleByName("ROLE_GYMMER"))) {
            // convert member to webMember to display in template
            WebMember webMember = new WebMember(theMember, appDAO.retrieveAllMembershipTypes());

            // add to model attribute to display in view
            theModel.addAttribute("webMember", webMember);

            return "update/member-update";
        } else {
            // convert member to webStaff to display in template
            WebStaff webStaff = new WebStaff(theMember, roleDAO.retrieveAllStaffRoleStrings());

            // add to model attribute to display in view
            theModel.addAttribute("webMember", webStaff);

            return "update/staff-update";
        }

    }

    @PostMapping("/my-profile/update/process")
    public String processUpdateProfile(@Valid @ModelAttribute("webMember") WebMember theWebMember,
            BindingResult theBindingResult, HttpSession session, Model theModel, Principal principal) {

        Member currentUpdatingMember = memberDAO.findMemberById(theWebMember.getId());

        // form validation
        if (theBindingResult.hasErrors()) {
            return "update/member-update";
        }

        // check if bank number is already in database
        String bankAccountNumber = theWebMember.getBankAccountNumber();
        BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(bankAccountNumber);
        Member associatedMember = null;
        // find associated member with that new bank account
        if (bankAccount != null) {
            associatedMember = bankAccount.getMember();
        }

        if (bankAccount != null && bankAccount != currentUpdatingMember.getBankAccountNumber()
                && associatedMember != null) {
            // bind the error message to a model attribute to display in View
            theModel.addAttribute("registrationError", "Bank account number already associated with another member.");

            return "update/member-update";
        }

        userService.update(theWebMember);

        return redirectAfterUpdate(theWebMember.getId(), theModel, principal);
    }

    @PostMapping("/my-profile-staff/update/process")
    public String processUpdateProfile(@Valid @ModelAttribute("webMember") WebStaff theWebStaff,
            BindingResult theBindingResult, HttpSession session, Model theModel, Principal principal) {

        Member currentUpdatingMember = memberDAO.findMemberById(theWebStaff.getId());

        // form validation
        if (theBindingResult.hasErrors()) {
            return "update/staff-update";
        }

        // check if bank number is already in database
        String bankAccountNumber = theWebStaff.getBankAccountNumber();
        BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(bankAccountNumber);
        Member associatedMember = null;
        // find associated member with that new bank account
        if (bankAccount != null) {
            associatedMember = bankAccount.getMember();
        }

        if (bankAccount != null && bankAccount != currentUpdatingMember.getBankAccountNumber()
                && associatedMember != null) {
            // bind the error message to a model attribute to display in View
            theModel.addAttribute("registrationError", "Bank account number already associated with another member.");

            return "update/staff-update";
        }

        userService.update(theWebStaff);

        return redirectAfterUpdate(theWebStaff.getId(), theModel, principal);
    }

    private String redirectAfterUpdate(int memberId, Model theModel, Principal principal) {

        // retrieve updated member with all updated info
        Member currentUpdatingMember = memberDAO.findMemberById(memberId);

        // retrieve current logged-in member
        Member loggedInMember = memberDAO.findByUserName(principal.getName());

        // CASE 1: current logged-in user update its info
        if (memberId == loggedInMember.getId()) {
            // set the member to model attribute
            theModel.addAttribute("member", currentUpdatingMember);

            return "my-profile";
        }
        // CASE 2: a manager updates info for a member/staff
        else {
            // add message
            String message = "Successfully updated member: " + currentUpdatingMember.getId() + " - "
                    + currentUpdatingMember.getFirstName() + " "
                    + currentUpdatingMember.getLastName() + ".";
            theModel.addAttribute("successfulUpdate", message);

            // if updating a member, return to members retrieve page
            if (currentUpdatingMember.checkContainARole(roleDAO.findRoleByName("ROLE_GYMMER"))) {
                // set the member list for UI in case it hasnt been loaded yet
                theModel.addAttribute("members", memberDAO.retrieveAllGymmers());
                return "retrieve/members-retrieve";
            }
            // if updating a staff, return to staffs retrieve page
            else {
                // set the member list for UI in case it hasnt been loaded yet
                theModel.addAttribute("members", memberDAO.retrieveAllStaffsWithoutOwner());
                return "retrieve/staffs-retrieve";
            }
        }

    }
}
