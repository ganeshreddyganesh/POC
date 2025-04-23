package com.mobily.loyalty.service.services;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobily.loyalty.service.constants.ServiceConstants;
import com.mobily.loyalty.service.model.MasterResponse;
import com.mobily.loyalty.service.model.Status;

/**
 * This class is responsible for building the service response
 * @author Mobily Info Tech (MIT)
 *
 */
@Component
public class ServiceResponseBuilder {
	/**
	 * builds service response
	 * @param status
	 * @param description
	 * @param data
	 * @return
	 */
	public String build(Status status, Object data) throws Exception{
		MasterResponse masterResponse = new MasterResponse();
		masterResponse.setStatus(status);		
		masterResponse.setData(data);
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(masterResponse);
	}	
	
	/**
	 * returns success message
	 * @return
	 */
	public Status buildSuccessStatus() {
		Status successStatus = new Status();
		successStatus.setCode(ServiceConstants.AUTHORIZATION);
		successStatus.setMessageAr("");
		successStatus.setMessageEn("");
		successStatus.setSourceCode("");
		successStatus.setSourceMessage("");
		return successStatus;
	}
}
