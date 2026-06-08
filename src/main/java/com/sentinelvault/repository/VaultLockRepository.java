package com.sentinelvault.repository;

import com.sentinelvault.entity.VaultLock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaultLockRepository extends JpaRepository<VaultLock, Long> {
    List<VaultLock> findByUnlockedFalse();
}