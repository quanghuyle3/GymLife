package com.dbmanagement.GymLife.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "access_log")
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private String date;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(name = "time_access_in")
    private String timeAccessIn;

    @Column(name = "time_access_out")
    private String timeAccessOut;

    public AccessLog() {
    }

    public AccessLog(String date, Member memberId, String timeAccessIn) {
        this.date = date;
        this.memberId = memberId;
        this.timeAccessIn = timeAccessIn;
    }

    public AccessLog(String date, Member memberId, String timeAccessIn, String timeAccessOut) {
        this.date = date;
        this.memberId = memberId;
        this.timeAccessIn = timeAccessIn;
        this.timeAccessOut = timeAccessOut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Member getMemberId() {
        return memberId;
    }

    public void setMemberId(Member memberId) {
        this.memberId = memberId;
    }

    public String getTimeAccessIn() {
        return timeAccessIn;
    }

    public void setTimeAccessIn(String timeAccessIn) {
        this.timeAccessIn = timeAccessIn;
    }

    public String getTimeAccessOut() {
        return timeAccessOut;
    }

    public void setTimeAccessOut(String timeAccessOut) {
        this.timeAccessOut = timeAccessOut;
    }

    @Override
    public String toString() {
        return "AccessLog [id=" + id + ", date=" + date + ", memberId=" + memberId + ", timeAccessIn=" + timeAccessIn
                + ", timeAccessOut=" + timeAccessOut + "]";
    }

}
