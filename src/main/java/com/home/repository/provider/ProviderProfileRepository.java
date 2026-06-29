package com.home.repository.provider;

import com.home.model.provider.ProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Integer> {
}
