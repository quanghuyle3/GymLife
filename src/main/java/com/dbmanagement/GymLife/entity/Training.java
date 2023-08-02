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
@Table(name = "training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    @JoinColumn(name = "trainer_id")
    private Member trainerId;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    @JoinColumn(name = "student_id")
    private Member studentId;

    @Column(name = "date_start")
    private String dateStart;

    @Column(name = "date_end")
    private String dateEnd;

    public Training() {
    }

    public Training(Member trainerId, Member studentId, String dateStart) {
        this.trainerId = trainerId;
        this.studentId = studentId;
        this.dateStart = dateStart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Member trainerId) {
        this.trainerId = trainerId;
    }

    public Member getStudentId() {
        return studentId;
    }

    public void setStudentId(Member studentId) {
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

    @Override
    public String toString() {
        return "Training [id=" + id + ", trainerId=" + trainerId + ", studentId=" + studentId + ", dateStart="
                + dateStart + ", dateEnd=" + dateEnd + "]";
    }

}
