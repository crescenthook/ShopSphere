package com.shopshere.product_service.Controller;

import com.shopshere.product_service.DTO.ProductRequestDto;
import com.shopshere.product_service.DTO.ProductResponseDto;
import com.shopshere.product_service.DTO.ProductResponseListDto;
import com.shopshere.product_service.Service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto request){
        ProductResponseDto response = productService.createProduct(request);
        return new ResponseEntity<ProductResponseDto>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseListDto>> getAllProducts(){
        List<ProductResponseListDto> productsList = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id){
        ProductResponseDto response = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto request){
        ProductResponseDto response = productService.updateProduct(id,request);
        return new ResponseEntity<ProductResponseDto>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        String message = "Product with id " + id + " successfully deleted";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
