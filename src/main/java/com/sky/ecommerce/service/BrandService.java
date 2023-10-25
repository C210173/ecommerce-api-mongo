package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.BrandException;
import com.sky.ecommerce.model.Brand;

import java.util.List;

public interface BrandService {
    Brand createBrand(Brand req);

    List<Brand> findAllBrands();

    Brand updateBrand(String brandId, Brand req) throws BrandException;

    void deleteBrand(String brandId) throws BrandException;
}
