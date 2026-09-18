package com.shopshere.product_service.Service;

import com.shopshere.product_service.Client.CategoryClient;
import com.shopshere.product_service.DTO.CategoryResponseDto;
import com.shopshere.product_service.DTO.ProductRequestDto;
import com.shopshere.product_service.DTO.ProductResponseDto;
import com.shopshere.product_service.DTO.ProductResponseListDto;
import com.shopshere.product_service.Entity.Product;
import com.shopshere.product_service.Exception.ProductNotFoundException;
import com.shopshere.product_service.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final CategoryClient categoryClient;

    ProductServiceImpl(ProductRepository productRepository, CategoryClient categoryClient)
    {
        this.productRepository = productRepository;
        this.categoryClient = categoryClient;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto request) {

        CategoryResponseDto category = categoryClient.getCategoryById(request.getCategoryId());
        Product product = new Product();

        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setCategoryId(request.getCategoryId());
        product.setName(request.getName());

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct,category);
    }

    @Override
    public List<ProductResponseListDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToResponseList).toList();
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product =  productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found."));

        CategoryResponseDto category = categoryClient.getCategoryById(product.getCategoryId());
        return mapToResponse(product,category);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found."));

        CategoryResponseDto category = categoryClient.getCategoryById(request.getCategoryId());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setCategoryId(request.getCategoryId());

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct,category);

    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found."));

        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseListDto> findProductsByCategoryId(Long categoryId) {
        List<Product> productList = productRepository.findProductsByCategoryId(categoryId);

        return productList.stream().map(this::mapToResponseList).collect(Collectors.toList());
    }

    private ProductResponseDto mapToResponse(Product product, CategoryResponseDto category) {

        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getImageUrl(),
                category
        );
    }
    private ProductResponseListDto mapToResponseList(Product product) {
        return new ProductResponseListDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategoryId(),
                product.getImageUrl()
        );
    }
}
