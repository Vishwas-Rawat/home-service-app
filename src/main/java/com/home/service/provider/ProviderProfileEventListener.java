package com.home.service.provider;

import com.home.event.auth.UserRegisteredEvent;
import com.home.model.provider.ProviderProfile;
import com.home.repository.provider.ProviderProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ProviderProfileEventListener {
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    @EventListener
    public void providerEventListener(UserRegisteredEvent userRegisteredEvent) {
        if ("PROVIDER".equalsIgnoreCase(userRegisteredEvent.getUser().getRole().getName())) {
            ProviderProfile providerProfile = new ProviderProfile();

            // 1. Link the profile to the registered User (Mandatory OneToOne relationship)
            providerProfile.setUser(userRegisteredEvent.getUser());

            // 2. Set registration values
            providerProfile.setFirstName(userRegisteredEvent.getUser().getName());
            providerProfile.setLastName("");
            providerProfile.setPhoneNumber("");

            // 3. Set shop defaults (these are nullable in DB and can be filled in later by the user)
            providerProfile.setShopName(null);
            providerProfile.setShopDetails(null);

            // 4. Set availability
            providerProfile.setIsAvailable(true);

            providerProfileRepository.save(providerProfile);
        }
    }
}
