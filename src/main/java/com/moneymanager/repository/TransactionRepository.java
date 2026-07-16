package com.moneymanager.repository;

import com.moneymanager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserIdOrderByTransactionDateDesc(Long userId);

    List<Transaction> findByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(
            Long userId, LocalDateTime start, LocalDateTime end);

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findTop10ByUserIdOrderByTransactionDateDesc(Long userId);
}
