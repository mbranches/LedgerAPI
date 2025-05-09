package com.branches.controller;

import com.branches.exception.InternalServerErrorException;
import com.branches.request.TransactionPostRequest;
import com.branches.response.TransactionGetResponse;
import com.branches.response.TransactionPostResponse;
import com.branches.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;
    private final ObjectMapper objectMapper;


    @PostMapping
    public ResponseEntity<TransactionPostResponse> save(@RequestBody TransactionPostRequest postRequest) {
        TransactionPostResponse response = service.save(postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionGetResponse>> findAll(@RequestParam(required = false) LocalDateTime startDate,
                                                                @RequestParam(required = false) LocalDateTime endDate) {
        List<TransactionGetResponse> response = service.findAll(startDate, endDate);

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

    @GetMapping("/export/json")
    public ResponseEntity<Resource> exportJson() {
        List<TransactionGetResponse> allTransactions = service.findAll(null, null);

        try {
            String csvData = objectMapper.writeValueAsString(allTransactions);
            ByteArrayResource resource = new ByteArrayResource(csvData.getBytes());

            return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.json")
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(resource);
        } catch (Exception e) {
            throw new InternalServerErrorException("Unable to export transactions");
        }
    }

    @GetMapping("/export/csv")
    public ResponseEntity<Resource> exportCsv() {
        List<TransactionGetResponse> allTransactions = service.findAll(null, null);

        try {
            StringBuilder csvBuilder = new StringBuilder();
            csvBuilder.append("id,value,description,date\n");

            for (TransactionGetResponse t : allTransactions) {
                csvBuilder.append(t.getId()).append(",");
                csvBuilder.append(t.getValue()).append(",");
                csvBuilder.append("\"").append(t.getDescription()).append("\",");
                csvBuilder.append(t.getDate()).append("\n");
            }
            String csvData = csvBuilder.toString();
            ByteArrayResource resource = new ByteArrayResource(csvData.getBytes());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.csv")
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(resource);
        } catch (Exception e) {
            throw new InternalServerErrorException("Unable to export transactions");
        }
    }
}
