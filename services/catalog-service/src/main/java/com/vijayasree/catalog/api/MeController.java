package com.vijayasree.catalog.api;

import com.vijayasree.catalog.domain.UserAccount;
import com.vijayasree.catalog.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/me")
public class MeController {
	private final UserService userService;
	public MeController(UserService userService) { this.userService = userService; }

	@GetMapping
	public Map<String, Object> me(Authentication auth) {
		Map<String, Object> res = new HashMap<>();
		res.put("username", auth.getName());
		res.put("roles", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		userService.findByUsername(auth.getName()).ifPresent(u -> res.put("profile", mapUser(u)));
		return res;
	}

	private Map<String, Object> mapUser(UserAccount u) {
		Map<String, Object> m = new HashMap<>();
		m.put("id", u.getId());
		m.put("mobile", u.getMobile());
		m.put("role", u.getRole());
		if (u.getCompany() != null) {
			Map<String,Object> c = new HashMap<>();
			c.put("id", u.getCompany().getId());
			c.put("name", u.getCompany().getName());
			m.put("company", c);
		}
		return m;
	}
}
