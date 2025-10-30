package com.vijayasree.catalog.repo;

import com.vijayasree.catalog.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CompanyRepository: data access for companies.
 */
public interface CompanyRepository extends JpaRepository<Company, String> {
}
