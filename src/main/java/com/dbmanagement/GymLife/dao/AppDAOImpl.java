package com.dbmanagement.GymLife.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dbmanagement.GymLife.entity.BankAccount;

import jakarta.persistence.EntityManager;

@Repository
public class AppDAOImpl implements AppDAO {

    private EntityManager entityManager;

    // inject entity manager using constructor injection
    // entity manager will communicate with database source
    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // BANK ACCOUNT

    @Override
    @Transactional
    public void save(BankAccount thisBankAccount) {
        entityManager.persist(thisBankAccount);
    }
}
