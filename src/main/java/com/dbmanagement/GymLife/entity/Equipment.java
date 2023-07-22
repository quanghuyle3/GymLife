package com.dbmanagement.GymLife.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @Column(name = "serials")
    private String serials;

    @Column(name = "name")
    private String name;

    @Column(name = "target")
    private String target;

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "transaction_id")
    private Transaction transactionId;

    @Column(name = "date_imported")
    private String dateImported;

    public Equipment() {
    }

    public Equipment(String serials, String name, Transaction transactionId) {
        this.serials = serials;
        this.name = name;
        this.transactionId = transactionId;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Transaction getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Transaction transactionId) {
        this.transactionId = transactionId;
    }

    public String getDateImported() {
        return dateImported;
    }

    public void setDateImported(String dateImported) {
        this.dateImported = dateImported;
    }

    @Override
    public String toString() {
        return "Equipment [serials=" + serials + ", name=" + name + ", target=" + target + ", transactionId="
                + transactionId + ", dateImported=" + dateImported + "]";
    }

    // map field to column

    // constructors

    // getter, setter

    // toString

}
