package com.soa.soaproject.controller;

import com.soa.soaproject.exception.ResourceNotFoundException;
import com.soa.soaproject.model.Product;
import com.soa.soaproject.model.Sale;
import com.soa.soaproject.repository.ProductRepository;
import com.soa.soaproject.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
        return ResponseEntity.ok(sale);
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
        // Make sure the associated product exists
        Product product = productRepository.findById(sale.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + sale.getProduct().getId()));

        sale.setProduct(product);

        Sale createdSale = saleRepository.save(sale);
        return new ResponseEntity<>(createdSale, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable long id, @RequestBody Sale saleDetails) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));

        existingSale.setDate(saleDetails.getDate());
        existingSale.setAmount(saleDetails.getAmount());

        // Update associated product if saleDetails has a non-null product
        if (saleDetails.getProduct() != null) {
            Product product = productRepository.findById(saleDetails.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + saleDetails.getProduct().getId()));

            existingSale.setProduct(product);
        }

        Sale updatedSale = saleRepository.save(existingSale);
        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSale(@PathVariable long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));

        saleRepository.delete(sale);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
