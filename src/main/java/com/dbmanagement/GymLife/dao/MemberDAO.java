package com.dbmanagement.GymLife.dao;

import java.util.List;

import com.dbmanagement.GymLife.entity.Member;

public interface MemberDAO {

    Member findByUserName(String theUserName);

    List<Member> retrieveAllMembers();

}
