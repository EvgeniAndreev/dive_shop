package com.under_waves.dive_shop.repository;

import com.under_waves.dive_shop.model.ManufacturerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturerInfoRepository extends JpaRepository<ManufacturerInfo, Long> {

    Optional<ManufacturerInfo> findById(Long id);

    Optional<ManufacturerInfo> findByPhone(String phone);

    Optional<ManufacturerInfo> findByEmail(String email);

    void deleteById(Long id);

    void deleteByPhone(String phone);

    void deleteByEmail(String email);
}
