package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.BrandException;
import com.sky.ecommerce.exception.CategoryException;
import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.request.CreateProductRequest;

import java.util.List;

public interface ProductService {

    Product createProduct(CreateProductRequest req) throws CategoryException, BrandException;

    List<Product> findAllProducts();

    Product updateProduct(String productId, CreateProductRequest req) throws ProductException, CategoryException, BrandException;

    void deleteProduct(String productId) throws ProductException;

    Product findProductByName(String name) throws ProductException;

    List<Product> SearchProductByName(String keyword);

    List<Product> SearchProductByBrand(String keyword);

    List<Product> SearchProductByCategory(String keyword);
}
