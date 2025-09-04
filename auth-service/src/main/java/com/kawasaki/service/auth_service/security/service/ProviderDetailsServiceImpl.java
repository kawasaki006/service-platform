package com.kawasaki.service.auth_service.security.service;

import com.kawasaki.service.auth_service.dto.ProviderDTO;
import com.kawasaki.service.auth_service.feign.ProviderFeignService;
import com.kawasaki.service.common.constants.AuthConstants;
import com.kawasaki.service.common.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ProviderDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ProviderFeignService providerFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ProviderDTO providerDTO = providerFeignService.findProviderByEmail(username).getData();
        if  (Objects.isNull(providerDTO)) {
            throw new UsernameNotFoundException("Email not found in provider service");
        }

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(AuthConstants.PROVIDER)
        );

        return new UserDetailsImpl(
                providerDTO.getId(), providerDTO.getEmail(), providerDTO.getPasswordHash(), authorities);
    }
}
