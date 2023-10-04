package com.dbmanagement.GymLife.webObject;

import java.util.List;

import com.dbmanagement.GymLife.entity.Equipment;
import com.dbmanagement.GymLife.entity.Transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WebEquipment {

    @NotNull(message = "Serials is required")
    @Size(min = 1, message = "Serials is required")
    private String serials;

    @NotNull(message = "Name is required")
    @Size(min = 1, message = "Name is required")
    private String name;

    @NotNull(message = "Transaction ID is required")
    @Min(value = 1, message = "Transaction ID must be chose")
    private int transactionId;

    private String dateImported;

    private String target;

    private List<Transaction> preTransactionList;

    private List<String> preTargetList;

    public WebEquipment() {
    }

    public WebEquipment(Equipment e, List<Transaction> preTransactionList, List<String> preTargetList) {
        this.serials = e.getSerials();
        this.name = e.getName();
        this.transactionId = e.getTransactionId().getId();
        this.dateImported = e.getDateImported();
        this.target = e.getTarget();
        this.preTransactionList = preTransactionList;
        this.preTargetList = preTargetList;
    }

    public String getSerials() {
        return serials;
    }

    public void setSerials(String serials) {
        this.serials = serials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getDateImported() {
        return dateImported;
    }

    public void setDateImported(String dateImported) {
        this.dateImported = dateImported;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<Transaction> getPreTransactionList() {
        return preTransactionList;
    }

    public void setPreTransactionList(List<Transaction> preTransactionList) {
        this.preTransactionList = preTransactionList;
    }

    public List<String> getPreTargetList() {
        return preTargetList;
    }

    public void setPreTargetList(List<String> preTargetList) {
        this.preTargetList = preTargetList;
    }

}
