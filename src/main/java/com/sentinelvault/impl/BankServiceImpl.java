package com.sentinelvault.impl;

import com.sentinelvault.service.BankService;

import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

    @Override
    public void processVaultUnlocks() {

        System.out.println("Vault unlock system active");
    }
}