package com.guilherme.price_tracker.product;

import com.guilherme.price_tracker.exception.InvalidProductUrlException;
import com.guilherme.price_tracker.exception.UserNotFoundException;
import com.guilherme.price_tracker.product.dto.RegisterProductRequest;
import com.guilherme.price_tracker.user.User;
import com.guilherme.price_tracker.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Product registerProduct(RegisterProductRequest request) {
        User owner = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(request.userId()));

        String externalId = MercadoLivreUrlParser.extractItemId(request.url())
                .orElseThrow(() -> new InvalidProductUrlException(request.url()));

        Product product = Product.builder()
                .url(request.url())
                .externalId(externalId)
                .targetPrice(request.targetPrice())
                .owner(owner)
                .build();

        return productRepository.save(product);
    }
}
