package com.dbmanagement.GymLife.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.entity.Role;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;

    public CustomUserDetails(Member member) {
        super(member.getUserName(), member.getPassword(), member.isActive(), true, true, true,
                member.mapRolesToAuthorities(member.getRoles()));
        this.id = member.getId();
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.email = member.getEmail();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    // private Collection<SimpleGrantedAuthority>
    // mapRolesToAuthorities(Collection<Role> roles) {
    // Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

    // for (Role role : roles) {
    // SimpleGrantedAuthority tempAuthority = new
    // SimpleGrantedAuthority(role.getName());
    // authorities.add(tempAuthority);
    // }

    // return authorities;
    // }

}
