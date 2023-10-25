package com.sky.ecommerce.controller;

import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Brand;
import com.sky.ecommerce.model.Category;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.model.Review;
import com.sky.ecommerce.response.ProductReviewsResponse;
import com.sky.ecommerce.service.BrandService;
import com.sky.ecommerce.service.CategoryService;
import com.sky.ecommerce.service.ProductService;
import com.sky.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ReviewService reviewService;
    @GetMapping("/brand/all")
    public ResponseEntity<List<Brand>> findAllBrands() {
        List<Brand> brands = brandService.findAllBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/category/all")
    public ResponseEntity<List<Category>> findAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/product/all")
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product")
    public  ResponseEntity<Product> findProductByNameHandler(@RequestParam String productName) throws ProductException {
        Product product = productService.findProductByName(productName);
        return  new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/product/review/{productId}")
    public ResponseEntity<ProductReviewsResponse> getProductReviews(@PathVariable String productId) {
        List<Review> reviews = reviewService.getReviewsByProduct(productId);
        double averageRating = reviewService.calculateAverageRating(reviews);

        ProductReviewsResponse response = new ProductReviewsResponse(reviews, averageRating);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/product/search/name")
    public ResponseEntity<List<Product>> SearchProductByName(@RequestParam String keyword){
        List<Product> productList = productService.SearchProductByName(keyword);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/search/brand")
    public ResponseEntity<List<Product>> SearchProductByBrand(@RequestParam String keyword){
        List<Product> productList = productService.SearchProductByBrand(keyword);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/search/category")
    public ResponseEntity<List<Product>> SearchProductByCategory(@RequestParam String keyword){
        List<Product> productList = productService.SearchProductByCategory(keyword);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/top-rated-products")
    public ResponseEntity<List<Product>> getTopRatedProducts() {
        List<Product> topRatedProducts = reviewService.getTopRatedProducts(10);
        return new ResponseEntity<>(topRatedProducts, HttpStatus.OK);
    }
}
