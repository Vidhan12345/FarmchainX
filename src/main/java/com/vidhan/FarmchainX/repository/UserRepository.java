package com.vidhan.FarmchainX.repository;

import com.vidhan.FarmchainX.entity.User;
import com.vidhan.FarmchainX.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    List<User> findByRole(UserRole role);

    List<User> findByVerified(Boolean verified);
}
