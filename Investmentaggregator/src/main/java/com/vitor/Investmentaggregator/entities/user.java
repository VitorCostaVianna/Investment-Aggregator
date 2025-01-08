package com.vitor.Investmentaggregator.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = user.TABLE_NAME)
public class user {
    
    private static final String TABLE_NAME = "tb_user";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant updatedTimestamp;

    public user() {
    }

    public user(UUID userId, String username,
                String password, String email,
                Instant creationTimestamp, Instant updatedTimestamp) {
        this.setUserId(userId);
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setCreationTimestamp(creationTimestamp);
        this.setUpdatedTimestamp(updatedTimestamp);
    }



    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Instant getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Instant updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    
}
