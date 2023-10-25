package com.sky.ecommerce.controller;

import com.sky.ecommerce.exception.CategoryException;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.Category;
import com.sky.ecommerce.model.Role;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.response.ApiResponse;
import com.sky.ecommerce.service.CategoryService;
import com.sky.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category req, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Category category = categoryService.createCategory(req);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> findAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}/update")
    public ResponseEntity<Category> updateCategory(@PathVariable String categoryId, @RequestBody Category req, @RequestHeader("Authorization") String jwt) throws UserException, CategoryException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Category category = categoryService.updateCategory(categoryId, req);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId, @RequestHeader("Authorization") String jwt) throws UserException, CategoryException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        categoryService.deleteCategory(categoryId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Category deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
