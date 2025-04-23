package com.mobily.loyalty.service.configs;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import lombok.extern.slf4j.Slf4j;


/**
 * KeyCloak JWT converter
 * @author ACLDigital
 *
 */
@Slf4j
public class KeycloakJwtTokenConverter implements Converter<Jwt, JwtAuthenticationToken> {	
	/** resource access **/
    private static final String RESOURCE_ACCESS = "resource_access";
    /** roles **/
    private static final String ROLES = "roles";
    /**role prefix **/
    private static final String ROLE_PREFIX = "ROLE_";
    /** JWT token converter **/
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
    
    /** token properties **/
    private final TokenConverterProperties properties;

    /**
     * constructor
     * @param jwtGrantedAuthoritiesConverter
     * @param properties
     */
    public KeycloakJwtTokenConverter(
            JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter,
            TokenConverterProperties properties) {
        this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
        this.properties = properties;
    }

    /**
     * Converts the JWT token
     */
    @Override
    public JwtAuthenticationToken convert(@NonNull Jwt jwt) {
    	log.info(" Converting JWT Token ");
        Stream<SimpleGrantedAuthority> accesses = Optional.of(jwt)
                .map(token -> token.getClaimAsMap(RESOURCE_ACCESS))
                .map(claimMap -> (Map<String, Object>) claimMap.get(properties.getResourceId()))
                .map(resourceData -> (Collection<String>) resourceData.get(ROLES))
                .stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                .distinct();
        Set<GrantedAuthority> authorities = Stream
                .concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(), accesses)
                .collect(Collectors.toSet());
        String principalClaimName = properties.getPrincipalAttribute()
                .map(jwt::getClaimAsString)
                .orElse(jwt.getClaimAsString(JwtClaimNames.SUB));
    	log.info(" Successful;ly Converted JWT Token ");
        return new JwtAuthenticationToken(jwt, authorities, principalClaimName);
    }
}