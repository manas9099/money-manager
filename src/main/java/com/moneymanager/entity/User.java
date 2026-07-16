package com.moneymanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Application user. Password is stored as a BCrypt hash (see SecurityConfig).
 * Google Sign-In users may have a null password and a populated googleId instead.
 */
@Getter
@Setter
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true)
})
public class User extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    /** BCrypt hash. Nullable to support Google-only sign-in. */
    private String password;

    /** Populated when the user signs in via Google OAuth. */
    private String googleId;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean twoFactorEnabled = false;

    @Column(nullable = false)
    private String role = "ROLE_USER";

    @Column(nullable = false)
    private String defaultCurrency = "INR";

    @Column(nullable = false)
    private String theme = "LIGHT"; // LIGHT or DARK

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();
}
