package com.mobily.loyalty.service.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.mobily.loyalty.service.constants.APIConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * Configures the security for resource access
 * @author ACLDigital
 *
 */
@SuppressWarnings("deprecation")
/*
 * @Configuration
 * 
 * @EnableWebSecurity
 * 
 * @EnableGlobalMethodSecurity(jsr250Enabled = true)
 */
@KeycloakConfiguration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Import(KeycloakSpringBootConfigResolver.class)
@Slf4j
public class GlobalSecurityConfiguration {
	
	/** Key Cloak JWT Token Converter **/
    private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;
  

    /**
     * default constructor
     * @param properties	
     */
    public GlobalSecurityConfiguration(TokenConverterProperties properties) {
    	log.info("Setting Security Configuration, Global Security");
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter= new JwtGrantedAuthoritiesConverter(); 
        this.keycloakJwtTokenConverter
                = new KeycloakJwtTokenConverter(
                        jwtGrantedAuthoritiesConverter,
                        properties); 
    	log.info("Successfully Set Security Configuration, Global Security");
    }

    /**
     * Provides the security filter chain
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	log.info("Setting Security Configuration, Global Security ");
    	 return http.csrf().disable().authorizeHttpRequests(customizer ->
	  		customizer.requestMatchers(APIConstants.PUBLIC_RESOURCES_URL).permitAll()
			  .requestMatchers(APIConstants.LOYALTY_SERVICE_RESOURCES_CONTEXT).authenticated()).oauth2ResourceServer(customizer ->
	  		customizer.jwt(jwtCustomizer ->
	  		jwtCustomizer.jwtAuthenticationConverter(keycloakJwtTokenConverter)))
			  .sessionManagement(customizer ->
			  	customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) .build();
    }
}