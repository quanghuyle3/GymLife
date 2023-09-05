package com.dbmanagement.GymLife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.service.UserService;

@Controller
@RequestMapping("/staffs")
public class StaffController {

    private UserService userService;
    private MemberDAO memberDAO;

    @Autowired
    public StaffController(UserService userService, MemberDAO memberDAO) {
        this.userService = userService;
        this.memberDAO = memberDAO;
    }

    @GetMapping("/retrieve")
    public String retrieveAllStaffs(Model theModel) {
        List<Member> allMembers = memberDAO.retrieveAllStaffsWithoutOwner();

        // add this object attribute values to spring model attribute
        theModel.addAttribute("members", allMembers);
        return "retrieve/staffs-retrieve";
    }

    @GetMapping("delete")
    public String deleteAMember(@RequestParam("memberId") int memberId, Model theModel) {
        // WARNING: This will lead to erased data in Training, WorkSchedule, AccessLog
        // (to avoid conflict in SQL table)
        // So only preceed this if a member request to delete their personal data from
        // the system.
        userService.delete(memberId);

        List<Member> allMembers = memberDAO.retrieveAllStaffsWithoutOwner();
        theModel.addAttribute("members", allMembers);
        String message = "Successfully deleted staff: " + memberId;
        theModel.addAttribute("successfulDelete", message);

        return "retrieve/staffs-retrieve";
    }

}
