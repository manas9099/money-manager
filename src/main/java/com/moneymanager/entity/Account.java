package com.moneymanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Column(nullable = false)
    private String name; // e.g. "SBI Savings", "Paytm Wallet"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal initialBalance;

    @Column(nullable = false)
    private LocalDate openingDate;

    @Column(nullable = false)
    private String currency = "INR";

    private String color; // hex code, e.g. #4F46E5
    private String icon;  // icon key/name used by the frontend

    @Column(nullable = false)
    private boolean includeInNetWorth = true;

    @Column(nullable = false)
    private boolean archived = false;
}
