package com.file_sharing.app.repositories;

import com.file_sharing.app.entity.FileEntity;
import com.file_sharing.app.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {
    Page<FileEntity> findAllByUploadBy(UserEntity user, Pageable pageable);
    Page<FileEntity> findByFileNameContaining(String fileName, Pageable pageable);
    List<FileEntity> findAllByExpiryDateBefore(Instant instant);
}
