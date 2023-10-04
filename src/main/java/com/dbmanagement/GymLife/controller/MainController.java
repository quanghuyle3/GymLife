package com.dbmanagement.GymLife.controller;

import java.security.Principal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.service.AccessLogService;
import com.dbmanagement.GymLife.service.MembershipService;
import com.dbmanagement.GymLife.service.TransactionService;
import com.dbmanagement.GymLife.service.UserService;

@Controller
public class MainController {

    private AppDAO appDAO;
    private MemberDAO memberDAO;
    private UserService userService;
    private TransactionService transactionService;
    private MembershipService membershipService;
    private AccessLogService accessLogService;

    @Autowired
    public MainController(AppDAO appDAO, MemberDAO memberDAO, UserService userService,
            TransactionService transactionService, MembershipService membershipService,
            AccessLogService accessLogService) {
        this.appDAO = appDAO;
        this.memberDAO = memberDAO;
        this.userService = userService;
        this.transactionService = transactionService;
        this.membershipService = membershipService;
        this.accessLogService = accessLogService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/my-profile/view")
    public String showMyProfile(Principal principal, Model theModel) {
        // retrieve the current member info
        Member theMember = memberDAO.findByUserName(principal.getName());

        // set the member to model attribute
        theModel.addAttribute("member", theMember);

        return "my-profile";
    }

    @GetMapping("/membership")
    public String showMembershipOptions() {
        return "membership";
    }

    @GetMapping("/contact")
    public String showContact() {
        return "contact";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

    @GetMapping("/dashboard")
    public String showDashBoard(Model theModel) {

        List<Member> allGymers = userService.retrieveAllGymmers();
        int totalMembers = allGymers.size();
        List<Member> allStaff = userService.retrieveAllStaff();
        int totalStaff = allStaff.size();
        List<Integer> totalGymmersByMonth = userService.retrieveTotalGymmersByMonth();
        int revenue = (int) Math.floor(transactionService.retrieveThisYearRevenue());
        List<Integer> membershipsPercentage = membershipService.getMembershipsPercentage();
        // System.out.println("Membership percentage: " + membershipsPercentage);

        theModel.addAttribute("totalMembers", totalMembers);
        theModel.addAttribute("totalStaff", totalStaff);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        // format number and remove decimal places
        currencyFormatter.setMaximumFractionDigits(0);
        String lastMonthEarning = currencyFormatter.format(revenue);

        theModel.addAttribute("lastMonthEarning", lastMonthEarning);
        theModel.addAttribute("lastMonthAccess", 2460);

        theModel.addAttribute("membersGrowth", totalGymmersByMonth);
        theModel.addAttribute("onGoingTrainings", 36);
        theModel.addAttribute("finishedTrainings", 78);
        theModel.addAttribute("allAccess", membershipsPercentage.get(0));
        theModel.addAttribute("premium", membershipsPercentage.get(1));
        theModel.addAttribute("basic", membershipsPercentage.get(2));

        theModel.addAttribute("equipment", Arrays.asList(36, 26, 15, 20, 35, 20));

        List<Integer> activity = accessLogService.getTotalAccessByTime();
        theModel.addAttribute("activityMonitor", activity);

        return "dashboard";
    }

}
