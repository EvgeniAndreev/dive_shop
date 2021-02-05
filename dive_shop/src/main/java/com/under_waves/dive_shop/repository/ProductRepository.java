package com.under_waves.dive_shop.repository;

import com.under_waves.dive_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Optional<Product> findByBarCode(String barCode);

    void deleteByBarCode(String barCode);
}
