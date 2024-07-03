package com.actividadgrupal.product_service.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Description is required")
    @Column(nullable = false)
    private String description;

    @NotBlank(message = "Category is required")
    @Column(nullable = false)
    private String category;

    @NotBlank(message = "Size is required")
    @Column(nullable = false)
    private String size;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be  equal or greater than 0")
    @Column(nullable = false)
    private Float price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be  equal or greater than 0")
    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = true)
    private String image;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


}
