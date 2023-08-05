package com.dbmanagement.GymLife.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.entity.Role;

@Service
public class UserServiceImpl implements UserService {

    private MemberDAO memberDAO;

    @Autowired
    public UserServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public Member findByUserName(String userName) {
        // find the member by its username
        // or check if it exists a member with this username
        // return either a member or null
        return memberDAO.findByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberDAO.findByUserName(username);

        if (member == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        // if (!member.isActive()) {
        // throw new DisabledException("This user is not active.");
        // }

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(member.getRoles());

        // return new
        // org.springframework.security.core.userdetails.User(member.getUserName(),
        // member.getPassword(),
        // authorities);
        return new org.springframework.security.core.userdetails.User(member.getUserName(), member.getPassword(),
                member.isActive(), true, true, true,
                authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }

}
