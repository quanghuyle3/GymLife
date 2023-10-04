package com.dbmanagement.GymLife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.service.UserService;
import com.dbmanagement.GymLife.webObject.WebMember;

@Controller
@RequestMapping("/members")
public class MemberController {

    private AppDAO appDAO;
    private MemberDAO memberDAO;
    private RoleDAO roleDAO;
    private UserService userService;

    @Autowired
    public MemberController(AppDAO appDAO, MemberDAO memberDAO, RoleDAO roleDAO, UserService userService) {
        this.appDAO = appDAO;
        this.memberDAO = memberDAO;
        this.roleDAO = roleDAO;
        this.userService = userService;
    }

    @GetMapping("/retrieve")
    public String retrieveAllMembers(Model theModel, String successfulRegistration, String successfulUpdate,
            String successfulDelete) {
        List<Member> allMembers = memberDAO.retrieveAllGymmersDESC();

        // add this object attribute values to spring model attribute
        theModel.addAttribute("members", allMembers);
        if (successfulRegistration != null) {
            theModel.addAttribute("successMessage", successfulRegistration);
        } else if (successfulUpdate != null) {
            theModel.addAttribute("successfulUpdate", successfulUpdate);
        } else if (successfulDelete != null) {
            theModel.addAttribute("successfulDelete", successfulDelete);
        }
        return "retrieve/members-retrieve";
    }

    @GetMapping("update")
    public String updateAMember(@RequestParam("memberId") int memberId, Model theModel) {
        System.out.println("Updating info for member: " + memberId);

        // retrieve the member need to be update
        Member theMember = memberDAO.findMemberById(memberId);

        // convert member to webMember to display in template
        WebMember webMember = new WebMember(theMember,
                appDAO.retrieveAllMembershipTypes());

        // add to model attribute to display in view
        theModel.addAttribute("webMember", webMember);

        return "update/member-update";
    }

    @GetMapping("delete")
    public String deleteAMember(@RequestParam("memberId") int memberId, Model theModel) {
        // WARNING: This will lead to erased data in Training, WorkSchedule, AccessLog
        // (to avoid conflict in SQL table)
        // So only preceed this if a member request to delete their personal data from
        // the system.
        userService.delete(memberId);

        String successfulDelete = "Successfully deleted member: " + memberId;
        return retrieveAllMembers(theModel, null, null, successfulDelete);
    }

}
