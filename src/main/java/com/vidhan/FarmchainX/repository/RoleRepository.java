package com.vidhan.FarmchainX.repository;

import com.vidhan.FarmchainX.entity.ERole;
import com.vidhan.FarmchainX.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole roleName);
}
