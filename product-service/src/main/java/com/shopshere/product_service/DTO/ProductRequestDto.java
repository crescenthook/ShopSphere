package com.shopshere.product_service.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {

    @NotBlank(message = "Product name is required.")
    private String name;

    @NotNull(message = "Product category ID is required.")
    @Positive(message = "Product category ID must be positive.")
    private Long categoryId;

    private String description;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0",inclusive = false, message = "Price cannot be negative.")
    private BigDecimal price;

    @NotNull(message = "Product quantity is required.")
    @Min(value = 0, message = "Quantity cannot be negative.")
    private Integer quantity;

}
