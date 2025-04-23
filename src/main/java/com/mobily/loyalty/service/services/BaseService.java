package com.mobily.loyalty.service.services;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobily.loyalty.service.configs.AppConfigurationLoader;
import com.mobily.loyalty.service.constants.APIConstants;
import com.mobily.loyalty.service.constants.MessageConstants;
import com.mobily.loyalty.service.constants.ServiceConstants;
import com.mobily.loyalty.service.exceptions.InternalServerException;
import com.mobily.loyalty.service.exceptions.InvalidRequestException;
import com.networknt.schema.ValidationMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * This provides the methods common to all services
 * @author Mobily Info Tech (MIT)
 *
 */
@Service
@Slf4j
public class BaseService {

	@Autowired
	private DataValidationService requestDataValidationService;
	
	/** Application Configuration **/
	@Autowired
	private AppConfigurationLoader appConfigurationLoader;
		
	/** Object Mapper **/
	private ObjectMapper objectMapper;
		
	public BaseService() {
		this.objectMapper = new ObjectMapper();
	}
			
	/**
	 * validates the request
	 * @param requestData
	 * @param requestValidationSchema
	 */
	public void validate(String journey,Object requestData,Map<String,String> mapHeaders) throws InternalServerException, InvalidRequestException{
		validateHeaders(journey,mapHeaders);
		validateRequestData(journey,requestData);
	}
	
	/**
	 * validates the request data
	 * @param requestValidationSchema
	 * @param requestData
	 */
	private void validateRequestData(String journey,Object requestData) throws InternalServerException,InvalidRequestException {
		Set<ValidationMessage> validationErrors = null;	
		InvalidRequestException invalidRequestException = null;
		String validationErrorMessage = "";
		
		try {
			
			validationErrors = requestDataValidationService.validateJson(objectMapper.readTree(objectMapper.writeValueAsBytes(requestData)), journey+"-"+MessageConstants.REQUEST_DATA+"-"+ServiceConstants.SCHEMA_KEY);
			
			if(!ObjectUtils.isEmpty(validationErrors)) {
				validationErrors.stream().forEach(validationError->System.out.println(" Data validation <<Error>>: "+validationError.getMessage()));	
				validationErrorMessage = String.valueOf(validationErrors.toArray()[0]).startsWith(MessageConstants.VALIDATION_ERROR_MESSAGE_START)?String.valueOf(validationErrors.toArray()[0]).substring(2):String.valueOf(validationErrors.toArray()[0]);
				invalidRequestException = new InvalidRequestException(MessageConstants.MAPI_400,validationErrorMessage);											
				throw invalidRequestException;
			}
		}catch(IOException ioException) {
			log.error(" Error while requesting for config item ",ioException);
			throw new InternalServerException(MessageConstants.MAPI_500,"");
		}catch(Exception exception) {
			log.error(" Error while requesting for config item ",exception);
			throw new InternalServerException(MessageConstants.MAPI_500,"");
		}
	}
	
	
	/**
	 * validates the response data
	 * @param requestValidationSchema
	 * @param requestData
	 */
	public void validateResponseData(String journey,String responseData) throws InternalServerException {
		Set<ValidationMessage> validationErrors = null;	
		InternalServerException internalServerException = null;
		String validationErrorMessage = "";
		
		try {			
			validationErrors = requestDataValidationService.validateJson(objectMapper.readTree(responseData), journey+"-"+MessageConstants.RESPONSE_DATA+"-"+ServiceConstants.SCHEMA_KEY);
			
			if(!ObjectUtils.isEmpty(validationErrors)) {
				validationErrors.stream().forEach(validationError->System.out.println(" Data validation <<Error>>: "+validationError.getMessage()));	
				validationErrorMessage = String.valueOf(validationErrors.toArray()[0]).startsWith(MessageConstants.VALIDATION_ERROR_MESSAGE_START)?String.valueOf(validationErrors.toArray()[0]).substring(2):String.valueOf(validationErrors.toArray()[0]);
				internalServerException = new InternalServerException(MessageConstants.MAPI_400,validationErrorMessage);				
				throw internalServerException;
			}
		}catch(IOException ioException) {
			log.error(" Error while requesting for config item ",ioException);
			throw new InternalServerException(MessageConstants.MAPI_500,"");
		}catch(Exception exception) {
			log.error(" Error while requesting for config item ",exception);
			throw new InternalServerException(MessageConstants.MAPI_500,"");
		}
	}
	
	/**
	 * Validates the header information
	 * @param hashMapHeaders
	 * @throws InvalidRequestException
	 * @throws InternalServerException
	 */
	private void validateHeaders(String journey,Map<String,String> hashMapHeaders) throws InvalidRequestException,InternalServerException{
		InvalidRequestException invalidRequestException=null;
		
		if(ObjectUtils.isEmpty(hashMapHeaders)) {
			invalidRequestException= new InvalidRequestException(journey, "required headers missing");
		}
		
		if(!hashMapHeaders.containsKey(APIConstants.CORRELATION_ID_HEADER_KEY)) { 
			invalidRequestException= new InvalidRequestException(journey, " no correlation id found");
		}
		
		if(!hashMapHeaders.containsKey(APIConstants.TRANSACTION_ID_HEADER_KEY)) {
			invalidRequestException= new InvalidRequestException(journey," no transaction id found");
		}
		
		if(!hashMapHeaders.containsKey(APIConstants.CEM_KEY_HEADER_KEY)) {
			invalidRequestException= new InvalidRequestException(journey," Invalid Request, no cem key found");
		}
		
		if(!hashMapHeaders.containsKey(APIConstants.CHANNEL_HEADER_KEY.toLowerCase())) {
			invalidRequestException= new InvalidRequestException(journey," Invalid Request, no channel source found");
		}
		
		if(!ObjectUtils.isEmpty(invalidRequestException)) {
			invalidRequestException = new InvalidRequestException(MessageConstants.MAPI_400_1, journey);			
			throw invalidRequestException;
		}
	}
	
}
