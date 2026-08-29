package com.home.service.provider;

import com.home.dto.provider.ProviderProfileResponse;
import com.home.dto.provider.ProviderProfileUpdateRequest;
import com.home.dto.provider.ProviderSkillsRequest;
import com.home.dto.provider.ProviderSkillsResponse;
import com.home.model.auth.User;
import com.home.model.catalog.Category;
import com.home.model.provider.ProviderProfile;
import com.home.model.provider.ProviderSkill;
import com.home.repository.catalog.CategoryRepository;
import com.home.repository.provider.ProviderProfileRepository;
import com.home.repository.provider.ProviderSkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderService {

    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    @Autowired
    private ProviderSkillRepository providerSkillRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public ProviderProfileResponse getProviderProfile(String email){
        ProviderProfile providerProfile = providerProfileRepository.findByUserEmail(email).orElseThrow(()-> new RuntimeException("\"Provider profile not found for email: \" + email"));

        ProviderProfileResponse response = new ProviderProfileResponse();
        response.setProviderId(providerProfile.getProviderId());
        response.setEmail(providerProfile.getUser().getEmail());
        response.setFirstName(providerProfile.getFirstName());
        response.setLastName(providerProfile.getLastName());
        response.setShopDetails(providerProfile.getShopDetails());
        response.setPhoneNumber(providerProfile.getPhoneNumber());
        response.setIsAvailable(providerProfile.getIsAvailable());

        return response;
    }

    @Transactional
    public ProviderProfileResponse updateProviderProfile(String email, ProviderProfileUpdateRequest request){
        ProviderProfile profile = providerProfileRepository.findByUserEmail(email).orElseThrow(()-> new RuntimeException("Provider not found"));

        // Update profile fields
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setShopName(request.getShopName());
        profile.setShopDetails(request.getShopDetails());
        profile.setIsAvailable(request.getIsAvailable());

        ProviderProfile updatedProfile = providerProfileRepository.save(profile);

        ProviderProfileResponse response = new ProviderProfileResponse();
        response.setProviderId(updatedProfile.getProviderId());
        response.setFirstName(updatedProfile.getFirstName());
        response.setLastName(updatedProfile.getLastName());
        response.setPhoneNumber(updatedProfile.getPhoneNumber());
        response.setShopName(updatedProfile.getShopName());
        response.setShopDetails(updatedProfile.getShopDetails());
        response.setIsAvailable(updatedProfile.getIsAvailable());
        response.setEmail(updatedProfile.getUser().getEmail());
        return response;
    }

    @Transactional
    public ProviderSkillsResponse addProviderSkill(String email, ProviderSkillsRequest request) {

        ProviderProfile profile = providerProfileRepository.findByUserEmail(email).orElseThrow(()-> new RuntimeException("No provider found for this email "+email));

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new RuntimeException("Category not found with ID "+ request.getCategoryId()));

        if(providerSkillRepository.existsByProviderProfile_ProviderIdAndCategory_CategoryId(profile.getProviderId(), request.getCategoryId())){
            throw new RuntimeException("Skill already registered for this provider");
        }

        ProviderSkill skill = new ProviderSkill();
        skill.setProviderProfile(profile);
        skill.setCategory(category);
        com.home.model.provider.ProviderSkill savedSkill = providerSkillRepository.save(skill);

        ProviderSkillsResponse response = new ProviderSkillsResponse();
        response.setId(savedSkill.getId());
        response.setProviderId(profile.getProviderId());
        response.setCategoryId(category.getCategoryId());
        response.setCategoryName(category.getName());
        return response;
    }

    public List<ProviderSkillsResponse> getProviderSkills(String email) {
        ProviderProfile providerProfile = providerProfileRepository.findByUserEmail(email).orElseThrow(()-> new RuntimeException("No user found for email "+ email));

        List<ProviderSkill> skills =  providerSkillRepository.findByProviderProfile_ProviderId(providerProfile.getProviderId());

        return skills.stream().map(skill->{
            ProviderSkillsResponse providerSkillsResponse = new ProviderSkillsResponse();
            providerSkillsResponse.setId(skill.getId());
            providerSkillsResponse.setCategoryId(skill.getCategory().getCategoryId());
            providerSkillsResponse.setCategoryName(skill.getCategory().getName());
            providerSkillsResponse.setProviderId(skill.getProviderProfile().getProviderId());
            return providerSkillsResponse;
        }).collect(Collectors.toList());
    }
}
