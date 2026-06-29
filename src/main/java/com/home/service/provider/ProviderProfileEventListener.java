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
    public void providerEventListener(UserRegisteredEvent userRegisteredEvent){
        if("PROVIDER".equalsIgnoreCase(userRegisteredEvent.getUser().getRole().getName())){
            ProviderProfile providerProfile = new ProviderProfile();
            providerProfile.setFirstName(providerProfile.getFirstName());
            providerProfile.setLastName(providerProfile.getLastName());
            providerProfile.setPhoneNumber(providerProfile.getPhoneNumber());
            providerProfile.setShopName(providerProfile.getShopName());
            providerProfile.setShopDetails(providerProfile.getShopDetails());
            providerProfile.setIsAvailable(true);
            providerProfileRepository.save(providerProfile);
        }
    }
}
