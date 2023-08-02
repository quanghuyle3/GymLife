package com.dbmanagement.GymLife.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.AttributeList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public class AppDAOImpl implements AppDAO {

    private EntityManager entityManager;

    // inject entity manager using constructor injection
    // entity manager will communicate with database source
    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // BANK ACCOUNT

    @Override
    @Transactional
    public void save(BankAccount thisBankAccount) {
        entityManager.persist(thisBankAccount);
    }

    @Override
    public BankAccount findBankAccountByAccountNumber(String theAccountNumber) {
        return entityManager.find(BankAccount.class, theAccountNumber);
    }

    @Override
    public void retrieveTransactionSendByBankAccount(BankAccount bankAccount) {
        TypedQuery<Transaction> query = entityManager
                .createQuery("select t from Transaction t where t.accountSend = :data", Transaction.class);
        query.setParameter("data", bankAccount);

        try {
            bankAccount.setTransactionSend(query.getResultList());
        }
        // empty transaction case
        catch (Exception e) {
            bankAccount.setTransactionSend(new ArrayList<>());
        }
    }

    @Override
    public void retrieveTransactionReceiveByBankAccount(BankAccount bankAccount) {
        TypedQuery<Transaction> query = entityManager
                .createQuery("select t from Transaction t where t.accountReceive = :data", Transaction.class);
        query.setParameter("data", bankAccount);

        try {
            bankAccount.setTransactionReceive(query.getResultList());
        }
        // empty transaction case
        catch (Exception e) {
            bankAccount.setTransactionReceive(new ArrayList<>());
        }
    }

    @Override
    @Transactional
    public void update(BankAccount thisBankAccount) {
        entityManager.merge(thisBankAccount);
    }

    @Override
    @Transactional
    public void deleteBankAccountByAccountNumber(String theAccountNumber) {
        // retrieve the correct bank account
        BankAccount theBankAccount = findBankAccountByAccountNumber(theAccountNumber);

        // remove the bank account
        entityManager.remove(theBankAccount);
    }

    // MANUFACTURE

    @Override
    @Transactional
    public void save(Manufacture thisManufacture) {
        entityManager.persist(thisManufacture);
    }

    @Override
    @Transactional
    public void update(Manufacture thisManufacture) {
        entityManager.merge(thisManufacture);
    }

    @Override
    public Manufacture findManufactureById(int theId) {
        return entityManager.find(Manufacture.class, theId);
    }

    @Override
    public Manufacture findManufactureByBankAccount(BankAccount bankAccount) {
        TypedQuery<Manufacture> query = entityManager
                .createQuery("select m from Manufacture m where m.bankAccount = :data", Manufacture.class);

        query.setParameter("data", bankAccount);

        Manufacture manufacture = null;
        try {
            manufacture = query.getSingleResult();
        } catch (Exception e) {
            // no manufacture
        }

        return manufacture;
    }

    @Override
    @Transactional
    public void deleteManufactureById(int theId) {
        // reetrieve the manufacture
        Manufacture manufacture = findManufactureById(theId);

        // delete the manufacture
        entityManager.remove(manufacture);
    }

    // TRANSACTION

    @Override
    @Transactional
    public void save(Transaction thisTransaction) {
        entityManager.persist(thisTransaction);
    }

    @Override
    public Transaction findTransactionById(int id) {
        return entityManager.find(Transaction.class, id);
    }

    // EQUIPMENT

    @Override
    @Transactional
    public void save(Equipment thisEquipment) {
        entityManager.persist(thisEquipment);
    }

    @Override
    public Equipment findEquipmentBySerials(String serials) {
        return entityManager.find(Equipment.class, serials);
    }

    @Override
    @Transactional
    public void update(Equipment thisEquipment) {
        entityManager.merge(thisEquipment);
    }

    @Override
    @Transactional
    public void deleteEquipmentBySerials(String serials) {
        entityManager.remove(entityManager.find(Equipment.class, serials));
    }

    // MEMBERSHIP

    @Override
    @Transactional
    public void save(Membership thisMembership) {
        entityManager.persist(thisMembership);
    }

    @Override
    public Membership findMembershipById(int id) {
        return entityManager.find(Membership.class, id);
    }

    @Override
    @Transactional
    public void update(Membership thisMembership) {
        entityManager.merge(thisMembership);
    }

    @Override
    @Transactional
    public void deleteMembershipById(int id) {
        entityManager.remove(entityManager.find(Membership.class, id));
    }

    @Override
    public void retrieveMembersByMembership(Membership thisMembership) {
        TypedQuery<Member> query = entityManager.createQuery("select m from Member m where m.membershipType = :data",
                Member.class);

        query.setParameter("data", thisMembership);

        try {
            thisMembership.setMembers(query.getResultList());
        }
        // empty member case
        catch (Exception e) {
            thisMembership.setMembers(new ArrayList<>());
        }
    }

    // MEMBER
    @Override
    @Transactional
    public void save(Member thisMember) {
        entityManager.persist(thisMember);
    }

    @Override
    public Member findMemberById(int id) {
        return entityManager.find(Member.class, id);

    }

    @Override
    @Transactional
    public void update(Member thisMember) {
        entityManager.merge(thisMember);
    }

    @Override
    @Transactional
    public void deleteMemberById(int id) {
        // retrieve the correct member with its roles
        Member member = entityManager.find(Member.class, id);
        entityManager.remove(member);
        // Member member = retrieveAMemberWithItsRoles(id);

        // // Remove the association that Member has on its Role
        // for (Role role : member.getRoles()) {
        // retrieveARoleWithItsMembers(role.getId()).getMembers().remove(member);
        // }
        // // remove all roles
        // // member.setRoles(null);
        // member.getRoles().clear();

        // // update to db
        // entityManager.remove(member);
    }

    @Override
    public Member findMemberByBankAccount(BankAccount bankAccount) {
        TypedQuery<Member> query = entityManager.createQuery("select m from Member m where m.bankAccountNumber = :data",
                Member.class);

        query.setParameter("data", bankAccount);

        Member member = null;
        try {
            member = query.getSingleResult();
        }
        // empty member case
        catch (Exception e) {

        }

        return member;
    }

    // ACCESS LOG
    @Override
    @Transactional
    public void save(AccessLog thisAccessLog) {
        entityManager.persist(thisAccessLog);
    }

    @Override
    public AccessLog findAccessLogById(int id) {
        return entityManager.find(AccessLog.class, id);
    }

    @Override
    @Transactional
    public void update(AccessLog thisAccessLog) {
        entityManager.merge(thisAccessLog);
    }

    @Override
    public void retrieveAccessLogsByMember(Member thisMember) {
        TypedQuery<AccessLog> query = entityManager.createQuery("select a from AccessLog a where a.memberId = :data",
                AccessLog.class);

        query.setParameter("data", thisMember);

        try {
            thisMember.setAccessLogs(query.getResultList());
        }
        // empty accessLog case
        catch (Exception e) {
            thisMember.setAccessLogs(new ArrayList<>());
        }
    }

    // WORK SCHEDULE
    @Override
    @Transactional
    public void save(WorkSchedule thisWorkSchedule) {
        entityManager.persist(thisWorkSchedule);
    }

    @Override
    public WorkSchedule findWorkScheduleById(int id) {
        return entityManager.find(WorkSchedule.class, id);
    }

    @Override
    @Transactional
    public void update(WorkSchedule thisWorkSchedule) {
        entityManager.merge(thisWorkSchedule);
    }

    @Override
    @Transactional
    public void deleteWorkScheduleById(int id) {
        entityManager.remove(entityManager.find(WorkSchedule.class, id));
    }

    @Override
    public List<WorkSchedule> retrieveAllWorkSchedule() {
        TypedQuery<WorkSchedule> query = entityManager.createQuery("select ws from WorkSchedule ws",
                WorkSchedule.class);

        List<WorkSchedule> result = null;
        try {
            result = query.getResultList();
        } catch (Exception e) {
            result = new ArrayList<>();
        }

        return result;
    }

    @Override
    public void retrieveWorkSchedulesByMember(Member thisMember) {
        TypedQuery<WorkSchedule> query = entityManager.createQuery(
                "select ws from WorkSchedule ws where ws.staffId = :data",
                WorkSchedule.class);

        query.setParameter("data", thisMember);

        try {
            thisMember.setWorkSchedules(query.getResultList());
        }
        // empty accessLog case
        catch (Exception e) {
            thisMember.setWorkSchedules(new ArrayList<>());
        }
    }

    // TRAINING
    @Override
    @Transactional
    public void save(Training thisTraining) {
        entityManager.persist(thisTraining);
    }

    @Override
    public Training findTrainingById(int id) {
        return entityManager.find(Training.class, id);
    }

    @Override
    public List<Training> retrieveAllTraining() {
        TypedQuery<Training> query = entityManager.createQuery("select t from Training t",
                Training.class);

        List<Training> result = null;
        try {
            result = query.getResultList();
        } catch (Exception e) {
            result = new ArrayList<>();
        }

        return result;
    }

    @Override
    @Transactional
    public void update(Training thisTraining) {
        entityManager.merge(thisTraining);
    }

    @Override
    @Transactional
    public void deleteTrainingById(int id) {
        entityManager.remove(entityManager.find(Training.class, id));
    }

    @Override
    public void retrieveTrainingAsTrainerByMember(Member thisMember) {
        TypedQuery<Training> query = entityManager.createQuery(
                "select t from Training t where t.trainerId = :data",
                Training.class);

        query.setParameter("data", thisMember);

        try {
            thisMember.setTrainingsAsTrainer(query.getResultList());
        }
        // empty accessLog case
        catch (Exception e) {
            thisMember.setTrainingsAsTrainer(new ArrayList<>());
        }
    }

    @Override
    public void retrieveTrainingAsStudentByMember(Member thisMember) {
        TypedQuery<Training> query = entityManager.createQuery(
                "select t from Training t where t.studentId = :data",
                Training.class);

        query.setParameter("data", thisMember);

        try {
            thisMember.setTrainingsAsStudent(query.getResultList());
        }
        // empty accessLog case
        catch (Exception e) {
            thisMember.setTrainingsAsStudent(new ArrayList<>());
        }
    }

    // ROLE
    @Override
    @Transactional
    public void save(Role thisRole) {
        entityManager.persist(thisRole);
    }

    @Override
    public Role findRoleById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> retrieveAllRole() {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r",
                Role.class);

        List<Role> result = null;

        try {

            result = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = new ArrayList<>();
        }

        return result;
    }

    @Override
    @Transactional
    public void update(Role thisRole) {
        entityManager.merge(thisRole);
    }

    @Override
    @Transactional
    public void deleteRoleById(int id) {
        entityManager.remove(entityManager.find(Role.class, id));
    }

    @Override
    public Role retrieveARoleWithItsMembers(int id) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r "
                + "JOIN FETCH r.members "
                + "where r.id = :data",
                Role.class);

        query.setParameter("data", id);

        Role tempRole;
        try {
            tempRole = query.getSingleResult();
        }
        // empty corresponding members
        catch (Exception e) {
            tempRole = findRoleById(id);
            tempRole.setMembers(new ArrayList<>());
        }

        return tempRole;
    }

    @Override
    public Member retrieveAMemberWithItsRoles(int id) {
        TypedQuery<Member> query = entityManager.createQuery("select m from Member m "
                + "JOIN FETCH m.roles "
                + "where m.id = :data",
                Member.class);

        query.setParameter("data", id);

        Member tempMember;
        try {
            tempMember = query.getSingleResult();
        }
        // empty corresponding members
        catch (Exception e) {
            tempMember = findMemberById(id);
            tempMember.setRoles(new ArrayList<>());
        }

        return tempMember;
    }

    @Override
    @Transactional
    // [Archive] the deleteMemberById can achieve this function
    public void deleteMemberWithItsRoles(int id) {
        // retrieve the correct member with its roles
        Member member = retrieveAMemberWithItsRoles(id);

        // Remove the association that Member has on its Role
        for (Role role : member.getRoles()) {
            retrieveARoleWithItsMembers(role.getId()).getMembers().remove(member);
        }
        // remove all roles
        // member.setRoles(null);
        member.getRoles().clear();

        // update to db
        entityManager.remove(member);

    }

    @Override
    @Transactional
    public void deleteTrainingWorkScheduleAccessLogOfAMember(int id) {

        // delete access log
        Query query = entityManager.createQuery("delete from AccessLog al where al.memberId.id = :data");

        query.setParameter("data", id);

        query.executeUpdate();

        // delete work schedule
        query = entityManager.createQuery("delete from WorkSchedule ws where ws.staffId.id = :data");

        query.setParameter("data", id);

        query.executeUpdate();

        // delete work schedule
        query = entityManager
                .createQuery("delete from Training t where t.trainerId.id = :data or t.studentId.id = :data");

        query.setParameter("data", id);

        query.executeUpdate();
    }
}
