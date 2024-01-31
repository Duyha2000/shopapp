package com.project.shopapp.controllers;

import com.project.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    // get - post - put - delete
    // get all products:
    @GetMapping("")
    public ResponseEntity<String> getAllProducts(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                 @RequestParam(value = "sort", defaultValue = "ASC") String sort) {
        return ResponseEntity.ok("This is a get request %d - %d - %s".formatted(page, limit, sort));
    }

    // upload file và kiểm tra dữ liệu < 10MB
    // post product:
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<?> insertProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult bindingResult) {
        try {
            // Validate ProductDTO
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(
                        bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage));
            }
            MultipartFile file = productDTO.getFile();
            // Validate file upload: kiểm tra file có tồn tại không
            if (file != null) {
                // Handle file upload: kiểm tra file có đúng định dạng không, có quá 10MB không
                if (file.getSize() > MAX_FILE_SIZE) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("Only JPEG and PNG image files are allowed");
                }
            }
            // Lưu file vào thư mục uploads
            assert file != null;
            String fileName = storeFile(file);
            // Lưu tên file vào productDTO
            productDTO.setThumbnail(fileName);
            return ResponseEntity.ok("  This is a post request");
        } catch (Exception e) {
            //  Handle the exception as per your requirements
            return ResponseEntity.badRequest().body("an error occurred");
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        System.out.println(filename);
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
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