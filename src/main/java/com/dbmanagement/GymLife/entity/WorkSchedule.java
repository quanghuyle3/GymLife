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
@Table(name = "work_schedule")
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "work_date")
    private String workDate;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    @JoinColumn(name = "staff_id")
    private Member staffId;

    @JoinColumn(name = "time_start")
    private String timeStart;

    @JoinColumn(name = "time_end")
    private String timeEnd;

    public WorkSchedule() {
    }

    public WorkSchedule(String workDate, Member staffId, String timeStart, String timeEnd) {
        this.workDate = workDate;
        this.staffId = staffId;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public Member getStaffId() {
        return staffId;
    }

    public void setStaffId(Member staffId) {
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

    @Override
    public String toString() {
        return "WorkSchedule [id=" + id + ", workDate=" + workDate + ", staffId=" + staffId + ", timeStart=" + timeStart
                + ", timeEnd=" + timeEnd + "]";
    }

}
