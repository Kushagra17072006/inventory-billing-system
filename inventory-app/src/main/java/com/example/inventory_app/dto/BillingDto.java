package com.example.inventory_app.dto;

import java.util.List;

public class BillingDto {

    // One line the cashier adds: which product and how many
    public record BillItemRequest(Long productId, int quantity) {}

    // What the page sends: the list of items on the bill
    public record BillRequest(List<BillItemRequest> items) {}

    // One printed line on the invoice
    public record BillLine(String name, double price, int quantity, double lineTotal) {}

    // What the server sends back: the finished invoice
    public record BillResponse(String invoiceNo, String date, List<BillLine> items, double grandTotal) {}
}

