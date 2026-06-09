package com.sentinelvault.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.sentinelvault.entity.Transaction;
import com.sentinelvault.entity.User;

import com.sentinelvault.service.TransactionService;
import com.sentinelvault.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BankController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/transactions")
    public String transactions(Model model,
                               HttpSession session) {

        User user =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        model.addAttribute(
                "transactions",
                transactionService
                        .getUserTransactions(user)
        );

        return "transactions";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam Double amount,
                          HttpSession session) {

        User user =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        transactionService.deposit(
                amount,
                user
        );

        return "redirect:/transactions";
    }

   @PostMapping("/withdraw")
public String withdraw(@RequestParam Double amount,
                       HttpSession session,
                       Model model) {

    User user =
            (User) session.getAttribute(
                    "loggedInUser"
            );

    String otp =
            String.valueOf(
                    (int)(Math.random() * 9000)
                    + 1000
            );

    user.setOtp(otp);

    user.setOtpVerified(false);

    userService.register(user);

    session.setAttribute(
            "withdrawAmount",
            amount
    );

    model.addAttribute(
            "generatedOtp",
            otp
    );

    return "otp-verification";
}
@PostMapping("/verify-otp")
public String verifyOtp(@RequestParam String enteredOtp,
                        HttpSession session,
                        Model model) {

    User user =
            (User) session.getAttribute(
                    "loggedInUser"
            );

    Double amount =
            (Double) session.getAttribute(
                    "withdrawAmount"
            );

    if(user.getOtp().equals(enteredOtp)) {

        transactionService.withdraw(
                amount,
                user
        );

        return "redirect:/transactions";
    }

    model.addAttribute(
            "error",
            "Invalid OTP"
    );

    model.addAttribute(
            "generatedOtp",
            user.getOtp()
    );

    return "otp-verification";
}

    @PostMapping("/lock-money")
    public String lockMoney(@RequestParam Double amount,
                            HttpSession session) {

        User user =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        if(user.getVaultBalance() == null) {

            user.setVaultBalance(0.0);
        }

        user.setVaultBalance(
                user.getVaultBalance() + amount
        );

        userService.register(user);

        return "redirect:/dashboard";
    }

    @PostMapping("/lock-money-hours")
    public String lockMoneyHours(
            @RequestParam Double amount,
            @RequestParam Integer hours,
            HttpSession session) {

        User user =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        if(user.getVaultBalance() == null) {

            user.setVaultBalance(0.0);
        }

        user.setVaultBalance(
                user.getVaultBalance() + amount
        );

        user.setVaultUnlockTime(

                java.time.LocalDateTime.now()
                        .plusHours(hours)
        );

        user.setEmergencyUnlocked(false);

        userService.register(user);

        return "redirect:/vault-security";
    }

    @PostMapping("/emergency-unlock")
    public String emergencyUnlock(
            HttpSession session) {

        User user =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        user.setEmergencyUnlocked(true);

        user.setVaultUnlockTime(null);

        userService.register(user);

        return "redirect:/vault-security";
    }

    @GetMapping("/download-statement")
    public void downloadStatement(
            HttpSession session,
            HttpServletResponse response)
            throws Exception {

        User user =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        response.setContentType(
                "application/pdf"
        );

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=statement.pdf"
        );

        Document document =
                new Document();

        PdfWriter.getInstance(
                document,
                response.getOutputStream()
        );

        document.open();

        document.add(
                new Paragraph(
                        "Sentinel Vault Bank Statement"
                )
        );

        document.add(
                new Paragraph(" ")
        );

        for(Transaction transaction :
                transactionService
                        .getUserTransactions(user)) {

            document.add(

                    new Paragraph(

                            "Type: " +
                            transaction.getType() +

                            " | Amount: ₹" +
                            transaction.getAmount() +

                            " | Risk: " +
                            transaction.getRiskLevel()
                    )
            );
        }

        document.close();
    }
}