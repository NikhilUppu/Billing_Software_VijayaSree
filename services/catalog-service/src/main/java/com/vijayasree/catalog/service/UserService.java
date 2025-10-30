package com.vijayasree.catalog.service;

import com.vijayasree.catalog.domain.Company;
import com.vijayasree.catalog.domain.UserAccount;
import com.vijayasree.catalog.repo.CompanyRepository;
import com.vijayasree.catalog.repo.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * UserService: manage users and encode passwords.
 */
@Service
public class UserService {
	private final UserAccountRepository userRepo;
	private final CompanyRepository companyRepo;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserAccountRepository userRepo, CompanyRepository companyRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.companyRepo = companyRepo;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UserAccount> listAll() { return userRepo.findAll(); }

	public Optional<UserAccount> findByUsername(String username) { return userRepo.findByUsername(username); }

	public UserAccount createUser(String username, String rawPassword, String role, String mobile, String companyId) {
		if (mobile != null && !mobile.isBlank() && !mobile.matches("^[0-9]{10}$")) {
			throw new IllegalArgumentException("mobile must be 10 digits");
		}
		UserAccount user = new UserAccount();
		user.setId(UUID.randomUUID().toString());
		user.setUsername(username);
		user.setMobile(mobile);
		user.setPasswordHash(passwordEncoder.encode(rawPassword));
		user.setRole(role);
		user.setActive(true);
		if (companyId != null && !companyId.isBlank()) {
			Company company = companyRepo.findById(companyId)
				.orElseThrow(() -> new IllegalArgumentException("company not found: " + companyId));
			user.setCompany(company);
		}
		return userRepo.save(user);
	}

	public void deleteUser(String id) {
		userRepo.deleteById(id);
	}
}
