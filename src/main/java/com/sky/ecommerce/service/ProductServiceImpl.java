package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.BrandException;
import com.sky.ecommerce.exception.CategoryException;
import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Brand;
import com.sky.ecommerce.model.Category;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.repository.BrandRepository;
import com.sky.ecommerce.repository.CategoryRepository;
import com.sky.ecommerce.repository.ProductRepository;
import com.sky.ecommerce.request.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    @Override
    public Product createProduct(CreateProductRequest req) throws CategoryException, BrandException {
        Category category = categoryRepository.findByName(req.getCategoryName());
        if (category == null) {
            throw new CategoryException("Category not found with name: " + req.getCategoryName());
        }
        Brand brand = brandRepository.findByName(req.getBrandName());
        if (brand == null) {
            throw new BrandException("Brand not found with name: " + req.getBrandName());
        }
        Product newProduct = new Product();
        newProduct.setName(req.getName());
        newProduct.setBrand(brand);
        newProduct.setDescription(req.getDescription());
        newProduct.setQuantity(req.getQuantity());
        newProduct.setPrice(req.getPrice());
        newProduct.setImageUrl(req.getImageUrl());
        newProduct.setOperatingSystem(req.getOperatingSystem());
        newProduct.setConnectivity(req.getConnectivity());
        newProduct.setCategory(category);

        return productRepository.save(newProduct);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(String productId, CreateProductRequest req) throws ProductException, CategoryException, BrandException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("No products found with ID: " + productId));

        Category category = categoryRepository.findByName(req.getCategoryName());
        if (category == null) {
            throw new CategoryException("Category not found with name: " + req.getCategoryName());
        }
        Brand brand = brandRepository.findByName(req.getBrandName());
        if (brand == null) {
            throw new BrandException("Brand not found with name: " + req.getBrandName());
        }

        existingProduct.setName(req.getName());
        existingProduct.setBrand(brand);
        existingProduct.setDescription(req.getDescription());
        existingProduct.setQuantity(req.getQuantity());
        existingProduct.setPrice(req.getPrice());
        existingProduct.setImageUrl(req.getImageUrl());
        existingProduct.setOperatingSystem(req.getOperatingSystem());
        existingProduct.setConnectivity(req.getConnectivity());
        existingProduct.setCategory(category);

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(String productId) throws ProductException {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productRepository.delete(product);
        } else {
            throw new ProductException("Product not found with ID: " + productId);
        }
    }

    @Override
    public Product findProductByName(String name) throws ProductException {
        Optional<Product> productOptional = productRepository.findByName(name);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new ProductException("Product not found with name: " + name);
        }
    }

    @Override
    public List<Product> SearchProductByName(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

    @Override
    public List<Product> SearchProductByBrand(String keyword) {
        return productRepository.findByBrandNameContaining(keyword);
    }

    @Override
    public List<Product> SearchProductByCategory(String keyword) {
        return productRepository.findByCategoryNameContaining(keyword);
    }


}
