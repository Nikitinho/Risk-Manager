package com.nikitinho.riskmanager.repo;

import com.nikitinho.riskmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}
