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
import com.dbmanagement.GymLife.webObject.WebStaff;

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
    public String retrieveAllMembers(Model theModel) {
        List<Member> allMembers = memberDAO.retrieveAllGymmers();

        // add this object attribute values to spring model attribute
        theModel.addAttribute("members", allMembers);
        return "members-retrieve";
    }

    @GetMapping("update")
    public String updateAMember(@RequestParam("memberId") int memberId, Model theModel) {
        System.out.println("Updating info for member: " + memberId);

        // retrieve the member need to be update
        Member theMember = memberDAO.findMemberById(memberId);

        if (theMember.checkContainARole(roleDAO.findRoleByName("ROLE_GYMMER"))) {
            // convert member to webMember to display in template
            WebMember webMember = new WebMember(theMember,
                    appDAO.retrieveAllMembershipTypes());

            // add to model attribute to display in view
            theModel.addAttribute("webMember", webMember);

            return "member-update";
        } else {
            // convert member to webStaff to display in template
            WebStaff webStaff = new WebStaff(theMember,
                    roleDAO.retrieveAllStaffRoleStrings());

            // add to model attribute to display in view
            theModel.addAttribute("webMember", webStaff);

            return "staff-update";
        }
    }

    @GetMapping("delete")
    public String deleteAMember(@RequestParam("memberId") int memberId, Model theModel) {
        // WARNING: This will lead to erased data in Training, WorkSchedule, AccessLog
        // (to avoid conflict in SQL table)
        // So only preceed this if a member request to delete their personal data from
        // the system.
        System.out.println("Deleting member: " + memberId);
        userService.delete(memberId);

        List<Member> allMembers = memberDAO.retrieveAllGymmers();
        theModel.addAttribute("members", allMembers);
        String message = "Successfully deleted member: " + memberId;
        theModel.addAttribute("successfulDelete", message);

        return "members-retrieve";
    }

}
