package com.moneymanager.controller;

import com.moneymanager.entity.Account;
import com.moneymanager.entity.Transaction;
import com.moneymanager.entity.TransactionType;
import com.moneymanager.entity.User;
import com.moneymanager.repository.AccountRepository;
import com.moneymanager.repository.TransactionRepository;
import com.moneymanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @GetMapping("/add")
    public String showAddTransactionForm(@AuthenticationPrincipal UserDetails principal, Model model) {
        User user = userRepository.findByEmail(principal.getUsername()).orElseThrow();
        
        model.addAttribute("accounts", accountRepository.findByUserIdAndArchivedFalse(user.getId()));
        model.addAttribute("transaction", new Transaction());
        
        return "add-transaction";
    }

    @PostMapping("/add")
    public String saveTransaction(@AuthenticationPrincipal UserDetails principal,
                                  @ModelAttribute Transaction transaction,
                                  @RequestParam Long accountId) {
        User user = userRepository.findByEmail(principal.getUsername()).orElseThrow();
        Account account = accountRepository.findById(accountId).orElseThrow();

        transaction.setUser(user);
        transaction.setAccount(account);
        transaction.setTransactionDate(LocalDateTime.now());

        if (transaction.getType() == TransactionType.INCOME) {
            account.setCurrentBalance(account.getCurrentBalance().add(transaction.getAmount()));
        } else if (transaction.getType() == TransactionType.EXPENSE) {
            account.setCurrentBalance(account.getCurrentBalance().subtract(transaction.getAmount()));
        }

        transactionRepository.save(transaction);
        accountRepository.save(account);

        return "redirect:/accounts";
    }
}
