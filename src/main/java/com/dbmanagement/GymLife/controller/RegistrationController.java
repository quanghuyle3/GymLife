package com.dbmanagement.GymLife.controller;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;
import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.service.UserService;
import com.dbmanagement.GymLife.webObject.WebMember;
import com.dbmanagement.GymLife.webObject.WebStaff;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private Logger logger = Logger.getLogger(getClass().getName());

    private UserService userService;
    private AppDAO appDAO;
    private MemberDAO memberDAO;
    private RoleDAO roleDAO;

    @Autowired
    public RegistrationController(UserService userService, AppDAO appDAO, MemberDAO memberDAO, RoleDAO roleDAO) {
        this.userService = userService;
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

    @GetMapping("/memberForm")
    public String showMemberForm(Model theModel) {

        // BINDING THE MODEL ATTRIBUTE HERE
        // get a list of all membership type names first (to present in frontend)
        WebMember webMember = new WebMember();
        webMember.setPreMembershipTypes(appDAO.retrieveAllMembershipTypes());
        theModel.addAttribute("webMember", webMember);
        return "register/member-form";
    }

    @PostMapping("/memberProcess")
    public String processMemberForm(@Valid @ModelAttribute("webMember") WebMember theWebMember,
            BindingResult theBindingResult, HttpSession session, Model theModel, Principal principal) {

        String userName = theWebMember.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()) {
            // set the list of membership types again before returning the view
            theWebMember.setPreMembershipTypes(appDAO.retrieveAllMembershipTypes());

            return "register/member-form";
        }

        // check if username already exists in database
        Member existing = userService.findByUserName(userName);
        if (existing != null) {
            // bind the new WebMember object to a model attribute
            // for new info from the form that appears after this
            WebMember webMember = new WebMember();
            webMember.setPreMembershipTypes(appDAO.retrieveAllMembershipTypes());
            theModel.addAttribute("webMember", webMember);

            // bind the error message to a model attribute to display in View
            theModel.addAttribute("registrationError", "Username already exists.");

            logger.warning("Username already exists.");
            return "register/member-form";
        }

        // check if bank number is already in database
        String bankAccountNumber = theWebMember.getBankAccountNumber();
        BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(bankAccountNumber);
        if (bankAccount != null) {
            // bind the error message to a model attribute to display in View
            theModel.addAttribute("registrationError", "Bank account number already associated with another member.");
            logger.warning("Bank account already exists.");

            // set the list of membership types again before returning the view
            theWebMember.setPreMembershipTypes(appDAO.retrieveAllMembershipTypes());
            return "register/member-form";
        }

        Member newMember = userService.save(theWebMember, "ROLE_GYMMER");

        logger.info("Successfully created user: " + userName);

        // place a charge on new member / process a transaction
        // (happened inside userService.save())

        // return "/register/member-confirmation";
        return redirectAfterRegister(newMember.getId(), principal, theModel);
    }

    // Note: Model attribute named "webMember" but it's actually WebStaff Object
    @GetMapping("/staffForm")
    public String showStaffForm(Model theModel) {

        // BINDING THE MODEL ATTRIBUTE HERE
        // get a list of all roles first (to present in frontend)
        WebStaff webStaff = new WebStaff();
        webStaff.setPreRoleStrings(roleDAO.retrieveAllStaffRoleStrings());
        theModel.addAttribute("webMember", webStaff);
        // return "register/member-form";
        return "register/staff-form";
    }

    // Note: Model attribute named "webMember" but it's actually WebStaff Object
    @PostMapping("/staffProcess")
    public String processStaffForm(@Valid @ModelAttribute("webMember") WebStaff theWebStaff,
            BindingResult theBindingResult, HttpSession session, Model theModel, Principal principal) {

        String userName = theWebStaff.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        // Exclude membershipType field from binding and validation
        if (theBindingResult.hasErrors()) {
            // set the list of all roles again before returning the view
            theWebStaff.setPreRoleStrings(roleDAO.retrieveAllStaffRoleStrings());

            return "register/staff-form";
        }

        // check if username already exists in database
        Member existing = userService.findByUserName(userName);
        if (existing != null) {
            // bind the new WebStaff object to a model attribute
            // for new info from the form that appears after this
            WebStaff webStaff = new WebStaff();
            // set the list of all roles again before returning the view
            webStaff.setPreRoleStrings(roleDAO.retrieveAllStaffRoleStrings());
            theModel.addAttribute("webMember", webStaff);

            // bind the error message to a model attribute to display in View
            theModel.addAttribute("registrationError", "Username already exists.");

            logger.warning("Username already exists.");
            return "register/staff-form";
        }

        // check if bank number is already in database
        String bankAccountNumber = theWebStaff.getBankAccountNumber();
        BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(bankAccountNumber);
        if (bankAccount != null) {
            // bind the error message to a model attribute to display in View
            theModel.addAttribute("registrationError", "Bank account number already associated with another member.");
            logger.warning("Bank account already exists.");

            // set the list of all roles again before returning the view
            theWebStaff.setPreRoleStrings(roleDAO.retrieveAllStaffRoleStrings());
            return "register/staff-form";
        }

        Member newMember = userService.save(theWebStaff, theWebStaff.getRole());

        logger.info("Successfully created staff: " + theWebStaff.getFirstName() + " " + theWebStaff.getLastName());

        return redirectAfterRegister(newMember.getId(), principal, theModel);
    }

    private String redirectAfterRegister(int registeredMemberId, Principal principal, Model theModel) {
        Member member = memberDAO.findMemberById(registeredMemberId);

        // a new staff just registered
        // meaning a manager just did so. Return to the staffs retrieve page
        if (!member.checkContainARole(roleDAO.findRoleByName("ROLE_GYMMER"))) {
            List<Member> allMembers = memberDAO.retrieveAllStaffsWithoutOwner();

            // add this object attribute values to spring model attribute
            theModel.addAttribute("members", allMembers);
            // add message
            String message = "Successfully added staff: " + member.getId() + " - " + member.getFirstName() + " "
                    + member.getLastName() + ".";
            theModel.addAttribute("successfulRegistration", message);
            return "retrieve/staffs-retrieve";
        }

        // a staff just added a new member
        else if (principal != null) {
            List<Member> allMembers = memberDAO.retrieveAllGymmers();

            // add this object attribute values to spring model attribute
            theModel.addAttribute("members", allMembers);
            // add message
            String message = "Successfully added member: " + member.getId() + " - " + member.getFirstName() + " "
                    + member.getLastName() + ".";
            theModel.addAttribute("successfulRegistration", message);
            return "retrieve/members-retrieve";
        }

        // a normal user registered as new member
        else {
            theModel.addAttribute("successfulRegistration", "Successfully registered as a new member");
            return "login";
        }
    }
}
