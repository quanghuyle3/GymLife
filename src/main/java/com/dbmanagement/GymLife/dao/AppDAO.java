package com.dbmanagement.GymLife.dao;

import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Manufacture;

public interface AppDAO {

    // Bank Account
    void save(BankAccount thisBankAccount);

    BankAccount findBankAccountByAccountNumber(String theAccountNumber);

    void update(BankAccount thisBankAccount);

    void deleteBankAccountByAccountNumber(String theAccountNumber);

    // Manufacture
    void save(Manufacture thisManufacture);

    Manufacture findManufactureById(int theId);

    Manufacture findManufactureByBankAccount(BankAccount bankAccount);

    void update(Manufacture thisManufacture);

    void deleteManufactureById(int theId);

}
