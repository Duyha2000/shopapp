package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    // get - post - put - delete
    // get all categories:
    // http://localhost:8080/api/v1/categories
    // ResponseEntity.ok - status code 200
    // Nếu không có @GetMapping("") thì sẽ báo lỗi 404
    @GetMapping("")
    // @RequestParam: lấy giá trị từ url: giả sử url là http://localhost:8080/api/v1/categories?page=1&limit=10&sort=ASC
    // thì page = 1, limit = 10, sort = ASC
    // @RequestParam(value = "page", defaultValue = "1") int page: nếu không có page thì mặc định là 1
    // @RequestParam là number_of_products thì int numbersOfProducts
    public ResponseEntity<String> getAllCategories(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                   @RequestParam(value = "sort", defaultValue = "ASC") String sort) {
        return ResponseEntity.ok("This is a get request %d - %d - %s".formatted(page, limit, sort));
    }
    @PostMapping("")
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage));
        return ResponseEntity.ok("This is a post request " + categoryDTO.getName());
    }
    // put id:

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok("This is a put request" + id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok("This is a delete request" + id);
    }


    // get category by id
    // create category
    // update category
    // delete category


}
