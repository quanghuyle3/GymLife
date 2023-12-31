package com.dbmanagement.GymLife.webObject;

import java.util.List;

import com.dbmanagement.GymLife.entity.BankAccount;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WebTransaction {

    @NotNull(message = "Account send is required")
    @Size(min = 1, message = "Account send is required")
    private String accountSend;

    @NotNull(message = "Account receive is required")
    @Size(min = 1, message = "Account receive is required")
    private String accountReceive;

    @NotNull(message = "Transaction amount is required")
    @Size(min = 1, message = "Transaction amount is required")
    private String amount;

    @NotNull(message = "Transaction date is required")
    @Size(min = 1, message = "Transaction date is required")
    private String date;

    private List<BankAccount> preBankAccountList;

    public WebTransaction() {
    }

    public String getAccountSend() {
        return accountSend;
    }

    public void setAccountSend(String accountSend) {
        this.accountSend = accountSend;
    }

    public String getAccountReceive() {
        return accountReceive;
    }

    public void setAccountReceive(String accountReceive) {
        this.accountReceive = accountReceive;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<BankAccount> getPreBankAccountList() {
        return preBankAccountList;
    }

    public void setPreBankAccountList(List<BankAccount> preBankAccountList) {
        this.preBankAccountList = preBankAccountList;
    }

}
