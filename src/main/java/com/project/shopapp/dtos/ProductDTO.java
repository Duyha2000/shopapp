package com.project.shopapp.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // toString, equals, hashCode, getter, setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductDTO {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Positive(message = "Price must be a positive number")
    private double price;

    @PositiveOrZero(message = "Quantity must be a non-negative number")
    private int quantity;

    // Assuming thumbnail and description can be empty or null
    @NotBlank(message = "Thumbnail cannot be blank")
    private String thumbnail;
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @Positive(message = "Category ID must be a positive number")
    @JsonProperty("category_id")
    private int categoryId;

}
