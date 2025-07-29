package com.kawasaki.service.auth_service.security.service;

import com.kawasaki.service.auth_service.dto.UserDTO;
import com.kawasaki.service.auth_service.feign.UserFeignService;
import com.kawasaki.service.auth_service.provider.InternalTokenProvider;
import com.kawasaki.service.auth_service.security.config.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserFeignService userFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userFeignService.findUserByEmail(username).getData();

        // todo: change hard coded authorities
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ADMIN")
        );

        return new UserDetailsImpl(
                userDTO.getId(), userDTO.getEmail(), userDTO.getPasswordHash(), authorities);
    }
}
