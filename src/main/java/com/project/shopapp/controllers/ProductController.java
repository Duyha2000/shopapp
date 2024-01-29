package com.project.shopapp.controllers;

import com.project.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    // get - post - put - delete
    // get all products:
    // http://localhost:8080/api/v1/products
    @GetMapping("")
    public ResponseEntity<String> getAllProducts(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                 @RequestParam(value = "sort", defaultValue = "ASC") String sort) {
        return ResponseEntity.ok("This is a get request %d - %d - %s".formatted(page, limit, sort));
    }

    // post product:
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertProduct(
            @Valid @RequestPart(name = "productDTO") ProductDTO productDTO,
            @RequestPart("file") @NotNull @Size(max = 10485760, message = "File size must not exceed 10MB") MultipartFile file,
            BindingResult bindingResult) {
        try {
            // Validate ProductDTO
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(
                        bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage));
            }
            // Handle file upload
            
            return ResponseEntity.ok("This is a post request " + productDTO.getName());
        } catch (Exception e) {
            // Handle the exception as per your requirements
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during file upload: " + e.getMessage());
        }
    }


    // put id:

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok("This is a put request" + id);
    }

    // @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok("This is a delete request" + id);
    }

    // get product by id: http://localhost:8080/api/v1/products/1
    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok("This is a get request" + id);
    }
}

//{
//        "name": "",
//        "price": -20.0,
//        "quantity": -50,
//        "thumbnail": "fake_thumbnail_url",
//        "description": "Fake product description",
//        "category_id": 1
//        }

