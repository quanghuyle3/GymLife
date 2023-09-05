package com.dbmanagement.GymLife.dao;

import java.util.ArrayList;
import java.util.List;

import com.dbmanagement.GymLife.entity.AccessLog;
import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Equipment;
import com.dbmanagement.GymLife.entity.Manufacture;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.entity.Membership;
import com.dbmanagement.GymLife.entity.Role;
import com.dbmanagement.GymLife.entity.Training;
import com.dbmanagement.GymLife.entity.Transaction;
import com.dbmanagement.GymLife.entity.WorkSchedule;

public interface AppDAO {

    // Bank Account
    void save(BankAccount thisBankAccount);

    BankAccount findBankAccountByAccountNumber(String theAccountNumber);

    void retrieveTransactionSendByBankAccount(BankAccount bankAccount);

    void retrieveTransactionReceiveByBankAccount(BankAccount bankAccount);

    void update(BankAccount thisBankAccount);

    void deleteBankAccountByAccountNumber(String theAccountNumber);

    // Manufacture
    void save(Manufacture thisManufacture);

    Manufacture findManufactureById(int theId);

    Manufacture findManufactureByBankAccount(BankAccount bankAccount);

    void update(Manufacture thisManufacture);

    void deleteManufactureById(int theId);

    List<Manufacture> retrieveAllManufacture();

    // Transaction
    void save(Transaction thisTransaction);

    Transaction findTransactionById(int id);

    List<Transaction> retrieveAllTransaction();

    // Equipment
    void save(Equipment thisEquipment);

    Equipment findEquipmentBySerials(String serials);

    List<Equipment> retrieveAllEquipment();

    void update(Equipment thisEquipment);

    void deleteEquipmentBySerials(String serials);

    // Membership
    void save(Membership thisMembership);

    Membership findMembershipById(int id);

    Membership findMembershipByName(String name);

    List<Membership> retrieveAllMembership();

    ArrayList<String> retrieveAllMembershipTypes();

    void update(Membership thisMembership);

    void deleteMembershipById(int id);

    void retrieveMembersByMembership(Membership thisMembership);

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

    // Access Log
    void save(AccessLog thisAccessLog);

    AccessLog findAccessLogById(int id);

    void update(AccessLog thisAccessLog);
    // no delete method

    List<AccessLog> retrieveAllAccessLog();

    // Work Schedule
    void save(WorkSchedule thisWorkSchedule);

    WorkSchedule findWorkScheduleById(int id);

    List<WorkSchedule> retrieveAllWorkSchedule();

    void update(WorkSchedule thisWorkSchedule);

    void deleteWorkScheduleById(int id);

    // Training
    void save(Training thisTraining);

    Training findTrainingById(int id);

    List<Training> retrieveAllTraining();

    void update(Training thisTraining);

    void deleteTrainingById(int id);

    // Role
    void save(Role thisRole);

    Role findRoleById(int id);

    List<Role> retrieveAllRole();

    void update(Role thisRole);

    void deleteRoleById(int id);

    Role retrieveARoleWithItsMembers(int id);

}
