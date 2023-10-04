package com.dbmanagement.GymLife.service;

import java.util.List;

import com.dbmanagement.GymLife.entity.Transaction;

public interface TransactionService {

    public List<Transaction> processMonthlyMembership(int month, int year);

    public double retrieveThisYearRevenue();
}
