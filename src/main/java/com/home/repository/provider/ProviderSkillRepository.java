package com.home.repository.provider;

import com.home.model.provider.ProviderSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderSkillRepository extends JpaRepository<ProviderSkill, Integer> {

    // Check if provider has a skill/category
    boolean existsByProviderProfile_ProviderIdAndCategory_CategoryId(Integer providerId, Integer categoryId);

    // List all skills for a provider
    List<ProviderSkill> findByProviderProfile_ProviderId(Integer providerId);
}
