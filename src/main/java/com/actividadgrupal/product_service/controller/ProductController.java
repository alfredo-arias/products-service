package com.actividadgrupal.product_service.controller;

import com.actividadgrupal.product_service.dto.ProductDto;
import com.actividadgrupal.product_service.entity.Product;
import com.actividadgrupal.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {
        ProductDto savedProductDto = productService.createProduct(productDto);
        return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/byCategory")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@RequestParam String category) {
        List<ProductDto> products = productService.getProductByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/byCategoryAndSize")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryAndSize(@RequestParam String category, @RequestParam String size) {
        List<ProductDto> products = productService.findAllByCategoryAndSize(category, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        ProductDto productDto = productService.getProductById(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long productId, @RequestBody @Valid ProductDto productDto) {
        productDto.setId(productId);
        ProductDto updatedProduct = productService.updateProduct(productDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }

}
