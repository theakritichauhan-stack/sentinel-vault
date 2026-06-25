package com.sentinelvault.service;

import com.sentinelvault.entity.User;

public interface AIAdvisorService {

    double getCategoryTotal(
            User user,
            String category
    );
    int calculateFinancialScore(User user);
    String generateAdvice(User user);
}