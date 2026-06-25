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
    @Override
public int calculateFinancialScore(User user) {

    int score = 100;

    double food =
            getCategoryTotal(user, "Food");

    double shopping =
            getCategoryTotal(user, "Shopping");

    if(food > 5000) {
        score -= 10;
    }

    if(shopping > 5000) {
        score -= 15;
    }

    return Math.max(score, 0);
}
}