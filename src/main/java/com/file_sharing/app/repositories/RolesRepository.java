package com.file_sharing.app.repositories;

import com.file_sharing.app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role,String> {
    Optional<Role> findByRoleName(String name);
}
