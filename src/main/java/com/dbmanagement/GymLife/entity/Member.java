package com.dbmanagement.GymLife.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    @JoinColumn(name = "membership_type")
    private Membership membershipType; // no need to automatically create membership in db when create member

    @Column(name = "date_join")
    private String dateJoin;

    @Column(name = "date_expiration")
    private String dateExpiration;

    @Column(name = "active")
    private boolean active = true;

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "bank_account_number")
    private BankAccount bankAccountNumber;

    @OneToMany(mappedBy = "memberId", cascade = { CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    private List<AccessLog> accessLogs;

    @OneToMany(mappedBy = "staffId", cascade = CascadeType.ALL)
    private List<WorkSchedule> workSchedules;

    @OneToMany(mappedBy = "trainerId", cascade = { CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    private List<Training> trainingsAsTrainer;

    @OneToMany(mappedBy = "studentId", cascade = { CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    private List<Training> trainingsAsStudent;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH })
    @JoinTable(name = "role_member", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public Member() {
    }

    public Member(String email, String userName, String password, String firstName, String lastName,
            String address, String phoneNumber, String dateOfBirth, String gender,
            String dateJoin) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.dateJoin = dateJoin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Membership getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(Membership membershipType) {
        this.membershipType = membershipType;
    }

    public String getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(String dateJoin) {
        this.dateJoin = dateJoin;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BankAccount getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(BankAccount bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    @Override
    public String toString() {
        return "Member [id=" + id + ", email=" + email + ", userName=" + userName +
                ", password=" + password
                + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" +
                address + ", phoneNumber="
                + phoneNumber + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender +
                ", membership_type="
                + membershipType + ", date_join=" + dateJoin + ", date_expiration=" +
                dateExpiration + ", active="
                + active + ", bankAccountNumber=" + bankAccountNumber + "]";
    }

    public List<AccessLog> getAccessLogs() {
        return accessLogs;
    }

    public void setAccessLogs(List<AccessLog> accessLogs) {
        this.accessLogs = accessLogs;
    }

    public List<WorkSchedule> getWorkSchedules() {
        return workSchedules;
    }

    public void setWorkSchedules(List<WorkSchedule> workSchedules) {
        this.workSchedules = workSchedules;
    }

    public List<Training> getTrainingsAsTrainer() {
        return trainingsAsTrainer;
    }

    public void setTrainingsAsTrainer(List<Training> trainingsAsTrainer) {
        this.trainingsAsTrainer = trainingsAsTrainer;
    }

    public List<Training> getTrainingsAsStudent() {
        return trainingsAsStudent;
    }

    public void setTrainingsAsStudent(List<Training> trainingsAsStudent) {
        this.trainingsAsStudent = trainingsAsStudent;
    }

    public Collection<Role> getRoles() {
        if (roles == null) {
            roles = new ArrayList<Role>();
        }
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        getRoles().add(role);
    }

    public Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }

    public boolean checkContainARole(Role roleCheck) {
        for (Role role : roles) {
            if (roleCheck == role) {
                return true;
            }
        }
        return false;
    }

}
