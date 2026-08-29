package com.home.controller.provider;

import com.home.dto.booking.ProviderBookingResponse;
import com.home.dto.provider.*;
import com.home.dto.provider.ProviderSkillsResponse;
import com.home.service.provider.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @GetMapping("/profile")
    public ProviderProfileResponse getProviderProfile(Principal principal) {
        return providerService.getProviderProfile(principal.getName());
    }

    @PutMapping("/update/profile")
    public ProviderProfileResponse updateProfile(Principal principal,
            @RequestBody ProviderProfileUpdateRequest request) {
        return providerService.updateProviderProfile(principal.getName(), request);
    }

    @PostMapping("/add/skills")
    public ProviderSkillsResponse addSkills(Principal principal, @RequestBody ProviderSkillsRequest request) {
        return providerService.addProviderSkill(principal.getName(), request);
    }

    @GetMapping("/skills")
    public List<ProviderSkillsResponse> getProviderSkill(Principal principal) {
        return providerService.getProviderSkills(principal.getName());
    }
}
