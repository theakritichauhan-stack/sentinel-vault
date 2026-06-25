package com.sentinelvault.controller;

import com.sentinelvault.entity.User;
import com.sentinelvault.service.AIAdvisorService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class AdvisorController {

    @Autowired
    private AIAdvisorService aiAdvisorService;

    @GetMapping("/advisor")
@ResponseBody
public String advisorPage(HttpSession session) {

    User user =
            (User) session.getAttribute(
                    "loggedInUser"
            );

    if(user == null) {
        return "USER IS NULL";
    }

    return "ADVISOR CONTROLLER WORKING";
}
}