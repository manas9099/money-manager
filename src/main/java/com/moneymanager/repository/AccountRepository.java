package com.moneymanager.repository;

import com.moneymanager.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserIdAndArchivedFalse(Long userId);
}
