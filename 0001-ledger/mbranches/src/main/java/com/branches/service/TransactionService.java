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

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;

    public TransactionPostResponse save(TransactionPostRequest postRequest) {
        System.out.println(postRequest.getDescription());
        Transaction transactionToSave = mapper.toTransaction(postRequest);
        System.out.println(transactionToSave.getDescription());

        Transaction response = repository.save(transactionToSave);

        return mapper.toTransactionPostResponse(response);
    }

    public Double getBalance() {
        return repository.sumAllTransactions();
    }

    public List<TransactionGetResponse> findAll() {
        List<Transaction> response = repository.findAll(Sort.by("date").descending());

        return mapper.toTransactionGetResponseList(response);
    }

    public List<TransactionGetResponse> getIncomes() {
        List<Transaction> response = repository.findAllByValueGreaterThan(0D);

        return mapper.toTransactionGetResponseList(response);
    }
}
