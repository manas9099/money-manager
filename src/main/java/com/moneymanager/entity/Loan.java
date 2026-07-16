package com.moneymanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String counterpartyName; // who the loan is with

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanDirection direction; // BORROWED or LENT

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal principalAmount;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal remainingAmount;

    private BigDecimal interestRatePercent;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal emiAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate dueDate;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal lateFeeAccrued = BigDecimal.ZERO;
}
