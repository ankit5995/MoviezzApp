package com.niit.bej.UserAuthService.repository;

import com.niit.bej.UserAuthService.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
