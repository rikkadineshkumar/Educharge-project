package com.educharge.educharge.Models;

/**
 * Created by R Dinesh Kumar on 20-9-17.
 */

import java.util.Date;

public class Profile {


    private int uid;

    private String fname;
    private String lname;
    private String gender;
    private Date dob;
    private String phone;
    private String email;
    private String password;
    private String role;

    public Profile() {
        super();
    }

    public Profile(int uid, String fname, String lname, String gender, Date dob, String phone, String email,
                   String password, String role) {
        super();
        this.uid = uid;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Profile [uid=" + uid + ", fname=" + fname + ", lname=" + lname + ", gender=" + gender + ", dob=" + dob
                + ", phone=" + phone + ", email=" + email + ", password=" + password + ", role=" + role + "]";
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
