package com.sky.ecommerce.controller;

import com.sky.ecommerce.exception.BrandException;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.Brand;
import com.sky.ecommerce.model.Role;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.response.ApiResponse;
import com.sky.ecommerce.service.BrandService;
import com.sky.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/brands")
@RequiredArgsConstructor
public class AdminBrandController {
    private final BrandService brandService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Brand> createBrand(@RequestBody Brand req, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Brand brand = brandService.createBrand(req);

        return new ResponseEntity<>(brand, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Brand>> findAllBrands() {
        List<Brand> brands = brandService.findAllBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @PutMapping("/{brandId}/update")
    public ResponseEntity<Brand> updateBrand(@PathVariable String brandId, @RequestBody Brand req, @RequestHeader("Authorization") String jwt) throws UserException, BrandException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Brand brand = brandService.updateBrand(brandId, req);

        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @DeleteMapping("/{brandId}/delete")
    public ResponseEntity<ApiResponse> deleteBrand(@PathVariable String brandId, @RequestHeader("Authorization") String jwt) throws UserException, BrandException {
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != Role.ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        brandService.deleteBrand(brandId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Brand deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
