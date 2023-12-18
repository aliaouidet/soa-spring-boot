package com.soa.soaproject.repository;

import com.soa.soaproject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT AVG(p.price) FROM Product p")
    double getAverageProductPrice();
    @Query("SELECT s.product, COUNT(s) as saleCount FROM Sale s GROUP BY s.product ORDER BY saleCount DESC")
    List<Object[]> findBestSellingProducts();
    @Query("SELECT p FROM Product p ORDER BY p.qtestock ASC")
    List<Product> findProductsOrderedByStockAsc(Pageable pageable);

    @Query("SELECT DISTINCT UPPER(REPLACE(p.inventoryStatus, ' ', '')) AS modified_inventory_status, COUNT(p.id) AS status_count FROM Product p GROUP BY p.inventoryStatus")
    List<Map<String, Object>> calculateInventoryStatusPercentage();
    @Query("SELECT COUNT(DISTINCT p.category) FROM Product p")
    long countDistinctCategories();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.dateCreated BETWEEN :startDate AND :endDate")
    long countProductsAddedInPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT MAX((p.price - p.cost) / p.price) FROM Product p")
    double calculateAverageProfitMargin();

    @Query("SELECT p.category.nomscategorie, COUNT(p) FROM Product p GROUP BY p.category.nomscategorie")
    Map<String, Long> countProductsInEachCategory();
}
