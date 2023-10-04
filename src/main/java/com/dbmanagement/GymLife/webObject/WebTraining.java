package com.dbmanagement.GymLife.webObject;

import java.util.List;

import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.entity.Training;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WebTraining {

    @NotNull(message = "Trainer is required")
    private int trainerId;

    @NotNull(message = "Student is required")
    private int studentId;

    @NotNull(message = "Start date is required")
    @Size(min = 1, message = "Start date is required")
    private String dateStart;

    private String dateEnd;

    private List<Member> preTrainerList;

    private List<Member> preStudentList;

    private int id;

    public WebTraining() {
    }

    public WebTraining(Training training, List<Member> preTrainerList, List<Member> preStudentList) {
        this.trainerId = training.getTrainerId().getId();
        this.studentId = training.getStudentId().getId();
        this.dateStart = training.getDateStart();
        if (training.getDateEnd() != null) {
            this.dateEnd = training.getDateEnd();
        }
        this.preTrainerList = preTrainerList;
        this.preStudentList = preStudentList;
        this.id = training.getId();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
