package com.sky.ecommerce.controller;

import com.sky.ecommerce.exception.BrandException;
import com.sky.ecommerce.exception.CategoryException;
import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.model.Role;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.CreateProductRequest;
import com.sky.ecommerce.response.ApiResponse;
import com.sky.ecommerce.service.ProductService;
import com.sky.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req, @RequestHeader("Authorization")String jwt) throws UserException, CategoryException, BrandException {
        User user = userService.findUserProfileByJwt(jwt);
        if(user.getRole()!= Role.ADMIN){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Product product = productService.createProduct(req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody CreateProductRequest req,  @RequestHeader("Authorization")String jwt) throws UserException, ProductException, CategoryException, BrandException {
        User user = userService.findUserProfileByJwt(jwt);
        if(user.getRole()!= Role.ADMIN){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Product updatedProduct = productService.updateProduct(productId, req);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId, @RequestHeader("Authorization")String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        if(user.getRole()!= Role.ADMIN){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        productService.deleteProduct(productId);
        ApiResponse res = new ApiResponse();
        res.setMessage("product deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
