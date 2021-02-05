package com.under_waves.dive_shop.repository;

import com.under_waves.dive_shop.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Optional<Manufacturer> findById(Long id);

    Optional<Manufacturer> findByName(String name);

    void deleteById(Long id);

    void deleteByName(String name);
}
