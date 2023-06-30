package com.dbmanagement.GymLife.dao;

import com.dbmanagement.GymLife.entity.BankAccount;

public interface AppDAO {

    // Bank Account
    void save(BankAccount thisBankAccount);

    BankAccount findBankAccountByAccountNumber(String theAccountNumber);

    void deleteBankAccountByAccountNumber(String theAccountNumber);
}
