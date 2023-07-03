package com.dbmanagement.GymLife.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_send")
    private BankAccount accountSend;

    @ManyToOne
    @JoinColumn(name = "account_receive")
    private BankAccount accountReceive;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date")
    private String date;

    public Transaction() {
    }

    public Transaction(BankAccount accountSend, BankAccount accountReceive, double amount, String date) {
        this.accountSend = accountSend;
        this.accountReceive = accountReceive;
        this.amount = amount;
        this.date = date;
    }

    public BankAccount getAccountSend() {
        return accountSend;
    }

    public void setAccountSend(BankAccount accountSend) {
        this.accountSend = accountSend;
    }

    public BankAccount getAccountReceive() {
        return accountReceive;
    }

    public void setAccountReceive(BankAccount accountReceive) {
        this.accountReceive = accountReceive;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", accountSend=" + accountSend + ", accountReceive=" + accountReceive
                + ", amount=" + amount + ", date=" + date + "]";
    }

}
