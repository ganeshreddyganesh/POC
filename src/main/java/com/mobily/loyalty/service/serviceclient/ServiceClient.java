package com.mobily.loyalty.service.serviceclient;

import org.apache.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mobily.loyalty.service.constants.APIConstants;
import com.mobily.loyalty.service.constants.ServiceConstants;
import com.mobily.loyalty.service.exceptions.InternalServerException;

import lombok.extern.slf4j.Slf4j;


/**
 * This class defines the methods to consumes the services like Data Power Service
 * @author Mobily Info Tech (MIT)
 *
 */
@Service
@Slf4j
public class ServiceClient {
	/**
	 * consumes the service method
	 * @param serviceURL
	 * @param httpMethod
	 * @param requestPayload
	 * @param httpHeaders
	 * @return
	 * @throws Exception
	 */
	public ServiceResponse execute(String serviceURL,HttpMethod httpMethod, String requestPayload,HttpHeaders httpHeaders) throws HttpClientErrorException,Exception {
		ServiceResponse serviceResponse = null;
		
		try {
			HttpEntity<String> request = new HttpEntity<String>(requestPayload,httpHeaders);
			
		    ResponseEntity<String> responseEntity = getRestTemplate().exchange(serviceURL, httpMethod, request, String.class); 
		   
		    if(ObjectUtils.isEmpty(responseEntity)) {
		    	 throw new InternalServerException("","Error occurred while consuming the service ");
		     }
		 	serviceResponse = new ServiceResponse();
			serviceResponse.setHttpStatusCode(responseEntity.getStatusCode().value());
			serviceResponse.setResponsePayload(responseEntity.getBody());
		}catch(HttpClientErrorException httpClientErrorException) {
			serviceResponse = new ServiceResponse();
			serviceResponse.setHttpStatusCode(httpClientErrorException.getRawStatusCode());
			return serviceResponse;
		}catch(Exception exception) {
			serviceResponse = new ServiceResponse();
			serviceResponse.setHttpStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			return serviceResponse;
		}
		return serviceResponse;
	}
	
	/**
	 * returns the RESTTemplate
	 * @return
	 */
	private RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
