package com.dbmanagement.GymLife.dao;

import java.util.List;

import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Manufacture;
import com.dbmanagement.GymLife.entity.Transaction;

public interface AppDAO {

    // Bank Account
    void save(BankAccount thisBankAccount);

    BankAccount findBankAccountByAccountNumber(String theAccountNumber);

    void retrieveTransactionSendByBankAccount(BankAccount bankAccount);

    void update(BankAccount thisBankAccount);

    void deleteBankAccountByAccountNumber(String theAccountNumber);

    // Manufacture
    void save(Manufacture thisManufacture);

    Manufacture findManufactureById(int theId);

    Manufacture findManufactureByBankAccount(BankAccount bankAccount);

    void update(Manufacture thisManufacture);

    void deleteManufactureById(int theId);

    // Transaction
    void save(Transaction thisTransaction);

}
