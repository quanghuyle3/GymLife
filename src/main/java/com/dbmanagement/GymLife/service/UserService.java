package com.dbmanagement.GymLife.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.webObject.WebMember;
import com.dbmanagement.GymLife.webObject.WebStaff;

public interface UserService extends UserDetailsService {

    public Member findByUserName(String userName);

    // For saving a gymmer (member)
    public Member save(WebMember webMember, String role);

    // For saving a staff
    public Member save(WebStaff webMember, String role);

    // For updating a gymmer (member)
    public void update(WebMember webMember);

    // For updating a staff
    public void update(WebStaff webStaff);

    // For deleting any member (gymmer & staff)
    // WARNING: All Trainings, Work Schedule, Access Log associated with this member
    // will be deleted
    public void delete(int id);

    public List<Member> retrieveAllGymmers();

    public List<Integer> retrieveTotalGymmersByMonth();

    public List<Member> retrieveAllStaff();
}
