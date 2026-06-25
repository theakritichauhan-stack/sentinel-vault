package com.sentinelvault.controller;

import com.sentinelvault.entity.User;
import com.sentinelvault.service.AIAdvisorService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdvisorController {

    @Autowired
    private AIAdvisorService aiAdvisorService;

    @GetMapping("/advisor")
public String advisorPage(Model model,
                          HttpSession session) {

    User user =
            (User) session.getAttribute(
                    "loggedInUser"
            );

    if(user == null) {
        return "redirect:/login";
    }

    model.addAttribute(
            "foodTotal",
            aiAdvisorService.getCategoryTotal(
                    user,
                    "Food"
            )
    );



    model.addAttribute(
            "rentTotal",
            aiAdvisorService.getCategoryTotal(
                    user,
                    "Rent"
            )
    );

    model.addAttribute(
            "shoppingTotal",
            aiAdvisorService.getCategoryTotal(
                    user,
                    "Shopping"
            )
    );

    model.addAttribute(
            "transportTotal",
            aiAdvisorService.getCategoryTotal(
                    user,
                    "Transport"
            )
    );
    model.addAttribute(
        "financialScore",
        aiAdvisorService
                .calculateFinancialScore(user)
);
model.addAttribute(
        "advice",
        aiAdvisorService.generateAdvice(user)
);

    return "advisor";
}
}