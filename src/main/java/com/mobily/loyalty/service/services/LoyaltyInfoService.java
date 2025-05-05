package com.mobily.loyalty.service.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.InternalServerErrorException;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobily.loyalty.service.configs.AppConfigurationLoader;
import com.mobily.loyalty.service.constants.APIConstants;
import com.mobily.loyalty.service.constants.MessageConstants;
import com.mobily.loyalty.service.constants.ServiceConstants;
import com.mobily.loyalty.service.datapower.model.LoyaltyInquiryRequest;
import com.mobily.loyalty.service.datapower.model.LoyaltyInquiryResponse;
import com.mobily.loyalty.service.exceptions.InternalServerException;
import com.mobily.loyalty.service.exceptions.InvalidRequestException;
import com.mobily.loyalty.service.exceptions.ResourceAlreadyExistsException;
import com.mobily.loyalty.service.exceptions.ResourceNotFoundException;
import com.mobily.loyalty.service.model.ExtraInfo;
import com.mobily.loyalty.service.model.LoyaltyInfoRequest;
import com.mobily.loyalty.service.model.LoyaltyInfoResponse;
import com.mobily.loyalty.service.model.MasterResponse;
import com.mobily.loyalty.service.resourcebundle.Locales;
import com.mobily.loyalty.service.resourcebundle.LocalizedMessageService;
import com.mobily.loyalty.service.serviceclient.ServiceClient;
import com.mobily.loyalty.service.serviceclient.ServiceResponse;
import com.mobily.loyalty.service.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * This class provides the Loyalty Information operations.
 *    1. Once Request is received, data is validated and if failed returns 400 status code
 *    2. Builds the service request to consume Data Power LoyaltyService
 *    3. Processes the Data Power Service Response
 *    4. Returns response back to client
 *    
 * @author Mobily Info Tech (MIT)
 *
 */
@Service
@Slf4j
public class LoyaltyInfoService extends BaseService{

	@Autowired
	private ServiceClient serviceClient;
	
	@Autowired
	private AppConfigurationLoader appConfigurationLoader;
	
	/** journey name **/
	private final static String JOURNEY="loyalty";
	
	/** operation name **/
	private final static String JOURNEY_OPERATION="loyalty-inquiry";
	
	@Autowired
	private LocalizedMessageService localizedMessageService;
		
	/**
	 * queries for loyalty information with DP services
	 * @param msidsn
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws InternalServerErrorException
	 * @throws InvalidRequestException
	 */
	public Object getLoyaltyInformation(LoyaltyInfoRequest loyaltyInfoRequest, Map<String,String> hashMapHeaders) throws ResourceNotFoundException,InternalServerException,InvalidRequestException,Exception{
		log.info(" About to query for Loyalty Information from Data Power service "+MDC.get(APIConstants.CORRELATION_ID_HEADER_KEY));				
		validate(JOURNEY_OPERATION,loyaltyInfoRequest,hashMapHeaders);	
		ServiceResponse serviceResponse = fetchLoyaltyInfoFromDP(loyaltyInfoRequest,hashMapHeaders);
		/** Span newSpan = this.tracer.nextSpan().name("calculateTax");
		Span span = tracer.currentSpan();
		log.info(" SPAN ID IS "+span.toString());
		log.info("Trace ID {}", span.context().spanId());
	    log.info("Span ID {}", span.context().traceId());
	    **/
		MDC.put("SERVICE_NAME", "LOYALTY_INQUIRY");
		return buildLoyaltyInquiryDPResponse(serviceResponse);
	}
	
	
	/**
	 * builds Data Power Service Response
	 * @param serviceResponse
	 * @return
	 */
	private Object buildLoyaltyInquiryDPResponse(ServiceResponse serviceResponse) throws InternalServerException {
				
		if(!ObjectUtils.isEmpty(serviceResponse) && serviceResponse.getHttpStatusCode()==200) {					
			validateResponseData(JOURNEY_OPERATION,serviceResponse.getResponsePayload());			
			return buildLoyaltyInquirySuccessResponse(serviceResponse.getResponsePayload());										
		}				
		InternalServerException internalServerException =  new InternalServerException(MessageConstants.MAPI_500, "");				
		throw internalServerException;		
	}
	
	/**
	 * build success response
	 * @param dpSuccessPayload
	 * @return
	 * @throws InternalServerException
	 */
	private Object buildLoyaltyInquirySuccessResponse(String dpSuccessPayload) throws InternalServerException{
		LoyaltyInfoResponse loyaltyInfoResponse = null;
		ObjectMapper objectMapper = null;
		LoyaltyInquiryResponse loyaltyInquiryResponse = null;
		MasterResponse masterResponse = null;
		
		try {
			masterResponse = new MasterResponse();
			loyaltyInfoResponse = new LoyaltyInfoResponse();
			objectMapper = new ObjectMapper();
			loyaltyInquiryResponse = objectMapper.readValue(dpSuccessPayload, LoyaltyInquiryResponse.class);
			loyaltyInfoResponse.setTransactionId(loyaltyInquiryResponse.getTransactionId());
			loyaltyInfoResponse.setPointsLeft(loyaltyInquiryResponse.getPointsLeft());
			loyaltyInfoResponse.setRedemptionTransLimit(loyaltyInquiryResponse.getRedemptionTransLimit());
			loyaltyInfoResponse.setStatusCode(loyaltyInquiryResponse.getStatusCode());
			loyaltyInfoResponse.setTotalEarnedPoints(loyaltyInquiryResponse.getTotalEarnedPoints());
			loyaltyInfoResponse.setTotalExpiredPoints(loyaltyInquiryResponse.getTotalExpiredPoints());
			loyaltyInfoResponse.setTotalRedeemedPoints(loyaltyInquiryResponse.getTotalRedeemedPoints());		
		}catch(Exception e) {
			log.error(" Error while processing response from Data Power Service ",e);
			throw new InternalServerException(MessageConstants.MAPI_500, "");
		}
		
		return loyaltyInfoResponse;
	}
		
