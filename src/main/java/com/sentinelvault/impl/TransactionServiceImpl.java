package com.sentinelvault.impl;

import com.sentinelvault.entity.Transaction;
import com.sentinelvault.entity.User;

import com.sentinelvault.repository.TransactionRepository;
import com.sentinelvault.repository.UserRepository;

import com.sentinelvault.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Transaction deposit(Double amount,
                               User user) {

        Transaction transaction = new Transaction();

       transaction.setType("DEPOSIT");
       transaction.setAmount(amount);

       transaction.setCategory("Income");
        transaction.setDescription("Money Deposited");
        if(amount > 50000) {

            transaction.setRiskLevel("HIGH");

        } else {

            transaction.setRiskLevel("LOW");
        }

        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }

    @Override
public Transaction withdraw(Double amount,
                            String category,
                            User user) {

        Transaction transaction = new Transaction();

        transaction.setType("WITHDRAW");
        transaction.setAmount(amount);
        System.out.println("Saving Category = " + category);
        transaction.setCategory(category);
        transaction.setDescription("Money Withdrawn");

        if(amount > 50000) {

            transaction.setRiskLevel("HIGH");

        } else {

            transaction.setRiskLevel("LOW");
        }

        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {

        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getUserTransactions(User user) {

        return transactionRepository.findByUser(user);
    }

    @Override
    public Double calculateBalance(User user) {

        List<Transaction> transactions =
                transactionRepository.findByUser(user);

        double balance = 0;

        for(Transaction transaction : transactions) {

            if(transaction.getType().equals("DEPOSIT")) {

                balance += transaction.getAmount();

            } else {

                balance -= transaction.getAmount();
            }
        }

        return balance;
    }

    @Override
    public void lockMoney(Double amount,
                          User user) {

        Double currentVault =
                user.getVaultBalance();

        if(currentVault == null) {

            currentVault = 0.0;
        }

        user.setVaultBalance(
                currentVault + amount
        );

        userRepository.save(user);
    }
    @Override
public void lockMoneyForHours(Double amount,
                              Integer hours,
                              User user) {

    Double currentVault =
            user.getVaultBalance();

    if(currentVault == null) {

        currentVault = 0.0;
    }

    user.setVaultBalance(
            currentVault + amount
    );

    user.setVaultUnlockTime(
            LocalDateTime.now().plusHours(hours)
    );

    userRepository.save(user);
}

@Override
public void emergencyUnlock(User user) {

    if(user.getVaultUnlockTime() != null &&
       user.getVaultUnlockTime().isAfter(
               LocalDateTime.now())) {

        user.setEmergencyUnlocked(true);

    } else {

        user.setEmergencyUnlocked(false);
    }

    user.setVaultUnlockTime(null);

    userRepository.save(user);
}
}