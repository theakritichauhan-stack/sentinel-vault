package com.sentinelvault.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;
    private String type;
    private double amount;
    private String riskLevel;
    private String category;
    private String description;
   
    @ManyToOne
    private User user;

    public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}
    private String status;
    private LocalDateTime timestamp;

    public Long getId() { return id; }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getRiskLevel() {
    return riskLevel;
}

public void setRiskLevel(String riskLevel) {
    this.riskLevel = riskLevel;
}
public String getCategory() {
    return category;
}

public void setCategory(String category) {
    this.category = category;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
