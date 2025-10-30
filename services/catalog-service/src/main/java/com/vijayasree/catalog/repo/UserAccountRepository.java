package com.vijayasree.catalog.repo;

import com.vijayasree.catalog.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserAccountRepository: persistence for users.
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
	Optional<UserAccount> findByUsername(String username);
}
