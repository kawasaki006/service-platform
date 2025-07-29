package com.kawasaki.service.user_service.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class ResourceServerSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(authorize -> authorize
                //.requestMatchers("/user/**").permitAll()
                .anyRequest().authenticated()
        );

        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );

        return http.build();
    }

//    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
//        converter.setAuthorityPrefix("ROLE_");
//        converter.setAuthoritiesClaimName("role");
//        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
//        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
//        return jwtConverter;
//    }
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return new Converter<Jwt, AbstractAuthenticationToken>() {
            @Override
            public AbstractAuthenticationToken convert(Jwt jwt) {
                Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
                return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
            }

            private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
                String type = jwt.getClaimAsString("token_type");

                if ("internal".equals(type)) {
                    return extractInternalAuthorities(jwt);
                } else {
                    return extractClientAuthorities(jwt);
                }
            }

            private Collection<GrantedAuthority> extractInternalAuthorities(Jwt jwt) {
                Collection<GrantedAuthority> authorities = new ArrayList<>();

                List<String> scopes = jwt.getClaimAsStringList("scopes");
                if (scopes != null) {
                    scopes.stream().forEach(scope -> {
                        authorities.add(new SimpleGrantedAuthority(scope));
                    });
                }
                authorities.add(new SimpleGrantedAuthority("ROLE_INTERNAL"));

                return authorities;
            }

            private Collection<GrantedAuthority> extractClientAuthorities(Jwt jwt) {
                Collection<GrantedAuthority> authorities = new ArrayList<>();

                List<String> roles = jwt.getClaimAsStringList("roles");
                if (roles != null) {
                    roles.stream().forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                    });
                }

                return authorities;
            }
        };
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String secretKey = "poyopoyopoyopoyopoyopoyopoyopoyo";  // 要与生成 token 时一致
        return NimbusJwtDecoder.withSecretKey(
                new javax.crypto.spec.SecretKeySpec(
                        secretKey.getBytes(), "HmacSHA256"
                )
        ).build();
    }
}
