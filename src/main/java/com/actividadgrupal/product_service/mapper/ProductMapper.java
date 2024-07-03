package com.actividadgrupal.product_service.mapper;

import com.actividadgrupal.product_service.dto.ProductDto;
import com.actividadgrupal.product_service.entity.Product;

public class ProductMapper {

    public static ProductDto mapToUserDto(Product product) {

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getSize(),
                product.getPrice(),
                product.getQuantity(),
                product.getImage(),
                product.getCreatedAt(),
                product.getUpdatedAt());
    }

    public static Product mapToProduct(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getCategory(),
                productDto.getSize(),
                productDto.getPrice(),
                productDto.getQuantity(),
                productDto.getImage(),
                productDto.getCreatedAt(),
                productDto.getUpdatedAt());
    }

}
