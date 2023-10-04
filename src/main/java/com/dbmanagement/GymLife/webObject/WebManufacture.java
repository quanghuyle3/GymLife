package com.dbmanagement.GymLife.webObject;

import com.dbmanagement.GymLife.entity.Manufacture;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class WebManufacture {

    @NotNull(message = "Manufacture name is required")
    @Size(min = 1, message = "Manufacture name is required")
    private String name;

    @NotNull(message = "Address is required")
    @Size(min = 1, message = "Address is required")
    private String address;

    private String phoneNumber;

    // some small private manufacture doesn't email
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Bank Name is required")
    @Size(min = 1, message = "Bank Name is required")
    private String bankName;

    @NotNull(message = "Bank account number is required")
    @Size(min = 1, message = "Bank account number is required")
    private String bankAccountNumber;

    @NotNull(message = "Routine number is required")
    @Pattern(regexp = "\\d{9}", message = "Routine number must have exactly 9 digits")
    private String routineNumber;

    private int id;

    public WebManufacture() {
    }

    public WebManufacture(Manufacture manufacture) {
        this.name = manufacture.getName();
        this.address = manufacture.getAddress();
        if (manufacture.getPhoneNumber() != null) {
            this.phoneNumber = manufacture.getPhoneNumber();
        }
        if (manufacture.getEmail() != null) {
            this.email = manufacture.getEmail();
        }
        this.bankName = manufacture.getBankAccount().getBankName();
        this.bankAccountNumber = manufacture.getBankAccount().getAccountNumber();
        this.routineNumber = String.valueOf(manufacture.getBankAccount().getRoutineNumber());
        this.id = manufacture.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
