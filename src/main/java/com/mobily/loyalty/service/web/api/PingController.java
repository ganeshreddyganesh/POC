package com.mobily.loyalty.service.web.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mobily.loyalty.service.constants.APIConstants;
import com.mobily.loyalty.service.serviceclient.ServiceClient;
import com.mobily.loyalty.service.serviceclient.ServiceResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Ping Service, to generally test application running status
 * @author ACLDigital
 *
 */
@RestController
@RequestMapping(APIConstants.PUBLIC_CONTEXT)
@Slf4j
public class PingController {
	
	@Autowired
	private ServiceClient serviceClient;
    /**
     * Ping service
     * @return
     */
	@GetMapping(value=APIConstants.PING_URL)
	public String ping() {
		log.info(" Successfully hit loyality services ping service");	
		return "Successfully running Loyalty service";
	}
	
}