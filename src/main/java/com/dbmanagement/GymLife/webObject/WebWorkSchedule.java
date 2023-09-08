package com.dbmanagement.GymLife.webObject;

import java.util.List;

import com.dbmanagement.GymLife.entity.Member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WebWorkSchedule {

    @NotNull(message = "Work date is required")
    @Size(min = 1, message = "Work date is required")
    private String workDate;

    @NotNull(message = "Staff is required")
    // @Size(min = 1, message = "Staff is required")
    private int staffId;

    @NotNull(message = "Time start is required")
    @Size(min = 1, message = "Time start is required")
    private String timeStart;

    @NotNull(message = "Time end is required")
    @Size(min = 1, message = "Time end is required")
    private String timeEnd;

    private List<Member> preStaffList;

    private List<String> preTimeStrings;

    public WebWorkSchedule() {
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public List<Member> getPreStaffList() {
        return preStaffList;
    }

    public void setPreStaffList(List<Member> preStaffList) {
        this.preStaffList = preStaffList;
    }

    public List<String> getPreTimeStrings() {
        return preTimeStrings;
    }

    public void setPreTimeStrings(List<String> preTimeStrings) {
        this.preTimeStrings = preTimeStrings;
    }

}
