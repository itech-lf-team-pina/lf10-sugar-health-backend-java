package com.health.sugar.lf10sugarhealth.repository;

import com.health.sugar.lf10sugarhealth.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> searchByCategoryOrCategoryGermanOrDescriptionOrDescriptionGerman(String category, String categoryGerman, String description, String descriptionGerman, Pageable pageable);
}
