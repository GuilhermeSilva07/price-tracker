package com.guilherme.price_tracker.product;

import com.guilherme.price_tracker.product.dto.ProductResponse;
import com.guilherme.price_tracker.product.dto.RegisterProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> register(@Valid @RequestBody RegisterProductRequest request) {
        Product product = productService.registerProduct(request);
        ProductResponse response = ProductResponse.from(product);
        return ResponseEntity.created(URI.create("/api/products/" + response.id())).body(response);
    }
}
