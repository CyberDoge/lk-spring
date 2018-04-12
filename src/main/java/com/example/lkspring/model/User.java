package com.example.lkspring.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    private Long id;

    private boolean enable;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private Double middleAttempt;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "email", nullable = false, unique = true)
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

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Double getMiddleAttempt() {
        return middleAttempt;
    }

    public void setMiddleAttempt(Double middleAttempt) {
        this.middleAttempt = middleAttempt;
    }

}
