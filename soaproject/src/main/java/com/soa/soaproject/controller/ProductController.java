package com.soa.soaproject.controller;

import com.soa.soaproject.exception.ResourceNotFoundException;
import com.soa.soaproject.model.Product;
import com.soa.soaproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productRepository.save(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception ex) {
            // Handle other exceptions if needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id:" + id));
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product productDetails) {
        try {
            Product updateProduct = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id: " + id));

            // Update fields
            updateProduct.setCode(productDetails.getCode());
            updateProduct.setName(productDetails.getName());
            updateProduct.setDescription(productDetails.getDescription());
            updateProduct.setPrice(productDetails.getPrice());
            updateProduct.setQtestock(productDetails.getQtestock());
            updateProduct.setInventoryStatus(productDetails.getInventoryStatus());
            updateProduct.setCategory(productDetails.getCategory());
            updateProduct.setImage(productDetails.getImage());
            updateProduct.setRating(productDetails.getRating());
            updateProduct.setCost(productDetails.getCost());
            updateProduct.setDateCreated(productDetails.getDateCreated());

            productRepository.save(updateProduct);

            return ResponseEntity.ok(updateProduct);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            // Handle other exceptions if needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id: " + id));

            productRepository.delete(product);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            // Handle other exceptions if needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
