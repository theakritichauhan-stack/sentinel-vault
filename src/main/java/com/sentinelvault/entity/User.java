package com.sentinelvault.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Column(nullable = false)
    private Double vaultBalance = 0.0;
    private String otp;

    private boolean otpVerified;

    private LocalDateTime vaultUnlockTime;

    private Boolean emergencyUnlocked = false;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getVaultBalance() {
        return vaultBalance;
    }

    public void setVaultBalance(Double vaultBalance) {
        this.vaultBalance = vaultBalance;
    }

    public LocalDateTime getVaultUnlockTime() {
        return vaultUnlockTime;
    }

    public void setVaultUnlockTime(
            LocalDateTime vaultUnlockTime) {

        this.vaultUnlockTime = vaultUnlockTime;
    }

    public Boolean getEmergencyUnlocked() {
        return emergencyUnlocked;
    }

    public void setEmergencyUnlocked(
            Boolean emergencyUnlocked) {

        this.emergencyUnlocked =
                emergencyUnlocked;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(
            List<Transaction> transactions) {

        this.transactions = transactions;
    }
    public String getOtp() {
    return otp;
}

public void setOtp(String otp) {
    this.otp = otp;
}

public boolean getOtpVerified() {
    return otpVerified;
}

public void setOtpVerified(boolean otpVerified) {
    this.otpVerified = otpVerified;
}
}