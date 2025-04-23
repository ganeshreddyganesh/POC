package com.mobily.loyalty.service.web.api;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mobily.loyalty.service.constants.MessageConstants;
import com.mobily.loyalty.service.exceptions.InternalServerException;
import com.mobily.loyalty.service.exceptions.InvalidRequestException;
import com.mobily.loyalty.service.exceptions.ResourceNotFoundException;
import com.mobily.loyalty.service.exceptions.UnauthorizedAccessException;
import com.mobily.loyalty.service.model.LoyaltyInfoRequest;
import com.mobily.loyalty.service.model.MasterResponse;
import com.mobily.loyalty.service.model.Status;
import com.mobily.loyalty.service.resourcebundle.Locales;
import com.mobily.loyalty.service.resourcebundle.LocalizedMessageService;
import com.mobily.loyalty.service.services.LoyaltyInfoService;
import com.mobily.loyalty.service.services.ServiceResponseBuilder;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Loyalty Infor Service API
 * @author ACLDigital
 *
 */
@RestController
@Slf4j
public class LoyaltyInfoAPIImpl implements ILoyaltyInfoAPI{
	
	/** Response service builder reference **/
	@Autowired
	private ServiceResponseBuilder responseBuilder;
	
	/** Login service reference **/
	@Autowired
	private LoyaltyInfoService loyaltyInfoService;
	
	/** cem key **/
	private final static String CEM_KEY="cem-key";
	
	/** cem key **/
	private final static String CUSTOM_HEADER_START_KEY="x-";
	
	@Autowired
	private LocalizedMessageService localizedMessageService;

	/**
	 * queries for loyalty information - This is secure version with JST Token
	 * @param msidsn
	 * @return
	 * @throws InternalServerException
	 * @throws InvalidRequestException
	 * @throws UnauthorizedAccessException
	 * @throws ResourceNotFoundException
	 * @throws Exception
	 */
   @Override
   public  MasterResponse getLoyaltyInfo(@RequestBody LoyaltyInfoRequest loyaltyInfoRequest,HttpServletRequest httpServletRequest) throws InternalServerException,InvalidRequestException,UnauthorizedAccessException,ResourceNotFoundException,Exception{
	   log.info("About to query for Loyalty Information Secured Service "+loyaltyInfoRequest);
	   MasterResponse masterResponse = new MasterResponse();
	   masterResponse.setStatus(buildSucecssStatus());
	   masterResponse.setData(loyaltyInfoService.getLoyaltyInformation(loyaltyInfoRequest,populateHeaders(httpServletRequest)));
	   return masterResponse;
   }
   
   /**
	 * queries for loyalty information - This is public version
	 * @param msidsn
	 * @return
	 * @throws InternalServerException
	 * @throws InvalidRequestException
	 * @throws UnauthorizedAccessException
	 * @throws ResourceNotFoundException
	 * @throws Exception
	 */
  @Override
  public  MasterResponse getLoyaltyInfoPub(@RequestBody(required = true) LoyaltyInfoRequest loyaltyInfoRequest,HttpServletRequest httpServletRequest) throws InternalServerException,InvalidRequestException,UnauthorizedAccessException,ResourceNotFoundException,Exception{	   
	   log.info("About to query for Loyalty Information Public Service"+loyaltyInfoRequest);
	   MasterResponse masterResponse = new MasterResponse();
	   masterResponse.setStatus(buildSucecssStatus());
	   masterResponse.setData(loyaltyInfoService.getLoyaltyInformation(loyaltyInfoRequest,populateHeaders(httpServletRequest)));
	   return masterResponse;
  }

  /**
   * builds success status
   * @return
   */
  private Status buildSucecssStatus() {
		Status status = new Status();
		status.setCode(String.valueOf(HttpStatus.SC_OK));
		status.setSourceCode(MessageConstants.MAPI_000);
		status.setSourceMessage(MessageConstants.SUCCESS_MESSAGE);
		status.setMessageEn(localizedMessageService.localMessage(MessageConstants.LOYALTY_INQUIRY_MESSAGES, MessageConstants.MAPI_000, Locales.EN));
		status.setMessageAr(localizedMessageService.localMessage(MessageConstants.LOYALTY_INQUIRY_MESSAGES, MessageConstants.MAPI_000, Locales.AR));					
		status.setHelp(localizedMessageService.localMessage(MessageConstants.GENERIC_MESSAGE,MessageConstants.HELP_MESSAGE, Locales.EN));    
		return status;
  }
  /**
   * returns headers
   * @param httpServletRequest
   * @return
   */
  private Map<String,String> populateHeaders(HttpServletRequest httpServletRequest){
	  HashMap<String,String> hashMapHeaders = new HashMap<String,String>();
	  
	  String key = "";
	  Enumeration enumHeaders =httpServletRequest.getHeaderNames();
	  
	  while(enumHeaders.hasMoreElements()) {
		  key = String.valueOf(enumHeaders.nextElement());	
		  if(key.startsWith(CUSTOM_HEADER_START_KEY) || key.startsWith(CEM_KEY)) {
			  hashMapHeaders.put(key, httpServletRequest.getHeader(key));	
		  }
	  }	  
	  return hashMapHeaders;
  }
}