package com.file_sharing.app.repositories;

import com.file_sharing.app.entity.RefreshToken;
import com.file_sharing.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByRefreshTokenHold(String refreshTokenHold);
    Optional<RefreshToken> findByUser(UserEntity user);
    List<RefreshToken> findByExpiresDateBefore(Instant timestamp);
}
