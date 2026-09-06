package com.shopshere.product_service.Service;

import com.shopshere.product_service.DTO.ProductRequestDto;
import com.shopshere.product_service.DTO.ProductResponseDto;
import com.shopshere.product_service.DTO.ProductResponseListDto;
import com.shopshere.product_service.Entity.Product;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto request);

    List<ProductResponseListDto> getAllProducts();

    ProductResponseDto getProductById(Long id);

    ProductResponseDto updateProduct(Long id, ProductRequestDto request);

    void deleteProduct(Long id);

    List<ProductResponseListDto> findProductsByCategoryId(Long categoryId);
}
