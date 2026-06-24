package com.sentinelvault.controller;

import com.sentinelvault.entity.User;
import com.sentinelvault.service.UserService;
import com.sentinelvault.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    public String homePage() {

        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/register-page")
    public String registerPage() {

        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {

        userService.register(user);

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {

        User user =
                userService.login(username, password);

        if(user != null) {

            session.setAttribute(
                    "loggedInUser",
                    user
            );

            return "redirect:/dashboard";
        }

        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(Model model,
                                HttpSession session) {

        User user =
                (User) session.getAttribute("loggedInUser");

        if(user == null) {

            return "redirect:/login";
        }

        double balance = 0;

        double vaultBalance = 0;

        boolean highRiskDetected = false;

        double totalDeposits = 0;
        double totalWithdrawals = 0;

        int riskPercentage = 10;

        if(user.getVaultUnlockTime() != null &&
           user.getVaultUnlockTime().isBefore(
                   java.time.LocalDateTime.now())) {

            user.setVaultUnlockTime(null);

            user.setEmergencyUnlocked(false);

            userService.register(user);
        }

        Double calculatedBalance =
                transactionService.calculateBalance(user);

        if(calculatedBalance != null) {

            balance = calculatedBalance;
        }

        if(user.getVaultBalance() != null) {

            vaultBalance =
                    user.getVaultBalance();
        }

        for(var transaction :
                transactionService
                        .getUserTransactions(user)) {

            if(transaction.getType()
                    .equals("DEPOSIT")) {

                totalDeposits +=
                        transaction.getAmount();

            } else {

                totalWithdrawals +=
                        transaction.getAmount();
            }

            if("HIGH".equals(
                    transaction.getRiskLevel())) {

                highRiskDetected = true;
            }
        }

        if(highRiskDetected) {

            riskPercentage = 75;
        }

        if(user.getEmergencyUnlocked()) {

            riskPercentage = 95;
        }

        model.addAttribute(
                "balance",
                balance);

        model.addAttribute(
                "vaultBalance",
                vaultBalance);

        model.addAttribute(
                "highRiskDetected",
                highRiskDetected);

        model.addAttribute(
                "riskPercentage",
                riskPercentage);

        model.addAttribute(
                "totalDeposits",
                totalDeposits);

        model.addAttribute(
                "totalWithdrawals",
                totalWithdrawals);

        return "dashboard";
    }

    @GetMapping("/vault-security")
public String vaultSecurityPage(HttpSession session) {

    User user =
            (User) session.getAttribute(
                    "loggedInUser"
            );

    if(user == null) {
        return "redirect:/login";
    }

    return "vault-security";
}

    @GetMapping("/face-auth")
    public String faceAuthPage() {

        return "face-auth";
    }
    @GetMapping("/profile")
public String profilePage(HttpSession session,
                          Model model) {

    User user =
            (User) session.getAttribute(
                    "loggedInUser"
            );

    model.addAttribute("user", user);

    return "profile";
}

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}