package com.moneymanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "investments")
public class Investment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name; // e.g. "HDFC Flexicap SIP", "Bitcoin"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvestmentType type;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal investedAmount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal currentValue;

    @Column(nullable = false)
    private LocalDate startDate;

    /** Annualized return, stored as a percentage e.g. 12.50 for 12.5%. */
    private BigDecimal annualReturnPercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
