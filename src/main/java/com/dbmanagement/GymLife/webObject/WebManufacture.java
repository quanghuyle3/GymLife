package com.dbmanagement.GymLife.webObject;

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

    // @NotNull(message = "Phone number is required")
    // @Size(min = 1, message = "Phone number is required")
    private String phoneNumber;

    // @NotNull(message = "Email is required")
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

    public WebManufacture() {
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

}
