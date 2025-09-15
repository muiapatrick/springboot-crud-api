package com.example.main.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_account")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bic_swift")
    private String bicSwift;

    @Column(name = "client_id")
    private String clientId;
}
