package com.sentinelvault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SentinelVaultApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelVaultApplication.class, args);
    }
}