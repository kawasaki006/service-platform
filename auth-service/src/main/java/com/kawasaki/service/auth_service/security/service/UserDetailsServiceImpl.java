package com.kawasaki.service.auth_service.security.service;

import com.kawasaki.service.auth_service.dto.UserDTO;
import com.kawasaki.service.auth_service.feign.UserFeignService;
import com.kawasaki.service.auth_service.provider.InternalTokenProvider;
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
import java.util.Objects;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserFeignService userFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userFeignService.findUserByEmail(username).getData();
        if (Objects.isNull(userDTO)) {
            throw new UsernameNotFoundException("Email not found in user service");
        }

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("USER")
        );

        // todo: temp admin authorization, to be deleted later
        if ("admin@poyo.com".equals(username)) {
            authorities = List.of(new SimpleGrantedAuthority("ADMIN"));
        }

        return new UserDetailsImpl(
                userDTO.getId(), userDTO.getEmail(), userDTO.getPasswordHash(), authorities);
    }
}
