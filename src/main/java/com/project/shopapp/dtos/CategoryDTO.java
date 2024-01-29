package com.project.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data // toString, equals, hashCode, getter, setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDTO {
    // DTO: Data Transfer Object: dùng để truyền dữ liệu giữa các class
    // Không cần id vì id là auto increment
    @NotEmpty(message = "Category name cannot be empty")
    private String name;

}
