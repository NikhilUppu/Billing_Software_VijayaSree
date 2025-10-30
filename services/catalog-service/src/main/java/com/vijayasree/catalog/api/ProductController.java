package com.vijayasree.catalog.api;

import com.vijayasree.catalog.domain.Product;
import com.vijayasree.catalog.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ProductController: CRUD endpoints guarded by roles.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	/** list: anyone authenticated can read products. */
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','ACCOUNTANT','CASHIER')")
	public List<Product> list() {
		return productService.listAll();
	}

	/** get: read single product. */
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','ACCOUNTANT','CASHIER')")
	public ResponseEntity<Product> get(@PathVariable String id) {
		Optional<Product> p = productService.findById(id);
		return p.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	/** create: restricted to ADMIN/MANAGER/ACCOUNTANT. */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','ACCOUNTANT')")
	public Product create(@RequestBody Product p) {
		return productService.save(p);
	}

	/** update: restricted to ADMIN/MANAGER/ACCOUNTANT. */
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','ACCOUNTANT')")
	public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product p) {
		p.setId(id);
		return ResponseEntity.ok(productService.save(p));
	}

	/** delete: restricted to ADMIN/MANAGER. */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
