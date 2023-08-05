package com.dbmanagement.GymLife.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.dbmanagement.GymLife.entity.Member;

public interface UserService extends UserDetailsService {

    public Member findByUserName(String userName);

}
