package com.example.bank.dto;

public class UserSignupDTO {

    private String id;
    private String password;
    private String passwordConfirm;
    private String fullName;
    private String email;
    private String phoneNumber;
    private int age;

    public UserSignupDTO() {}

    public UserSignupDTO(
        String id,
        String password,
        String passwordConfirm,
        String fullName,
        String email,
        String phoneNumber,
        int age
    ) {
        this.id = id;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
