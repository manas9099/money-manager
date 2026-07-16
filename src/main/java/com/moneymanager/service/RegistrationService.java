package com.moneymanager.service;

import com.moneymanager.entity.*;
import com.moneymanager.repository.AccountRepository;
import com.moneymanager.repository.CategoryRepository;
import com.moneymanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    private static final List<String> DEFAULT_EXPENSE_CATEGORIES = List.of(
            "Food", "Shopping", "Fuel", "Rent", "Electricity", "Education",
            "Travel", "Healthcare", "Entertainment", "Subscriptions", "Tax",
            "Insurance", "Pets", "Others"
    );

    private static final List<String> DEFAULT_INCOME_CATEGORIES = List.of(
            "Salary", "Investment", "Refund", "Other Income"
    );

    @Transactional
    public User registerNewUser(String fullName, String email, String rawPassword) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);

        seedDefaultCashAccount(user);
        seedDefaultCategories(user);

        return user;
    }

    private void seedDefaultCashAccount(User user) {
        Account cash = new Account();
        cash.setUser(user);
        cash.setName("Cash");
        cash.setType(AccountType.CASH);
        cash.setCurrentBalance(BigDecimal.ZERO);
        cash.setInitialBalance(BigDecimal.ZERO);
        cash.setOpeningDate(LocalDate.now());
        cash.setCurrency(user.getDefaultCurrency());
        cash.setColor("#22C55E");
        cash.setIcon("wallet");
        accountRepository.save(cash);
    }

    private void seedDefaultCategories(User user) {
        DEFAULT_EXPENSE_CATEGORIES.forEach(name -> saveCategory(user, name, TransactionType.EXPENSE));
        DEFAULT_INCOME_CATEGORIES.forEach(name -> saveCategory(user, name, TransactionType.INCOME));
    }

    private void saveCategory(User user, String name, TransactionType type) {
        Category category = new Category();
        category.setUser(user);
        category.setName(name);
        category.setType(type);
        category.setDefault(true);
        categoryRepository.save(category);
    }
}
