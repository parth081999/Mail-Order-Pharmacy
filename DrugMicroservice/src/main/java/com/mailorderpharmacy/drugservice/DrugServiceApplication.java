package com.mailorderpharmacy.drugservice;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class DrugServiceApplication
{
    public static void main(final String[] args) {
        SpringApplication.run((Class)DrugServiceApplication.class, args);
    }
}