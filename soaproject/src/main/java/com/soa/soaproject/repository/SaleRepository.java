// SaleRepository.java
package com.soa.soaproject.repository;

import com.soa.soaproject.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s.product, COUNT(s) AS saleCount FROM Sale s GROUP BY s.product ORDER BY saleCount DESC")
    List<Object[]> findBestSellingProducts();
    @Query("SELECT SUM(s.amount * p.price) FROM Sale s JOIN s.product p")
    Double calculateTotalRevenue();
    @Query("SELECT COUNT(s) FROM Sale s")
    Long calculateTotalQuantitySold();

    @Query("SELECT s.product.code FROM Sale s ORDER BY s.date DESC")
    List<String> findRecentlySoldProducts();

    @Query("SELECT MAX(s.amount) FROM Sale s")
    double findHighestSaleAmount();  // Add this method



    // Add more custom query methods as needed
}
