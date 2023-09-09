package com.dbmanagement.GymLife.webObject;

import java.util.List;

import com.dbmanagement.GymLife.entity.Member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WebTraining {

    @NotNull(message = "Staff is required")
    private int trainerId;

    @NotNull(message = "Staff is required")
    private int studentId;

    @NotNull(message = "Work date is required")
    @Size(min = 1, message = "Work date is required")
    private String dateStart;

    private String dateEnd;

    private List<Member> preTrainerList;

    private List<Member> preStudentList;

    public WebTraining() {
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public List<Member> getPreTrainerList() {
        return preTrainerList;
    }

    public void setPreTrainerList(List<Member> preTrainerList) {
        this.preTrainerList = preTrainerList;
    }

    public List<Member> getPreStudentList() {
        return preStudentList;
    }

    public void setPreStudentList(List<Member> preStudentList) {
        this.preStudentList = preStudentList;
    }

}
