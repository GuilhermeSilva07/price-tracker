package com.guilherme.price_tracker.product.dto;

import com.guilherme.price_tracker.product.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String url,
        String externalId,
        BigDecimal targetPrice,
        BigDecimal currentPrice,
        UUID ownerId
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getUrl(),
                product.getExternalId(),
                product.getTargetPrice(),
                product.getCurrentPrice(),
                product.getOwner().getId()
        );
    }
}
