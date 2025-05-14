package com.branches.repository;

import com.branches.model.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT SUM(t.value) FROM Transaction t")
    Double sumAllTransactions();

    List<Transaction> findAllByValueGreaterThan(Double valueIsGreaterThan);

    List<Transaction> findAllByValueIsLessThan(Double valueIsLessThan);

    @Query(
            "SELECT t FROM Transaction t WHERE (:startDate IS NULL OR t.date >= :startDate)" +
                    "AND (:endDate IS NULL OR t.date <= :endDate)"
    )
    List<Transaction> findAllByDateFilter(LocalDateTime startDate, LocalDateTime endDate, Sort sort);
}
