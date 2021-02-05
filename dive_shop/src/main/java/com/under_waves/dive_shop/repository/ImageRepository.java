package com.under_waves.dive_shop.repository;

import com.under_waves.dive_shop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findById(Long id);

    Optional<Image> findByName(String name);

    void deleteById(Long id);

    void deleteByName(String name);

}
