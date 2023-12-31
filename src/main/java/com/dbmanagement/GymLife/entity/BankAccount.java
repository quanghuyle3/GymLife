package com.dbmanagement.GymLife.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "routine_number")
    private int routineNumber;

    @Column(name = "remaining_balance_owed")
    private double remainingBalanceOwed;

    @OneToOne(mappedBy = "bankAccount", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    private Manufacture manufacture;

    @OneToMany(mappedBy = "accountSend", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    private List<Transaction> transactionSend;

    @OneToMany(mappedBy = "accountReceive", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    private List<Transaction> transactionReceive;

    @OneToOne(mappedBy = "bankAccountNumber", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    private Member member;

    public BankAccount() {
    }

    public BankAccount(String accountNumber, String bankName, int routineNumber) {
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.routineNumber = routineNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getRoutineNumber() {
        return routineNumber;
    }

    public void setRoutineNumber(int routineNumber) {
        this.routineNumber = routineNumber;
    }

    public double getRemainingBalanceOwed() {
        return remainingBalanceOwed;
    }

    public void setRemainingBalanceOwed(double remainingBalanceOwed) {
        this.remainingBalanceOwed = remainingBalanceOwed;
    }

    public Manufacture getManufacture() {
        return manufacture;
    }

    public void setManufacture(Manufacture manufacture) {
        this.manufacture = manufacture;
    }

    public List<Transaction> getTransactionSend() {
        return transactionSend;
    }

    public void setTransactionSend(List<Transaction> transactionSend) {
        this.transactionSend = transactionSend;
    }

    public List<Transaction> getTransactionReceive() {
        return transactionReceive;
    }

    public void setTransactionReceive(List<Transaction> transactionReceive) {
        this.transactionReceive = transactionReceive;
    }

    @Override
    public String toString() {
        return "BankAccount [accountNumber=" + accountNumber + ", bankName=" + bankName + ", routineNumber="
                + routineNumber + ", remainingBalanceOwed=" + remainingBalanceOwed + "]";
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
