package com.under_waves.dive_shop.repository;

import com.under_waves.dive_shop.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    Optional<ProductType> findById(Long id);

    Optional<ProductType> findByType(String type);

    Optional<ProductType> deleteByType(String type);
}
