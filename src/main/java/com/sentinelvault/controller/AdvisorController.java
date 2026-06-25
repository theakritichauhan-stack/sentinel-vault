package com.sentinelvault.controller;

import com.sentinelvault.service.AIAdvisorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdvisorController {

    @Autowired
    private AIAdvisorService aiAdvisorService;

    @GetMapping("/advisor")
    @ResponseBody
    public String advisorPage() {

        return "Advisor Controller Working";
    }
}