package com.dbmanagement.GymLife.webObject;

import java.util.ArrayList;

import com.dbmanagement.GymLife.entity.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class WebMember {

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Username is required")
    @Size(min = 1, message = "Username is required")
    private String userName;

    @NotNull(message = "Password is required")
    @Size(min = 1, message = "Password is required")
    private String password;

    @NotNull(message = "First name is required")
    @Size(min = 1, message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 1, message = "Last name is required")
    private String lastName;

    @NotNull(message = "Address is required")
    @Size(min = 1, message = "Address is required")
    private String address;

    @NotNull(message = "Phone number is required")
    @Size(min = 1, message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Date of birth is required")
    @Size(min = 1, message = "Date of birth is required")
    private String dateOfBirth;

    @NotNull(message = "Gender is required")
    @Size(min = 1, message = "Gender is required")
    private String gender;

    @NotNull(message = "Membership type is required")
    @Size(min = 1, message = "Membership type is required")
    private String membershipType;

    @NotNull(message = "Date join is required")
    @Size(min = 1, message = "Date join is required")
    private String dateJoin;

    @NotNull(message = "Bank Name is required")
    @Size(min = 1, message = "Bank Name is required")
    private String bankName;

    @NotNull(message = "Bank account number is required")
    @Size(min = 1, message = "Bank account number is required")
    private String bankAccountNumber;

    @NotNull(message = "Routine number is required")
    @Pattern(regexp = "\\d{9}", message = "Routine number must have exactly 9 digits")
    private String routineNumber;

    private ArrayList<String> preMembershipTypes;

    private int id;

    private String passwordConfirm;

    public WebMember() {
    }

    public WebMember(Member member, ArrayList<String> preMembershipTypes) {
        this.email = member.getEmail();
        this.userName = member.getUserName();
        this.password = member.getPassword();
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.address = member.getAddress();
        this.phoneNumber = member.getPhoneNumber();
        this.dateOfBirth = member.getDateOfBirth();
        this.gender = member.getGender();
        this.membershipType = member.getMembershipType().getTypeName();
        this.dateJoin = member.getDateJoin();
        this.bankName = member.getBankAccountNumber().getBankName();
        this.bankAccountNumber = member.getBankAccountNumber().getAccountNumber();
        this.routineNumber = Integer.toString(member.getBankAccountNumber().getRoutineNumber());
        this.preMembershipTypes = preMembershipTypes;
        this.id = member.getId();
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

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(String dateJoin) {
        this.dateJoin = dateJoin;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getRoutineNumber() {
        return routineNumber;
    }

    public void setRoutineNumber(String routineNumber) {
        this.routineNumber = routineNumber;
    }

    public ArrayList<String> getPreMembershipTypes() {
        return preMembershipTypes;
    }

    public void setPreMembershipTypes(ArrayList<String> preMembershipTypes) {
        this.preMembershipTypes = preMembershipTypes;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
