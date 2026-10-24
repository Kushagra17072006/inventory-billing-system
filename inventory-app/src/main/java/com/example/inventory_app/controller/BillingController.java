package com.example.inventory_app.controller;

import com.example.inventory_app.dto.BillingDto;
import com.example.inventory_app.dto.BillingDto.BillRequest;
import com.example.inventory_app.dto.BillingDto.BillResponse;
import com.example.inventory_app.service.BillingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/checkout")
    public BillResponse checkout(@RequestBody BillRequest request) {
        return billingService.checkout(request);
    }
}
