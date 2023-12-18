package com.soa.soaproject.controller;

import com.soa.soaproject.model.Product;
import com.soa.soaproject.repository.ProductRepository;
import com.soa.soaproject.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@CrossOrigin("*")

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;


    @Autowired
    public void ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/getBestSellingProducts")
    public List<Product> getBestSellingProducts() {
        List<Object[]> result = saleRepository.findBestSellingProducts();

        List<Product> bestSellingProducts = result.stream()
                .map(array -> (Product) array[0])
                .collect(Collectors.toList());

        return bestSellingProducts;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
    @GetMapping("/calculateInventoryStatusPercentage")
    public List<Map<String, Object>> calculateInventoryStatusPercentage() {
        return productRepository.calculateInventoryStatusPercentage();
    }
    public DashboardController(ProductRepository productRepository, SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
    }

        @GetMapping("/statistics")
        public List<String> getDashboardStatistics() {
            List<String> statistics = new ArrayList<>();

            long totalProducts = productRepository.count();
            long totalSales = saleRepository.count();

            statistics.add(String.valueOf(totalProducts));
            statistics.add(String.valueOf(totalSales));

            // Add more statistics as needed

            return statistics;
        }

    @GetMapping("/averagePrice")
    public double getAverageProductPrice() {
        return productRepository.getAverageProductPrice();
    }

    @GetMapping("/highestSaleAmount")
    public double getHighestSaleAmount() {
        return saleRepository.findHighestSaleAmount();
    }

    @GetMapping("/uniqueCategories")
    public long getUniqueProductCategories() {
        return productRepository.countDistinctCategories();
    }


    @GetMapping("/totalRevenue")
    public double getTotalRevenue() {
        return saleRepository.calculateTotalRevenue();
    }

    @GetMapping("/totalQuantitySold")
    public long getTotalQuantitySold() {
        // Implement logic to calculate the total quantity of products sold
        return saleRepository.calculateTotalQuantitySold();
    }

    @GetMapping("/productWithLowestStock")
    public String getProductWithLowestStock() {
        // Retrieve the first product (lowest stock) using Pageable
        List<Product> products = productRepository.findProductsOrderedByStockAsc((Pageable) PageRequest.of(0, 1));

        if (!products.isEmpty()) {
            return products.get(0).toString();
        } else {
            return "No products found.";
        }
    }


    @GetMapping("/productsInEachCategory")
    public Map<String, Long> getProductsInEachCategory() {
        return productRepository.countProductsInEachCategory();
    }

    @GetMapping("/productsAddedInPeriod")
    public long getProductsAddedInPeriod(@RequestParam String startDate, @RequestParam String endDate) {
        // Implement logic to retrieve the total number of products added in a specific time period
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return productRepository.countProductsAddedInPeriod(start, end);
    }

    @GetMapping("/averageProfitMargin")
    public double getAverageProfitMargin() {
        // Implement logic to calculate the average profit margin across products
        return productRepository.calculateAverageProfitMargin();
    }




}
