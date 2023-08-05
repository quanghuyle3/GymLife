package com.dbmanagement.GymLife.dao;

import com.dbmanagement.GymLife.entity.Member;

public interface MemberDAO {

    Member findByUserName(String theUserName);

}
