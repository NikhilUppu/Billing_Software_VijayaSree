package com.vijayasree.catalog.api;

import com.vijayasree.catalog.domain.UserAccount;
import com.vijayasree.catalog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * UserController: admin-only endpoints to manage users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	/** list: ADMIN only */
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserAccount> list() {
		return userService.listAll();
	}

	/** create: ADMIN only. Body: { username, password, role, mobile, companyId } */
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestBody Map<String, String> body) {
		String username = body.getOrDefault("username", "");
		String password = body.getOrDefault("password", "");
		String role = body.getOrDefault("role", "CASHIER");
		String mobile = body.get("mobile");
		String companyId = body.get("companyId");
		if (username.isBlank() || password.isBlank()) {
			return ResponseEntity.badRequest().body("username and password are required");
		}
		try {
			UserAccount u = userService.createUser(username, password, role, mobile, companyId);
			return ResponseEntity.ok(u);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	/** delete: ADMIN only. */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
