package com.kawasaki.service.auth_service.security.service;

import com.kawasaki.service.auth_service.dto.ProviderDTO;
import com.kawasaki.service.auth_service.feign.ProviderFeignService;
import com.kawasaki.service.auth_service.security.config.UserDetailsImpl;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProviderDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ProviderFeignService providerFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            ProviderDTO providerDTO = providerFeignService.findProviderByEmail(username).getData();

            List<GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("PROVIDER")
            );

            return new UserDetailsImpl(
                    providerDTO.getId(), providerDTO.getEmail(), providerDTO.getPasswordHash(), authorities);
        } catch (FeignException fe) {
            throw new UsernameNotFoundException("Email not found in provider service", fe);
        }
    }
}
