package com.vijayasree.catalog.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Product: sellable item with company association and size/volume.
 */
@Entity
@Table(name = "product")
public class Product {
	@Id
	@Column(name = "id", nullable = false, length = 60)
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "volume")
	private String volume; // e.g., 100 ml, 1 L

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "image_url")
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getVolume() { return volume; }
	public void setVolume(String volume) { this.volume = volume; }
	public BigDecimal getPrice() { return price; }
	public void setPrice(BigDecimal price) { this.price = price; }
	public String getImageUrl() { return imageUrl; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
	public Company getCompany() { return company; }
	public void setCompany(Company company) { this.company = company; }
}
