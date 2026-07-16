package com.moneymanager.repository;

import com.moneymanager.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByUserId(Long userId);
    List<Bill> findByUserIdAndNextDueDateBetween(Long userId, LocalDate start, LocalDate end);
}
