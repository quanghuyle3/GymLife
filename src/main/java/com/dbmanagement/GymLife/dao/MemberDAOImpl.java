package com.dbmanagement.GymLife.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dbmanagement.GymLife.entity.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class MemberDAOImpl implements MemberDAO {

    private EntityManager entityManager;

    @Autowired
    public MemberDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public Member findByUserName(String theUserName) {
        TypedQuery<Member> query = entityManager.createQuery("from Member where userName = :data", Member.class);
        query.setParameter("data", theUserName);

        Member member = null;
        try {
            member = query.getSingleResult();
        } catch (Exception e) {
            member = null;
        }
        return member;
    }

}
