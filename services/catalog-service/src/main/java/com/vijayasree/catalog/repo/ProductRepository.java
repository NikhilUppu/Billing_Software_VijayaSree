package com.vijayasree.catalog.repo;

import com.vijayasree.catalog.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ProductRepository: data access for products.
 */
public interface ProductRepository extends JpaRepository<Product, String> {
	List<Product> findByCompany_Id(String companyId);
}
