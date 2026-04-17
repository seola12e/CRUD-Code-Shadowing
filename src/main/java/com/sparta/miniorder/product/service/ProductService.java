package com.sparta.miniorder.product.service;

import com.sparta.miniorder.product.dto.ProductRequest;
import com.sparta.miniorder.product.dto.ProductResponse;
import com.sparta.miniorder.product.entity.Product;
import com.sparta.miniorder.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(@Valid ProductRequest request) {
        Product product = new Product(request.getName(), request.getPrice());
        Product saved = productRepository.save(product);
        return new ProductResponse(saved);
    }

    public ProductResponse getProduct(Long id) {
        Product product = findProductById(id);
        return new ProductResponse(product);
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::new)
                .toList();
    }

    @Transactional
    public ProductResponse updateProduct(Long id, @Valid ProductRequest request) {
        Product product = findProductById(id);
        product.update(request.getName(), request.getPrice());
        return new ProductResponse(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. id=" + id));
    }
}
