package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.BrandException;
import com.sky.ecommerce.model.Brand;
import com.sky.ecommerce.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService{
    private final BrandRepository brandRepository;
    @Override
    public Brand createBrand(Brand req) {
        return brandRepository.save(req);
    }

    @Override
    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand updateBrand(String brandId, Brand req) throws BrandException {
        Brand existingbrand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandException("Brand not found with id: "+ brandId));
        if (req.getName()!=null){
            existingbrand.setName(req.getName());
        }
        if (req.getDescription()!=null){
            existingbrand.setDescription(req.getDescription());
        }
        if (req.getImageUrl()!=null){
            existingbrand.setImageUrl(req.getImageUrl());
        }
        return brandRepository.save(existingbrand);
    }

    @Override
    public void deleteBrand(String brandId) throws BrandException {
        Optional<Brand> brandToDelete = brandRepository.findById(brandId);
        if (brandToDelete.isPresent()) {
            brandRepository.delete(brandToDelete.get());
        } else {
            throw new BrandException("Brand not found");
        }
    }
}
