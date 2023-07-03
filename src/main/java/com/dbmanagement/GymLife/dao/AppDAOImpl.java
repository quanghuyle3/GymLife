package com.dbmanagement.GymLife.dao;

import java.util.ArrayList;
import java.util.List;

import javax.management.AttributeList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Manufacture;
import com.dbmanagement.GymLife.entity.Transaction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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

    @Override
    public BankAccount findBankAccountByAccountNumber(String theAccountNumber) {
        return entityManager.find(BankAccount.class, theAccountNumber);
    }

    @Override
    public void retrieveTransactionSendByBankAccount(BankAccount bankAccount) {
        TypedQuery<Transaction> query = entityManager
                .createQuery("select t from Transaction t where t.accountSend = :data", Transaction.class);
        query.setParameter("data", bankAccount);

        try {
            bankAccount.setTransactionSend(query.getResultList());
        }
        // empty transaction case
        catch (Exception e) {
            bankAccount.setTransactionSend(new ArrayList<>());
        }
    }

    @Override
    @Transactional
    public void update(BankAccount thisBankAccount) {
        entityManager.merge(thisBankAccount);
    }

    @Override
    @Transactional
    public void deleteBankAccountByAccountNumber(String theAccountNumber) {
        // retrieve the correct bank account
        BankAccount theBankAccount = findBankAccountByAccountNumber(theAccountNumber);

        // remove the bank account
        entityManager.remove(theBankAccount);
    }

    // MANUFACTURE

    @Override
    @Transactional
    public void save(Manufacture thisManufacture) {
        entityManager.persist(thisManufacture);
    }

    @Override
    @Transactional
    public void update(Manufacture thisManufacture) {
        entityManager.merge(thisManufacture);
    }

    @Override
    public Manufacture findManufactureById(int theId) {
        return entityManager.find(Manufacture.class, theId);
    }

    @Override
    public Manufacture findManufactureByBankAccount(BankAccount bankAccount) {
        TypedQuery<Manufacture> query = entityManager
                .createQuery("select m from Manufacture m where m.bankAccount = :data", Manufacture.class);

        query.setParameter("data", bankAccount);

        Manufacture manufacture = null;
        try {
            manufacture = query.getSingleResult();
        } catch (Exception e) {
            // no manufacture
        }

        return manufacture;
    }

    @Override
    @Transactional
    public void deleteManufactureById(int theId) {
        // reetrieve the manufacture
        Manufacture manufacture = findManufactureById(theId);

        // delete the manufacture
        entityManager.remove(manufacture);
    }

    // TRANSACTION

    @Override
    @Transactional
    public void save(Transaction thisTransaction) {
        entityManager.persist(thisTransaction);
    }

}
