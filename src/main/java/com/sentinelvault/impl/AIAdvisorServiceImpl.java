package com.sentinelvault.impl;

import com.sentinelvault.entity.Transaction;
import com.sentinelvault.entity.User;
import com.sentinelvault.service.AIAdvisorService;
import com.sentinelvault.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIAdvisorServiceImpl implements AIAdvisorService {

    @Autowired
    private TransactionService transactionService;

    @Override
    public double getCategoryTotal(User user, String category) {

        double total = 0;

        for (Transaction t : transactionService.getUserTransactions(user)) {

            if ("WITHDRAW".equals(t.getType())
                    && category.equalsIgnoreCase(t.getCategory())) {

                total += t.getAmount();
            }
        }

        return total;
    }

    @Override
    public int calculateFinancialScore(User user) {

        int score = 100;

        double food = getCategoryTotal(user, "Food");
        double shopping = getCategoryTotal(user, "Shopping");

        if (food > 5000) {
            score -= 10;
        }

        if (shopping > 5000) {
            score -= 15;
        }

        return Math.max(score, 0);
    }

    @Override
    public String generateAdvice(User user) {

        double food = getCategoryTotal(user, "Food");
        double shopping = getCategoryTotal(user, "Shopping");

        if (shopping > 5000) {
            return "High shopping expenses detected. Reducing shopping by 10% could increase your monthly savings.";
        }

        if (food > 5000) {
            return "Your food spending is high. Consider reducing outside meals to save more money.";
        }

        return "Excellent financial habits. Keep maintaining your spending balance.";
    }

    @Override
    public double getTotalSpending(User user) {

        double total = 0;

        for (Transaction t : transactionService.getUserTransactions(user)) {

            if ("WITHDRAW".equals(t.getType())) {
                total += t.getAmount();
            }
        }

        return total;
    }

    @Override
    public String getTopCategory(User user) {

        double food = getCategoryTotal(user, "Food");
        double rent = getCategoryTotal(user, "Rent");
        double shopping = getCategoryTotal(user, "Shopping");
        double transport = getCategoryTotal(user, "Transport");

        double max = food;
        String category = "Food";

        if (rent > max) {
            max = rent;
            category = "Rent";
        }

        if (shopping > max) {
            max = shopping;
            category = "Shopping";
        }

        if (transport > max) {
            category = "Transport";
        }

        return category;
    }
}