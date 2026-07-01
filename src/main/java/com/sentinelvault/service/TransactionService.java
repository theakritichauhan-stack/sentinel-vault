package com.sentinelvault.service;

import com.sentinelvault.entity.Transaction;
import com.sentinelvault.entity.User;

import java.util.List;

public interface TransactionService {

    Transaction deposit(Double amount,
                        User user);

    Transaction withdraw(Double amount,
                     String category,
                     User user);

    List<Transaction> getAllTransactions();

    List<Transaction> getUserTransactions(User user);

    Double calculateBalance(User user);

    void lockMoney(Double amount,
                   User user);

    void lockMoneyForHours(Double amount,
                           Integer hours,
                           User user);
int getHighRiskTransactionCount(User user);
    void emergencyUnlock(User user);
    
}