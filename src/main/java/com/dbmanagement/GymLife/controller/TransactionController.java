package com.dbmanagement.GymLife.controller;

import java.util.ArrayList;
import java.util.List;

import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Transaction;
import com.dbmanagement.GymLife.webObject.WebTraining;
import com.dbmanagement.GymLife.webObject.WebTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    public AppDAO appDAO;
    public MemberDAO memberDAO;
    public RoleDAO roleDAO;

    @Autowired
    public TransactionController(AppDAO appDAO, MemberDAO memberDAO, RoleDAO roleDAO) {
        this.appDAO = appDAO;
        this.memberDAO = memberDAO;
        this.roleDAO = roleDAO;
    }

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {

        // remove whitespace - leading and trailing
        // true: trim to null if only whitespace
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        // pass in the customized trimmer editor object to trim all string all string
        // from web
        // request
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/retrieve")
    public String retrieveAllTransactions(Model theModel) {

        List<Transaction> allTransactions = appDAO.retrieveAllTransaction();

        theModel.addAttribute("transactions", allTransactions);

        return "retrieve/transactions-retrieve";
    }

    @GetMapping("add-transaction")
    public String showTransactionForm(Model theModel) {
        WebTransaction webTransaction = new WebTransaction();
        webTransaction.setPreBankAccountList(appDAO.retrieveAllBankAccount());

        theModel.addAttribute("webTransaction", webTransaction);

        return "add/transaction-form";
    }

    @PostMapping("transaction-process")
    public String processTransaction(@Valid @ModelAttribute("webTransaction") WebTransaction theWebTransaction,
            BindingResult theBindingResult, Model theModel) {

        // form validation
        if (theBindingResult.hasErrors()) {
            theWebTransaction.setPreBankAccountList(appDAO.retrieveAllBankAccount());
            return "add/transaction-form";
        }

        // if 2 accounts are the same
        if (theWebTransaction.getAccountReceive().equals(theWebTransaction.getAccountSend())) {
            theModel.addAttribute("errorMessage", "Error: You chose the same bank account. Plase try again.");
            theWebTransaction.setPreBankAccountList(appDAO.retrieveAllBankAccount());
            return "add/transaction-form";
        }

        Transaction transaction = new Transaction();

        try {
            transaction.setDate(theWebTransaction.getDate());
            transaction.setAccountSend(appDAO.findBankAccountByAccountNumber(theWebTransaction.getAccountSend()));
            transaction.setAccountReceive(appDAO.findBankAccountByAccountNumber(theWebTransaction.getAccountReceive()));
            transaction.setAmount(Double.parseDouble(theWebTransaction.getAmount()));

        } catch (NumberFormatException e) {
            theModel.addAttribute("formatError", "Error: Incorrect amount format.");
            WebTransaction webTransaction = new WebTransaction();
            theWebTransaction.setPreBankAccountList(appDAO.retrieveAllBankAccount());
            return "add/transaction-form";
        }

        // save this new transaction
        appDAO.save(transaction);

        // add message
        theModel.addAttribute("successMessage", "Successfully proceed a transaction - ID: " + transaction.getId());

        // retrieve all transactions before returning to the retrieve page
        List<Transaction> allTransactions = appDAO.retrieveAllTransaction();
        theModel.addAttribute("transactions", allTransactions);

        return "retrieve/transactions-retrieve";
    }

}
