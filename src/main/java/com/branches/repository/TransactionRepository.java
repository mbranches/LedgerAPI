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

    List<Transaction> findAllByDateBetween(LocalDateTime dateAfter, LocalDateTime dateBefore, Sort sort);

    List<Transaction> findAllByDateGreaterThanEqual(LocalDateTime dateIsGreaterThan, Sort sort);

    List<Transaction> findAllByDateLessThanEqual(LocalDateTime dateEnd, Sort sort);
}
