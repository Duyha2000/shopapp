package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data // toString, equals, hashCode, getter, setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductDTO {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Positive(message = "Price must be a positive number")
    private double price;

    @PositiveOrZero(message = "Quantity must be a non-negative number")
    private int quantity;

    private String thumbnail;

    @NotBlank(message = "Description cannot be blank")
    private String description;


    @JsonProperty("category_id")
    @PositiveOrZero(message = "Category ID must be a positive number")
    private int categoryId;

    private MultipartFile file;
}