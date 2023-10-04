package com.dbmanagement.GymLife.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.entity.Transaction;

@Service
public class TransactionServiceImp implements TransactionService {

    private MemberDAO memberDAO;
    private AppDAO appDAO;

    @Autowired
    public TransactionServiceImp(MemberDAO memberDAO, AppDAO appDAO) {
        this.memberDAO = memberDAO;
        this.appDAO = appDAO;
    }

    @Override
    public List<Transaction> processMonthlyMembership(int month, int year) {
        List<Member> allGymmers = memberDAO.retrieveAllGymmers();
        List<Transaction> insertions = new ArrayList<>();
        Member gym = memberDAO.retrieveOwner();

        if (gym == null) {
            return new ArrayList<>();
        }

        BankAccount gymAccount = gym.getBankAccountNumber();

        for (Member m : allGymmers) {
            // skip if user is inactive
            if (!m.isActive()) {
                continue;
            }

            double membershipPrice = m.getMembershipType().getPrice();

            String dataJoin = m.getDateJoin();
            int yearJoin = Integer.parseInt(dataJoin.substring(0, 4));
            int monthJoin = Integer.parseInt(dataJoin.substring(5, 7));
            String dateJoin = dataJoin.substring(8, 10);
            if (yearJoin <= year && monthJoin <= month) {
                String dateProcess = String.valueOf(year) + "-" + String.valueOf(month) + "-"
                        + String.valueOf(dateJoin);

                Transaction transaction = new Transaction(m.getBankAccountNumber(),
                        gymAccount, membershipPrice, dateProcess);

                // save transaction
                appDAO.save(transaction);

                // add to result list
                insertions.add(transaction);
            }
        }

        return insertions;
    }

    @Override
    public double retrieveThisYearRevenue() {
        List<Transaction> allTransactions = appDAO.retrieveAllTransaction();
        double revenue = 0;

        BankAccount gymAccount = memberDAO.retrieveOwner().getBankAccountNumber();

        for (Transaction t : allTransactions) {
            if (gymAccount == t.getAccountReceive()) {
                revenue += t.getAmount();
            }

        }
        return revenue;
    }

}
