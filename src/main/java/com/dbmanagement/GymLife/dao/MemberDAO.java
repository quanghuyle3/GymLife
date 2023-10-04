package com.dbmanagement.GymLife.dao;

import java.util.List;

import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Member;

public interface MemberDAO {

    Member findByUserName(String theUserName);

    List<Member> retrieveAllMembers();

    List<Member> retrieveAllGymmers();

    List<Member> retrieveAllGymmersDESC();

    List<Member> retrieveAllStaffs();

    List<Member> retrieveAllStaffsWithoutOwner();

    Member retrieveOwner();

    List<Member> retrieveAllTrainers();

    // Member
    void save(Member thisMember);

    Member findMemberById(int id);

    void update(Member thisMember);

    void deleteMemberById(int id);

    Member findMemberByBankAccount(BankAccount bankAccount);

    void retrieveAccessLogsByMember(Member thisMember);

    void retrieveWorkSchedulesByMember(Member thisMember);

    void retrieveTrainingAsTrainerByMember(Member thisMember);

    void retrieveTrainingAsStudentByMember(Member thisMember);

    Member retrieveAMemberWithItsRoles(int id);

    void deleteMemberWithItsRoles(int id);

    void deleteTrainingWorkScheduleAccessLogOfAMember(int id);

}
