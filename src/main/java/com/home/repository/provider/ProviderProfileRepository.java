package com.home.repository.provider;

import com.home.model.auth.User;
import com.home.model.provider.ProviderProfile;
import com.home.model.provider.ProviderSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Integer> {
    Optional<ProviderProfile> findByUserEmail(String email);
}
