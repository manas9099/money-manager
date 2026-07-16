package com.moneymanager.controller;

import com.moneymanager.entity.Account;
import com.moneymanager.entity.Transaction;
import com.moneymanager.entity.User;
import com.moneymanager.repository.AccountRepository;
import com.moneymanager.repository.TransactionRepository;
import com.moneymanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(@AuthenticationPrincipal UserDetails principal, Model model) {
        User user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));

        List<Account> accounts = accountRepository.findByUserIdAndArchivedFalse(user.getId());
        List<Transaction> recentTransactions =
                transactionRepository.findTop10ByUserIdOrderByTransactionDateDesc(user.getId());

        BigDecimal totalBalance = accounts.stream()
                .filter(Account::isIncludeInNetWorth)
                .map(Account::getCurrentBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("user", user);
        model.addAttribute("accounts", accounts);
        model.addAttribute("recentTransactions", recentTransactions);
        model.addAttribute("totalBalance", totalBalance);

        return "dashboard";
    }
}