	/**
	 * fetches the loyalty information from DP
	 * @param loyaltyInfoRequest
	 * @param hashMapHeaders
	 * @throws ResourceNotFoundException
	 * @throws InternalServerException
	 * @throws InvalidRequestException
	 */
	private ServiceResponse fetchLoyaltyInfoFromDP(LoyaltyInfoRequest loyaltyInfoRequest, Map<String,String> hashMapHeaders) throws ResourceNotFoundException,ResourceAlreadyExistsException,InternalServerException,InvalidRequestException,Exception{
		
		// Validate account number
		boolean hasAccountNumber = false;
		List<ExtraInfo> lstExtraInfo = loyaltyInfoRequest.getExtraInfo();
		if (lstExtraInfo == null || lstExtraInfo.isEmpty()) {
			throw new InvalidRequestException(MessageConstants.MAPI_400, "Account number is required");
		}
		
		for(ExtraInfo extraInfo : lstExtraInfo) {
			if(!ObjectUtils.isEmpty(extraInfo.getKey()) && extraInfo.getKey().equalsIgnoreCase(MessageConstants.ACCOUNT_NUMBER)){
				if(ObjectUtils.isEmpty(extraInfo.getValue()) || extraInfo.getValue().trim().isEmpty()) {
					throw new InvalidRequestException(MessageConstants.MAPI_400, "Account number is required");
				}
				hasAccountNumber = true;
				break;
			}
		}
		if(!hasAccountNumber) {
			throw new InvalidRequestException(MessageConstants.MAPI_400, "Account number is required");
		}

		// Validate language
		if(ObjectUtils.isEmpty(loyaltyInfoRequest.getLanguage()) || loyaltyInfoRequest.getLanguage().trim().isEmpty()) {
			throw new InvalidRequestException(MessageConstants.MAPI_400, "Language is required");
		}
		if(loyaltyInfoRequest.getLanguage().length() != 2) {
			throw new InvalidRequestException(MessageConstants.MAPI_400, "Language must be 2 characters");
		}

		String loyaltyServiceDPURL = appConfigurationLoader.getLoyaltyInquiryDPURL();
		ObjectMapper objectMapper = new ObjectMapper();
		ServiceResponse serviceResponse = null;		
		LoyaltyInquiryRequest loyaltyInquiryRequest = new LoyaltyInquiryRequest();
		
		loyaltyInquiryRequest.setVersion(loyaltyInfoRequest.getVersion());
		loyaltyInquiryRequest.setTransactionId(hashMapHeaders.get(APIConstants.CORRELATION_ID_HEADER_KEY));
		loyaltyInquiryRequest.setLanguage(loyaltyInfoRequest.getLanguage());
		
		for(ExtraInfo extraInfo:lstExtraInfo) {
			if(!ObjectUtils.isEmpty(extraInfo.getKey()) && extraInfo.getKey().equalsIgnoreCase(MessageConstants.ACCOUNT_NUMBER)){
				loyaltyInquiryRequest.setMsisdn(extraInfo.getValue());
			}
		}
		loyaltyInquiryRequest.setSessionId("");
		loyaltyInquiryRequest.setUserName(MessageConstants.USER_NAME);
		loyaltyInquiryRequest.setAppId(MessageConstants.APP_ID);
		loyaltyInquiryRequest.setDeviceId(MessageConstants.DEVICE_ID);
		loyaltyInquiryRequest.setRequestDate(DateUtil.getCurrentDate());
		loyaltyInquiryRequest.setLanguage(Locales.EN);
		
		if(!ObjectUtils.isEmpty(System.getenv(APIConstants.DP_SERVICE_URL))) {
			loyaltyServiceDPURL = System.getenv(APIConstants.DP_SERVICE_URL);
		}
		log.info(" DP Service URL is "+loyaltyServiceDPURL);
		serviceResponse = serviceClient.execute(loyaltyServiceDPURL, HttpMethod.POST, objectMapper.writeValueAsString(loyaltyInquiryRequest), populateHttpHeaders(hashMapHeaders));
		
		// Check for service errors
		if (serviceResponse != null && serviceResponse.getHttpStatusCode() >= 500) {
			throw new InternalServerException(MessageConstants.MAPI_500, "Service error occurred");
		}
		
		return serviceResponse;				
	}
		
	/**
	 * populates the headers
	 * @param mapHeaders
	 * @return
	 */
	private HttpHeaders populateHttpHeaders(Map<String,String> mapHeaders) {
		String key="";
		HttpHeaders httpHeaders = new HttpHeaders();
		Set<String> enumKeys = mapHeaders.keySet();		
		enumKeys.forEach(c->httpHeaders.add(c, mapHeaders.get(c)));
		httpHeaders.add(ServiceConstants.CONTENT_TYPE_KEY, ServiceConstants.CONTENT_TYPE_APPLICATION_JSON);	
		httpHeaders.add(APIConstants.X_USERID_HEADER_KEY, APIConstants.X_USERID_KEY_VALUE);	
		return httpHeaders;
	}	
}
