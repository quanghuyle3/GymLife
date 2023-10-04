package com.dbmanagement.GymLife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.service.UserService;
import com.dbmanagement.GymLife.webObject.WebStaff;

@Controller
@RequestMapping("/staffs")
public class StaffController {

    private UserService userService;
    private MemberDAO memberDAO;
    private RoleDAO roleDAO;

    @Autowired
    public StaffController(UserService userService, MemberDAO memberDAO, RoleDAO roleDAO) {
        this.userService = userService;
        this.memberDAO = memberDAO;
        this.roleDAO = roleDAO;
    }

    @GetMapping("/retrieve")
    public String retrieveAllStaffs(Model theModel, String successfulRegistration, String successfulUpdate,
            String successfulDelete) {
        List<Member> allMembers = memberDAO.retrieveAllStaffsWithoutOwner();

        // add this object attribute values to spring model attribute
        theModel.addAttribute("members", allMembers);
        if (successfulRegistration != null) {
            theModel.addAttribute("successMessage", successfulRegistration);
        } else if (successfulUpdate != null) {
            theModel.addAttribute("successfulUpdate", successfulUpdate);
        } else if (successfulDelete != null) {
            theModel.addAttribute("successfulDelete", successfulDelete);
        }
        return "retrieve/staffs-retrieve";
    }

    @GetMapping("update")
    public String updateAStaff(@RequestParam("memberId") int memberId, Model theModel) {
        System.out.println("Updating info for staff: " + memberId);

        // retrieve the member need to be update
        Member theMember = memberDAO.findMemberById(memberId);

        // convert member to webStaff to display in template
        WebStaff webStaff = new WebStaff(theMember, roleDAO.retrieveAllStaffRoleStrings());

        // add to model attribute to display in view
        theModel.addAttribute("webMember", webStaff);

        return "update/staff-update";
    }

    @GetMapping("delete")
    public String deleteAMember(@RequestParam("memberId") int memberId, Model theModel) {
        // WARNING: This will lead to erased data in Training, WorkSchedule, AccessLog
        // (to avoid conflict in SQL table)
        // So only preceed this if a member request to delete their personal data from
        // the system.
        userService.delete(memberId);

        String successfulDelete = "Successfully deleted staff: " + memberId;
        return retrieveAllStaffs(theModel, null, null, successfulDelete);
    }

}
