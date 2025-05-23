package com.branches.service;

import com.branches.mapper.TransactionMapper;
import com.branches.model.Transaction;
import com.branches.repository.TransactionRepository;
import com.branches.request.TransactionPostRequest;
import com.branches.response.TransactionGetResponse;
import com.branches.response.TransactionPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;

    public TransactionPostResponse save(TransactionPostRequest postRequest) {
        Transaction transactionToSave = mapper.toTransaction(postRequest);

        Transaction response = repository.save(transactionToSave);

        return mapper.toTransactionPostResponse(response);
    }

    public Double getBalance() {
        return repository.sumAllTransactions();
    }

    public List<TransactionGetResponse> findAll(LocalDateTime startDate, LocalDateTime endDate) {
        Sort sort = Sort.by("date").descending();
        List<Transaction> response = repository.findAllByDateFilter(startDate, endDate, sort);

        return mapper.toTransactionGetResponseList(response);
    }

    public List<TransactionGetResponse> getIncomes() {
        List<Transaction> response = repository.findAllByValueGreaterThan(0D);

        return mapper.toTransactionGetResponseList(response);
    }

    public List<TransactionGetResponse> getExpenses() {
        List<Transaction> response = repository.findAllByValueIsLessThan(0D);

        return mapper.toTransactionGetResponseList(response);
    }
}
