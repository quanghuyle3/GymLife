package com.dbmanagement.GymLife.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.dao.RoleDAO;
import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.entity.Membership;
import com.dbmanagement.GymLife.entity.Role;
import com.dbmanagement.GymLife.entity.Transaction;
import com.dbmanagement.GymLife.webObject.WebMember;
import com.dbmanagement.GymLife.webObject.WebStaff;

@Service
public class UserServiceImpl implements UserService {

    private MemberDAO memberDAO;
    private AppDAO appDAO;
    private RoleDAO roleDAO;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(MemberDAO memberDAO, AppDAO appDAO, RoleDAO roleDAO, BCryptPasswordEncoder passwordEncoder) {
        this.memberDAO = memberDAO;
        this.appDAO = appDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Member findByUserName(String userName) {
        // find the member by its username
        // or check if it exists a member with this username
        // return either a member or null
        return memberDAO.findByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberDAO.findByUserName(username);

        if (member == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        // if (!member.isActive()) {
        // throw new DisabledException("This user is not active.");
        // }

        // Collection<SimpleGrantedAuthority> authorities =
        // mapRolesToAuthorities(member.getRoles());

        // return new
        // org.springframework.security.core.userdetails.User(member.getUserName(),
        // member.getPassword(),
        // authorities);
        // return new
        // org.springframework.security.core.userdetails.User(member.getUserName(),
        // member.getPassword(),
        // member.isActive(), true, true, true,
        // authorities);

        // Create a UserDetails object with custome properties
        CustomUserDetails userDetails = new CustomUserDetails(member);
        return userDetails;
    }

    @Override
    public Member save(WebMember webMember, String roleName) {
        Member member = new Member();
        BankAccount bankAccount = new BankAccount();

        // assign bank account details to bank account object
        int routineNumber = 0;
        try {
            routineNumber = Integer.parseInt(webMember.getRoutineNumber());
        } catch (Exception e) {
            System.out.println("Error from routine number!");
        }
        bankAccount.setBankName(webMember.getBankName());
        bankAccount.setAccountNumber(webMember.getBankAccountNumber());
        bankAccount.setRoutineNumber(routineNumber);

        // assign member details to the user object
        member.setEmail(webMember.getEmail());
        member.setUserName(webMember.getUserName());
        member.setPassword(passwordEncoder.encode(webMember.getPassword()));
        member.setFirstName(webMember.getFirstName());
        member.setLastName(webMember.getLastName());
        member.setAddress(webMember.getAddress());
        member.setPhoneNumber(webMember.getPhoneNumber());
        member.setDateOfBirth(webMember.getDateOfBirth());
        member.setGender(webMember.getGender());
        Membership membership = null;
        if (webMember.getMembershipType() != null) {
            membership = appDAO.findMembershipByName(webMember.getMembershipType());
            member.setMembershipType(membership);
        }
        member.setDateJoin(webMember.getDateJoin());
        member.setBankAccountNumber(bankAccount);

        // set role for it
        Role role = roleDAO.findRoleByName(roleName);
        member.addRole(role);

        // save new member
        memberDAO.save(member);

        // if it's a gymmer, process a TRANSACTION/charge
        // from its bank to owner's bank
        if (membership != null) {
            BankAccount accountSend = bankAccount;

            Role ownerRole = roleDAO.retrieveOwnerRoleWithItMember();
            BankAccount accountReceive = ownerRole.getMembers().get(0).getBankAccountNumber();

            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = today.format(formatter);

            Transaction transaction = new Transaction(accountSend, accountReceive, membership.getPrice(),
                    formattedDate);
            appDAO.save(transaction);
        }

        return member;

    }

    @Override
    public Member save(WebStaff webStaff, String roleName) {
        Member member = new Member();
        BankAccount bankAccount = new BankAccount();

        // assign bank account details to bank account object
        int routineNumber = 0;
        try {
            routineNumber = Integer.parseInt(webStaff.getRoutineNumber());
        } catch (Exception e) {
            System.out.println("Error from routine number!");
        }
        bankAccount.setBankName(webStaff.getBankName());
        bankAccount.setAccountNumber(webStaff.getBankAccountNumber());
        bankAccount.setRoutineNumber(routineNumber);

        // assign member details to the user object
        member.setEmail(webStaff.getEmail());
        member.setUserName(webStaff.getUserName());
        member.setPassword(passwordEncoder.encode(webStaff.getPassword()));
        member.setFirstName(webStaff.getFirstName());
        member.setLastName(webStaff.getLastName());
        member.setAddress(webStaff.getAddress());
        member.setPhoneNumber(webStaff.getPhoneNumber());
        member.setDateOfBirth(webStaff.getDateOfBirth());
        member.setGender(webStaff.getGender());
        member.setDateJoin(webStaff.getDateJoin());
        member.setBankAccountNumber(bankAccount);

        // set role for it
        Role role = roleDAO.findRoleByName(roleName);
        member.addRole(role);

        // save new staff
        memberDAO.save(member);

        return member;
    }

    @Override
    public void update(WebMember webMember) {
        Member member = memberDAO.findMemberById(webMember.getId());
        BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(webMember.getBankAccountNumber());
        if (bankAccount == null) {
            bankAccount = new BankAccount();
        }

        // assign bank account details to bank account object
        int routineNumber = 0;
        try {
            routineNumber = Integer.parseInt(webMember.getRoutineNumber());
        } catch (Exception e) {
            System.out.println("Error from routine number!");
        }
        bankAccount.setBankName(webMember.getBankName());
        bankAccount.setAccountNumber(webMember.getBankAccountNumber());
        bankAccount.setRoutineNumber(routineNumber);

        // assign member details to the user object
        member.setEmail(webMember.getEmail());
        member.setUserName(webMember.getUserName());
        // don't update password again.. because it will bcrypt encode it one more time
        // which leads to unpredicted passwords
        // member.setPassword(passwordEncoder.encode(webMember.getPassword()));
        member.setFirstName(webMember.getFirstName());
        member.setLastName(webMember.getLastName());
        member.setAddress(webMember.getAddress());
        member.setPhoneNumber(webMember.getPhoneNumber());
        member.setDateOfBirth(webMember.getDateOfBirth());
        member.setGender(webMember.getGender());
        Membership membership = null;
        if (webMember.getMembershipType() != null) {
            membership = appDAO.findMembershipByName(webMember.getMembershipType());
            member.setMembershipType(membership);
        }
        member.setDateJoin(webMember.getDateJoin());
        member.setBankAccountNumber(bankAccount);

        // member already has role(s). No need to set for it
        // // set role for it
        // Role role = roleDAO.findRoleByName(roleName);
        // member.addRole(role);

        // save new member
        memberDAO.update(member);
    }

    @Override
    public void update(WebStaff webStaff) {
        Member member = memberDAO.findMemberById(webStaff.getId());
        BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(webStaff.getBankAccountNumber());
        if (bankAccount == null) {
            bankAccount = new BankAccount();
        }

        // assign bank account details to bank account object
        int routineNumber = 0;
        try {
            routineNumber = Integer.parseInt(webStaff.getRoutineNumber());
        } catch (Exception e) {
            System.out.println("Error from routine number!");
        }
        bankAccount.setBankName(webStaff.getBankName());
        bankAccount.setAccountNumber(webStaff.getBankAccountNumber());
        bankAccount.setRoutineNumber(routineNumber);

        // assign member details to the user object
        member.setEmail(webStaff.getEmail());
        member.setUserName(webStaff.getUserName());
        // don't update password again.. because it will bcrypt encode it one more time
        // which leads to unpredicted passwords
        // member.setPassword(passwordEncoder.encode(webMember.getPassword()));
        member.setFirstName(webStaff.getFirstName());
        member.setLastName(webStaff.getLastName());
        member.setAddress(webStaff.getAddress());
        member.setPhoneNumber(webStaff.getPhoneNumber());
        member.setDateOfBirth(webStaff.getDateOfBirth());
        member.setGender(webStaff.getGender());
        Membership membership = null;
        member.setDateJoin(webStaff.getDateJoin());
        member.setBankAccountNumber(bankAccount);

        // member already has role(s). No need to set for it
        // // set role for it
        // Role role = roleDAO.findRoleByName(roleName);
        // member.addRole(role);

        // save new member
        memberDAO.update(member);
    }

    @Override
    public void delete(int id) {
        Member member = appDAO.findMemberById(id);

        // Member member = appDAO.retrieveAMemberWithItsRoles(id); // no need to, since
        // roles are fetched eager

        member.getBankAccountNumber().setMember(null);

        // Remove the all association that Member has on its Role
        for (Role role : member.getRoles()) {
            roleDAO.retrieveARoleWithItsMembers(role.getId()).getMembers().remove(member);
        }

        // remove all roles from this member
        member.setRoles(null);

        // (MUST DO!) remove all access log, training (if any), and work schedule (if
        // any)
        // associate to this member
        // to avoid constraint in SQL table
        memberDAO.deleteTrainingWorkScheduleAccessLogOfAMember(id);

        memberDAO.deleteMemberById(id);
    }

}
