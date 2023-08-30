package com.dbmanagement.GymLife.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dbmanagement.GymLife.entity.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private EntityManager entityManager;

    @Autowired
    public RoleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findRoleByName(String theRoleName) {
        // retrieve/read from database using name
        TypedQuery<Role> theQuery = entityManager.createQuery("from Role where name=:data", Role.class);
        theQuery.setParameter("data", theRoleName);

        Role theRole = null;

        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            theRole = null;
        }

        return theRole;
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
    public Role retrieveOwnerRoleWithItMember() {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r "
                + "JOIN FETCH r.members "
                + "where r.name = :data",
                Role.class);

        query.setParameter("data", "ROLE_OWNER");

        Role tempRole;
        try {
            tempRole = query.getSingleResult();
        }
        // empty corresponding members
        catch (Exception e) {
            tempRole = null;
        }

        return tempRole;
    }

    @Override
    public ArrayList<String> retrieveAllStaffRoleStrings() {
        TypedQuery<Role> query = entityManager.createQuery(
                "select r from Role r where r.name NOT IN (:excludedRoles)",
                Role.class);

        query.setParameter("excludedRoles", Arrays.asList("ROLE_OWNER", "ROLE_GYMMER"));

        ArrayList<String> roles = new ArrayList<>();
        try {
            for (Role r : query.getResultList()) {
                roles.add(r.getName());
            }
        }
        // empty member case
        catch (Exception e) {

        }

        return roles;
    }

}
