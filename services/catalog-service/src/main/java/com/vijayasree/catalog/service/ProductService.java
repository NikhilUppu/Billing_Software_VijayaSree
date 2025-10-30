package com.vijayasree.catalog.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.vijayasree.catalog.domain.Product;
import com.vijayasree.catalog.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * ProductService: provides cached access to product data while persisting to DB.
 */
@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final Cache<String, Product> productCache = Caffeine.newBuilder()
			.maximumSize(10_000)
			.expireAfterWrite(Duration.ofMinutes(30))
			.build();

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	/**
	 * listAll: returns all products; for large catalogs, add pagination later.
	 */
	public List<Product> listAll() {
		return productRepository.findAll();
	}

	/**
	 * findById: cached lookup by id.
	 */
	public Optional<Product> findById(String id) {
		Product cached = productCache.getIfPresent(id);
		if (cached != null) return Optional.of(cached);
		Optional<Product> p = productRepository.findById(id);
		p.ifPresent(v -> productCache.put(id, v));
		return p;
	}

	/**
	 * save: persists product and updates cache.
	 */
	public Product save(Product p) {
		Product saved = productRepository.save(p);
		productCache.put(saved.getId(), saved);
		return saved;
	}

	/**
	 * delete: removes product and cache entry.
	 */
	public void delete(String id) {
		productRepository.deleteById(id);
		productCache.invalidate(id);
	}
}
