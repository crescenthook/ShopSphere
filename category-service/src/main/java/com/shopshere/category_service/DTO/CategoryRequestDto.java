package com.shopshere.category_service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDto(

        @NotBlank(message = "Name is required.")
        @Size(max = 100, message = "Name cannot exceed 100 characters.")
        String name,

        @Size(max = 500, message = "Description cannot exceed 500 characters.")
        String description
)

{}
