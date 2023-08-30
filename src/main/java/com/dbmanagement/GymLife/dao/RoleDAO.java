package com.dbmanagement.GymLife.dao;

import java.util.ArrayList;
import java.util.List;

import com.dbmanagement.GymLife.entity.Role;

public interface RoleDAO {

    Role findRoleByName(String theRoleName);

    ArrayList<String> retrieveAllStaffRoleStrings();

    // Role
    void save(Role thisRole);

    Role findRoleById(int id);

    List<Role> retrieveAllRole();

    void update(Role thisRole);

    void deleteRoleById(int id);

    Role retrieveARoleWithItsMembers(int id);

    Role retrieveOwnerRoleWithItMember();
}
