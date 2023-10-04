package com.dbmanagement.GymLife.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.entity.Membership;

@Service
public class MembershipServiceImpl implements MembershipService {

    private UserService userService;
    private MemberDAO memberDAO;
    private AppDAO appDAO;

    @Autowired
    public MembershipServiceImpl(UserService userService, MemberDAO memberDAO, AppDAO appDAO) {
        this.userService = userService;
        this.memberDAO = memberDAO;
        this.appDAO = appDAO;
    }

    @Override
    public List<Integer> getMembershipsPercentage() {
        List<Member> allGymmers = memberDAO.retrieveAllGymmers();
        // all access, premium, basic
        Integer[] total = new Integer[] { 0, 0, 0 };
        int count = 0;

        for (Member m : allGymmers) {
            if (!m.isActive()) {
                continue;
            }
            count++;
            Membership membership = m.getMembershipType();
            if (membership.getTypeName().equals("ALL ACCESS")) {
                total[0] += 1;
            } else if (membership.getTypeName().equals("PREMIUM")) {
                total[1] += 1;
            } else {
                total[2] += 1;
            }
        }

        // convert to percentage
        total[0] = (int) ((total[0] / (double) count) * 100.0);
        total[1] = (int) ((total[1] / (double) count) * 100.0);
        total[2] = 100 - total[0] - total[1];

        return Arrays.asList(total);
    }

}
