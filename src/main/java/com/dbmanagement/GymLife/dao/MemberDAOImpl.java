package com.dbmanagement.GymLife.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dbmanagement.GymLife.entity.AccessLog;
import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.entity.Role;
import com.dbmanagement.GymLife.entity.Training;
import com.dbmanagement.GymLife.entity.WorkSchedule;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public class MemberDAOImpl implements MemberDAO {

    private EntityManager entityManager;
    private RoleDAO roleDAO;

    @Autowired
    public MemberDAOImpl(EntityManager theEntityManager, RoleDAO roleDAO) {
        entityManager = theEntityManager;
        this.roleDAO = roleDAO;
    }

    @Override
    public Member findByUserName(String theUserName) {
        TypedQuery<Member> query = entityManager.createQuery("from Member where userName = :data", Member.class);
        query.setParameter("data", theUserName);

        Member member = null;
        try {
            member = query.getSingleResult();
        } catch (Exception e) {
            member = null;
        }
        return member;
    }

    @Override
    public List<Member> retrieveAllMembers() {
        TypedQuery<Member> query = entityManager.createQuery("from Member", Member.class);

        List<Member> result = null;

        try {
            result = query.getResultList();
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    @Override
    public List<Member> retrieveAllGymmers() {
        TypedQuery<Member> query = entityManager.createQuery(
                "SELECT m FROM Member m JOIN m.roles r WHERE r.name = :roleName",
                Member.class);
        query.setParameter("roleName", "ROLE_GYMMER");

        List<Member> result = null;

        try {
            result = query.getResultList();
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    @Override
    public List<Member> retrieveAllStaffs() {
        TypedQuery<Member> query = entityManager.createQuery(
                "SELECT m FROM Member m JOIN m.roles r WHERE r.name != :roleName",
                Member.class);
        query.setParameter("roleName", "ROLE_GYMMER");

        List<Member> result = null;

        try {
            result = query.getResultList();
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    @Override
    public List<Member> retrieveAllStaffsWithoutOwner() {
        TypedQuery<Member> query = entityManager.createQuery(
                "SELECT m FROM Member m JOIN m.roles r WHERE r.name NOT IN (:roleList)",
                Member.class);

        query.setParameter("roleList", Arrays.asList("ROLE_GYMMER", "ROLE_OWNER"));

        List<Member> result = null;

        try {
            result = query.getResultList();
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    @Override
    public List<Member> retrieveAllTrainers() {
        TypedQuery<Member> query = entityManager.createQuery(
                "SELECT m FROM Member m JOIN m.roles r WHERE r.name = :roleName",
                Member.class);
        query.setParameter("roleName", "ROLE_TRAINER");

        List<Member> result = null;

        try {
            result = query.getResultList();
        } catch (Exception e) {
            result = null;
        }
        return result;
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
            roleDAO.retrieveARoleWithItsMembers(role.getId()).getMembers().remove(member);
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
