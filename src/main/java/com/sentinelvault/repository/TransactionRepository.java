package com.sentinelvault.repository;

import com.sentinelvault.entity.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sentinelvault.entity.User;

import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {
     List<Transaction> findByUser(User user);
    List<Transaction> findAll();
}