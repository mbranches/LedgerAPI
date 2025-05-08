package com.branches.controller;

import com.branches.request.TransactionPostRequest;
import com.branches.response.TransactionGetResponse;
import com.branches.response.TransactionPostResponse;
import com.branches.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionPostResponse> save(@RequestBody TransactionPostRequest postRequest) {
        TransactionPostResponse response = service.save(postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionGetResponse>> findAll() {
        List<TransactionGetResponse> response = service.findAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/incomes")
    public ResponseEntity<List<TransactionGetResponse>> getIncomes() {
        List<TransactionGetResponse> response = service.getIncomes();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<TransactionGetResponse>> getExpenses() {
        List<TransactionGetResponse> response = service.getExpenses();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance")
    public ResponseEntity<Map<String, Double>> getBalance() {
        Double balance = service.getBalance();

        return ResponseEntity.ok(Map.of("balance", balance));
    }
}
