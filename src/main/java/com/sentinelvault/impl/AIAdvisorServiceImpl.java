package com.sentinelvault.impl;

import com.sentinelvault.entity.Transaction;
import com.sentinelvault.entity.User;
import com.sentinelvault.service.AIAdvisorService;
import com.sentinelvault.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIAdvisorServiceImpl
        implements AIAdvisorService {

    @Autowired
    private TransactionService transactionService;

    @Override
    public double getCategoryTotal(
    User user,
            String category) {
                System.out.println("Advisor running for user: " + user.getUsername());

        double total = 0;

        for (Transaction t :
                transactionService
                        .getUserTransactions(user)) {

            if ("WITHDRAW".equals(t.getType())
                    &&
               
                    category.equalsIgnoreCase(
                       t.getCategory())) {

                total += t.getAmount();
            }
        }

        return total;
    }
}