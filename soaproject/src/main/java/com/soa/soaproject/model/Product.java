package com.soa.soaproject.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int qtestock;

    @Column(name = "inventoryStatus")
    private String inventoryStatus;

    // Many-to-One relationship with Scategorie
    @ManyToOne
    @JoinColumn(name = "scategorie_id", nullable = false)
    private Scategorie category;

    @Column(name = "image")
    private String image;

    @Column(name = "rating")
    private String rating;

    @Column(name = "cost") // New field for the cost of the product
    private double cost;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    // One-to-many relationship with Sale
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Sale> sales;


    public Product(String p003, String s, String s1, double v, int i, String inStock, Scategorie scategorie2, String image, Object o, double v1, LocalDateTime now, Object o1) {
    }
}