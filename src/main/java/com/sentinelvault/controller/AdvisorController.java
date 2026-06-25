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

        model.addAttribute("foodTotal", 100);
        model.addAttribute("rentTotal", 200);
        model.addAttribute("shoppingTotal", 300);
        model.addAttribute("transportTotal", 400);

        return "advisor";
    }
}