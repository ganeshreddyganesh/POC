package com.mobily.loyalty.service.configs;

import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * defines the token converter properties
 * @author ACLDigital
 *
 */
@Configuration
@ConfigurationProperties(prefix = "token.converter")
@Slf4j
@Getter
@Setter
public class TokenConverterProperties {
	   
	/** resource id **/
    private String resourceId;
    
    /** principal attributes **/
    private String principalAttribute;

    /**
     * returns resource id
     * @return
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * returns principal attribute
     * @return
     */
    public Optional<String> getPrincipalAttribute() {
        return Optional.ofNullable(principalAttribute);
    }

    /**
     * sets resource id
     * @param resourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * sets the principal attribute
     * @param principalAttribute
     */
    public void setPrincipalAttribute(String principalAttribute) {
        this.principalAttribute = principalAttribute;
    }
}