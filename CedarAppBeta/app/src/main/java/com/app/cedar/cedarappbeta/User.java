package com.app.cedar.cedarappbeta;

/**
 * Created by Saboor Salaam on 11/29/2014.
 */
public class User {
    String user_name, password, email, f_name, l_name, classification, major;
    Number gpa;

    public User(String user_name, String password, String email, String f_name, String l_name, String classification, String major, Number gpa) {
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.f_name = f_name;
        this.l_name = l_name;
        this.classification = classification;
        this.major = major;
        this.gpa = gpa;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Number getGpa() {
        return gpa;
    }

    public void setGpa(Number gpa) {
        this.gpa = gpa;
    }
}