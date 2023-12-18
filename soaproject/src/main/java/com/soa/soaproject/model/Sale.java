package com.soa.soaproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "amount")
    private double amount;

    // Many-to-one relationship with Product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public Sale(LocalDateTime localDateTime, double v, Product product1) {
    }
}
