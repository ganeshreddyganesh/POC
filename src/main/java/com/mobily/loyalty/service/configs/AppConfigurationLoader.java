package com.mobily.loyalty.service.configs;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobily.loyalty.service.exceptions.ConfigItemNotFoundException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
/**
 * Applicatiopn configuration
 * @author Mobily Info Tech (MIT)
 *
 */
@Component
@Slf4j
@Getter
@Setter
public class AppConfigurationLoader {
	
	/** Key Cloak authentication end point **/
    @Value("${keycloak.auth.service.endpoint.url}")
    private String userAuthSrvcURL;
    
    /** Key Cloak authentication grant type end point**/
    @Value("${keycloak.auth.service.granttype}")
    private String secTknGrntType;
    
    /** Loyalty Information Inquiry Endpoint **/
    @Value("${loyalty.inquiry.service.url}")
    private String loyaltyInquiryDPURL;;
    
    
   /**
    * default constructor
    * @throws Exception
    */
    public AppConfigurationLoader() throws Exception{
    	
    }
    
	/**
	 * Loads the configuration details on application startup
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {	   
	}
	
	/**
	 * returns the configuration item
	 * @param key
	 * @return
	 * @throws ConfigItemNotFoundException
	 */
	public String getConfigItem(String key) throws ConfigItemNotFoundException{
		return "";
	}
}