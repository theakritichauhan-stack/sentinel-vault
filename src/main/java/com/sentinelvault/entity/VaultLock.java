package com.sentinelvault.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VaultLock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;
    private double amount;
    private LocalDateTime lockedAt;
    private LocalDateTime unlockAt;
    private boolean unlocked;

    public Long getId() { return id; }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDateTime getLockedAt() { return lockedAt; }
    public void setLockedAt(LocalDateTime lockedAt) { this.lockedAt = lockedAt; }

    public LocalDateTime getUnlockAt() { return unlockAt; }
    public void setUnlockAt(LocalDateTime unlockAt) { this.unlockAt = unlockAt; }

    public boolean isUnlocked() { return unlocked; }
    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }
}
    