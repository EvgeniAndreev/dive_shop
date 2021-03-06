package com.under_waves.dive_shop.repository;

import com.under_waves.dive_shop.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT e * FROM Employee e WHERE e.email = ?1")
    Optional<Employee> findByEmail(String email);

    @Query(value = "SELECT * FROM employees WHERE ucn = ?1", nativeQuery = true)
    Optional<Employee> findByUcn(String phoneNumber);

    void deleteByUcn(String ucn);

    void deleteByEmail(String email);
}
