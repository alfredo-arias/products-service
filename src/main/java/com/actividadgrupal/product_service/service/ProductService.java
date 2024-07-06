package com.actividadgrupal.product_service.service;

import com.actividadgrupal.product_service.config.WebClientConfig;
import com.actividadgrupal.product_service.dto.ProductDto;
import com.actividadgrupal.product_service.dto.StockRequest;
import com.actividadgrupal.product_service.dto.StockResponse;
import com.actividadgrupal.product_service.entity.Product;
import com.actividadgrupal.product_service.exception.ProductAlreadyExistsException;
import com.actividadgrupal.product_service.exception.ResourceNotFoundException;
import com.actividadgrupal.product_service.mapper.ProductMapper;
import com.actividadgrupal.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final WebClient.Builder webClientBuilder;

    public ProductDto createProduct(ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findByName(productDto.getName());

        if (optionalProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product already exists");
        }

        Product product = ProductMapper.mapToProduct(productDto);
        Product savedProduct = productRepository.save(product);

        // =====================

        StockRequest stockRequest = new StockRequest();
        stockRequest.setProductId(savedProduct.getId());
        stockRequest.setQuantity(savedProduct.getQuantity());

        log.info("productDto: {}",productDto);

        StockResponse stockResponse = webClientBuilder.build()
                .post()
                .uri("https://stocks-service-production.up.railway.app/api/stocks")
                .body(Mono.just(stockRequest), StockRequest.class)
                .retrieve()
                .bodyToMono(StockResponse.class)
                .block();

        log.info("New product created: {}", stockResponse);
        // =====================



        return ProductMapper.mapToUserDto(savedProduct);
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::mapToUserDto).collect(Collectors.toList());
    }

    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        Product product = optionalProduct.orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", productId)
        );

        return ProductMapper.mapToUserDto(product);
    }

    public List<ProductDto> getProductByCategory(String category) {
        List<Product> products = productRepository.findAllByCategory(category);

        /*Product product = optionalProduct.orElseThrow(
                () -> new ResourceNotFoundException("Product", "Category", category)
        );*/
        return products.stream().map(ProductMapper::mapToUserDto).collect(Collectors.toList());

    }

    public List<ProductDto> findAllByCategoryAndSize(String category, String size) {
        List<Product> products = productRepository.findAllByCategoryAndSize(category,size);

        /*Product product = optionalProduct.orElseThrow(
                () -> new ResourceNotFoundException("Product", "Category", category)
        );*/

        return products.stream().map(ProductMapper::mapToUserDto).collect(Collectors.toList());
    }

    public ProductDto updateProduct(ProductDto productDto) {
        Product existingProduct = productRepository.findById(productDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", productDto.getId())
        );

        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setCategory(productDto.getCategory());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setSize(productDto.getSize());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setImage(productDto.getImage());
        existingProduct.setCreatedAt(existingProduct.getCreatedAt());
        existingProduct.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(existingProduct);

        return ProductMapper.mapToUserDto(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", productId)
        );
        productRepository.delete(existingProduct);
    }

}
