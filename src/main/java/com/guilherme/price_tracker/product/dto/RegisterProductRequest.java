package com.guilherme.price_tracker.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record RegisterProductRequest(
        @NotBlank String url,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal targetPrice,
        @NotNull UUID userId
) {
}
