package com.file_sharing.app.repositories;

import com.file_sharing.app.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    Page<UserEntity>  findByNameContaining(String keyword, Pageable pageable);
    Optional<UserEntity> findByEmailAndPassword(String email,String password);

}
