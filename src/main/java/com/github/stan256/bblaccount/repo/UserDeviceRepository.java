package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.entity.RefreshToken;
import com.github.stan256.bblaccount.model.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    @Override
    Optional<UserDevice> findById(Long id);

    Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken);

    List<UserDevice> findAllByUserId(Long userId);
}
