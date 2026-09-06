package com.shopshere.product_service.Repository;

import com.shopshere.product_service.DTO.ProductResponseListDto;
import com.shopshere.product_service.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByCategoryId(Long categoryId);
}
