package com.dbmanagement.GymLife.webObject;

import java.util.List;

import com.dbmanagement.GymLife.entity.Member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WebAccessLog {

    @NotNull(message = "Date is required")
    @Size(min = 1, message = "Date is required")
    private String date;

    @NotNull(message = "Member is required")
    private int memberId;

    @NotNull(message = "Time access in is required")
    @Size(min = 1, message = "Time access in is required")
    private String timeAccessIn;

    private String timeAccessOut;

    private List<Member> preMemberList;

    // private List<String> preTimeStrings;

    public WebAccessLog() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
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

    public List<Member> getPreMemberList() {
        return preMemberList;
    }

    public void setPreMemberList(List<Member> preMemberList) {
        this.preMemberList = preMemberList;
    }

    // public List<String> getPreTimeStrings() {
    // return preTimeStrings;
    // }

    // public void setPreTimeStrings(List<String> preTimeStrings) {
    // this.preTimeStrings = preTimeStrings;
    // }

}
