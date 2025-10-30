package com.vijayasree.catalog.domain;

import jakarta.persistence.*;

/**
 * Company: brand/manufacturer for products.
 */
@Entity
@Table(name = "company")
public class Company {
	@Id
	@Column(name = "id", nullable = false, length = 50)
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "is_active", nullable = false)
	private boolean active = true;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
}
