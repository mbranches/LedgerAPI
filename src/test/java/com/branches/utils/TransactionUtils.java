package com.branches.utils;

import com.branches.model.Transaction;
import com.branches.request.TransactionPostRequest;
import com.branches.response.TransactionGetResponse;
import com.branches.response.TransactionPostResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionUtils {
    public static List<Transaction> newTransactionList() {
        LocalDateTime dateTime1 = LocalDateTime.of(2025, 1, 29, 15, 43, 30);
        Transaction transaction1 = Transaction.builder().id(1L).value(5000D).description("Salário").date(dateTime1).build();

        LocalDateTime dateTime2 = LocalDateTime.of(2025, 1, 28, 15, 43, 30);
        Transaction transaction2 = Transaction.builder().id(2L).value(-1000D).description("Aluguel").date(dateTime2).build();

        LocalDateTime dateTime3 = LocalDateTime.of(2025, 1, 28, 14, 20, 30);
        Transaction transaction3 = Transaction.builder().id(3L).value(-50D).description("Uber").date(dateTime3).build();


        return new ArrayList<>(List.of(transaction1, transaction2, transaction3));
    }

    public static List<TransactionGetResponse> newTransactionGetResponseList() {
        LocalDateTime dateTime1 = LocalDateTime.of(2025, 1, 29, 15, 43, 30);
        TransactionGetResponse transaction1 = TransactionGetResponse.builder().id(1L).value(5000D).description("Salário").date(dateTime1).build();

        LocalDateTime dateTime2 = LocalDateTime.of(2025, 1, 28, 15, 43, 30);
        TransactionGetResponse transaction2 = TransactionGetResponse.builder().id(2L).value(-1000D).description("Aluguel").date(dateTime2).build();

        LocalDateTime dateTime3 = LocalDateTime.of(2025, 1, 28, 16, 20, 30);
        TransactionGetResponse transaction3 = TransactionGetResponse.builder().id(3L).value(-50D).description("Uber").date(dateTime3).build();

        return List.of(transaction1, transaction2, transaction3);
    }

    public static TransactionPostRequest newTransactionPostRequest() {
        return TransactionPostRequest.builder()
                .description("Fatura do Cartão")
                .value(-2700.4D)
                .build();
    }

    public static Transaction newTransactionToSave() {
        return Transaction.builder()
                .description("Fatura do Cartão")
                .value(2700.4D)
                .build();
    }

    public static Transaction newTransactionSaved() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 1, 30, 20, 25, 30);

        return newTransactionToSave().withId(4L).withDate(localDateTime);
    }

    public static TransactionPostResponse newTransactionPostResponse() {
        return TransactionPostResponse.builder()
                .id(4L)
                .description("Fatura do Cartão")
                .value(-2700.4D)
                .date(LocalDateTime.of(2025, 1, 30, 20, 25, 30))
                .build();
    }

    public static double getBalance() {
        return newTransactionList().stream().mapToDouble(Transaction::getValue).sum();
    }

    public static LocalDateTime getStartDateValid() {
        return newTransactionList().getFirst().getDate();
    }

    public static LocalDateTime getEndDateValid() {
        return newTransactionList().getLast().getDate();
    }

    public static List<Transaction> newPositiveTransactionsList() {
        return newTransactionList().stream().filter(transaction -> transaction.getValue() > 0).toList();
    }

    public static List<TransactionGetResponse> newPositiveTransactionGetResponseList() {
        return newTransactionGetResponseList().stream().filter(transaction -> transaction.getValue() > 0).toList();
    }

    public static List<Transaction> newNegativeTransactionsList() {
        return newTransactionList().stream().filter(transaction -> transaction.getValue() < 0).toList();
    }

    public static List<TransactionGetResponse> newNegativeTransactionGetResponseList() {
        return newTransactionGetResponseList().stream().filter(transaction -> transaction.getValue() < 0).toList();
    }
}
