package com.rahat.sales_management.service;

import com.rahat.sales_management.model.Product;
import com.rahat.sales_management.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        // todo pagination
        return productRepository.findAll();
    }

    public Product findById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow();
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Integer id, Product product) {
        findById(id);
        product.setId(id);
        return productRepository.save(product);
    }

    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
