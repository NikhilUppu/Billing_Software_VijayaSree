package com.vijayasree.catalog.domain;

import jakarta.persistence.*;

/**
 * UserAccount: application user with role-based access.
 */
@Entity
@Table(name = "user_account")
public class UserAccount {
	@Id
	@Column(name = "id", nullable = false, length = 50)
	private String id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "mobile", length = 10, unique = true)
	private String mobile; // 10-digit mobile number used for login

	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(name = "role", nullable = false, length = 20)
	private String role; // ADMIN, MANAGER, ACCOUNTANT, CASHIER

	@Column(name = "is_active", nullable = false)
	private boolean active = true;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company; // optional association

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getMobile() { return mobile; }
	public void setMobile(String mobile) { this.mobile = mobile; }
	public String getPasswordHash() { return passwordHash; }
	public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
	public Company getCompany() { return company; }
	public void setCompany(Company company) { this.company = company; }
}
