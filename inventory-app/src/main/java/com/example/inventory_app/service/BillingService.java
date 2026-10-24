package com.example.inventory_app.service;

import com.example.inventory_app.dto.BillingDto.BillItemRequest;
import com.example.inventory_app.dto.BillingDto.BillLine;
import com.example.inventory_app.dto.BillingDto.BillRequest;
import com.example.inventory_app.dto.BillingDto.BillResponse;
import com.example.inventory_app.entity.Product;
import com.example.inventory_app.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillingService {

    private final ProductRepository productRepository;

    public BillingService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // @Transactional: if anything fails, every stock change is undone,
    // so a bill is either fully saved or not saved at all.
    @Transactional
    public BillResponse checkout(BillRequest request) {
        if (request == null || request.items() == null || request.items().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The bill has no items.");
        }

        List<BillLine> lines = new ArrayList<>();
        double total = 0;

        for (BillItemRequest item : request.items()) {
            if (item.quantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be at least 1.");
            }

            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Product not found: " + item.productId()));

            if (product.getQuantity() < item.quantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Not enough stock for " + product.getName() + ": only " + product.getQuantity() + " left.");
            }

            // reduce the stock
            product.setQuantity(product.getQuantity() - item.quantity());
            productRepository.save(product);

            double lineTotal = product.getPrice() * item.quantity();
            lines.add(new BillLine(product.getName(), product.getPrice(), item.quantity(), lineTotal));
            total += lineTotal;
        }

        LocalDateTime now = LocalDateTime.now();
        String invoiceNo = "INV-" + now.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        double grandTotal = Math.round(total * 100.0) / 100.0;
        return new BillResponse(invoiceNo, now.toString(), lines, grandTotal);
    }
}
