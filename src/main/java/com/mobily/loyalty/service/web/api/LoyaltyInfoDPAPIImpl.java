package com.mobily.loyalty.service.web.api;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobily.loyalty.service.constants.APIConstants;
import com.mobily.loyalty.service.constants.MessageConstants;
import com.mobily.loyalty.service.constants.ServiceConstants;
import com.mobily.loyalty.service.datapower.model.LoyaltyInquiryRequest;
import com.mobily.loyalty.service.datapower.model.LoyaltyInquiryResponse;
import com.mobily.loyalty.service.exceptions.InternalServerException;
import com.mobily.loyalty.service.exceptions.InvalidRequestException;
import com.mobily.loyalty.service.exceptions.ResourceNotFoundException;
import com.mobily.loyalty.service.exceptions.UnauthorizedAccessException;
import com.mobily.loyalty.service.model.LoyaltyInfoRequest;
import com.mobily.loyalty.service.model.LoyaltyInfoResponse;
import com.mobily.loyalty.service.model.MasterResponse;
import com.mobily.loyalty.service.model.Status;
import com.mobily.loyalty.service.resourcebundle.Locales;
import com.mobily.loyalty.service.resourcebundle.LocalizedMessageService;
import com.mobily.loyalty.service.services.LoyaltyInfoService;
import com.mobily.loyalty.service.services.ServiceResponseBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Loyalty Infor Service API
 * @author ACLDigital
 *
 */
@RestController
@Slf4j
public class LoyaltyInfoDPAPIImpl implements ILoyaltyInfoDPAPI{

	@Autowired
	private LocalizedMessageService localizedMessageService;

	/** Response service builder reference **/
	@Autowired
	private ServiceResponseBuilder responseBuilder;

	/** Login service reference **/
	@Autowired
	private LoyaltyInfoService loyaltyInfoService;

	/** cem key **/
	private final static String CEM_KEY="cem-key";

	/** journey name **/
	private final static String JOURNEY="loyalty";

	/** journey operation name **/
	private final static String JOURNEY_OPERATION="loyalty-inquiry";

	/** journey name **/
	private static int expectedResponseCode = 200;

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
  public  String getLoyaltyInfoDPPub(@RequestBody(required = true) LoyaltyInquiryRequest loyaltyInquiryRequest,HttpServletRequest httpServletRequest) throws InternalServerException,InvalidRequestException,UnauthorizedAccessException,ResourceNotFoundException,Exception{

	   ObjectMapper objectMapper = new ObjectMapper();
	   log.info(" Request from Orchetration Layer is "+objectMapper.writeValueAsString(loyaltyInquiryRequest));
	   try {
		     if(expectedResponseCode==200) {
		    	 return buildSuccessMesage();
		     }else {
		    	 return buildNonSuccessMesage();
		     }
	   }catch(Exception e) {
		   throw new ResourceNotFoundException(MessageConstants.MAPI_500, "");
	   }
  }

  /**
	 * Sets the expected respons ecode from the Loyalty Inquiry Service - This is a dummy service for testing
	 * @param msidsn
	 * @return
	 * @throws InternalServerException
	 * @throws InvalidRequestException
	 * @throws UnauthorizedAccessException
	 * @throws ResourceNotFoundException
	 * @throws Exception
	 */
  @PostMapping(APIConstants.LOYALTY_INFO_SERVICE_DP_RESP_CODE_PUBLIC_URL)
  @Consumes({APIConstants.CONSUMES_FORMAT})
  @Produces({APIConstants.PRODUCES_FORMAT})
	 @ApiOperation(value = "Queries for Loyalty Information", notes = "This Service API queries for Loyalty Information based on MSISDN", tags = {"Loyalty Information"})
	    @ApiResponses(value = {
	        @ApiResponse(code = 200, message = "*Success* - Success queries Laoyalty Information", response = MasterResponse.class),
	        @ApiResponse(code = 400, message = "Bad request - Required request details missing"),
	        @ApiResponse(code = 500, message = "Internal server error - Something went wrong")
	    })
	@Override
   public String setLoyaltyInqueryResCodePub(HttpServletRequest httpServletRequest) throws InternalServerException,InvalidRequestException,UnauthorizedAccessException,ResourceNotFoundException,Exception{
		this.expectedResponseCode = 200;

		if(!ObjectUtils.isEmpty(httpServletRequest.getParameter(ServiceConstants.RESPONSE_CODE_KEY))) {
			this.expectedResponseCode = Integer.parseInt(httpServletRequest.getParameter(ServiceConstants.RESPONSE_CODE_KEY));
		}
		return "Successfully set the expected status code";
	}

  public void setExpectedResponse(int expectedResponseCode) {
	  this.expectedResponseCode = expectedResponseCode;
  }

  /**
   * returns expected response code
   * @param expectedResponseCode
   * @return
   */
  public int getExpectedResponse() {
	  return this.expectedResponseCode;
  }

  /**
   * temporary response
   */
  private LoyaltyInquiryResponse populateDummyResponse() {
	  LoyaltyInquiryResponse loyaltyInquiryResponse = new LoyaltyInquiryResponse();
	  loyaltyInquiryResponse.setTransactionId("222333");
	  loyaltyInquiryResponse.setStatusCode("0");
	  loyaltyInquiryResponse.setPointsLeft("1000");
	  loyaltyInquiryResponse.setTotalEarnedPoints("3000");
	  loyaltyInquiryResponse.setTotalRedeemedPoints("3000");
	  loyaltyInquiryResponse.setTotalLostPoints("400");
	  loyaltyInquiryResponse.setTotalExpiredPoints("200");
	  loyaltyInquiryResponse.setRedemptionTransLimit("2000");
	  return loyaltyInquiryResponse;
  }

  /**
   * builds response code for 200
   */
  private String buildSuccessMesage() throws Exception{
	  ObjectMapper objectMapper = new ObjectMapper();
	  return objectMapper.writeValueAsString(populateDummyResponse());
  }

  /**
   * builds response code for 200
   */
  private String buildNonSuccessMesage() throws Exception{
	  ObjectMapper objectMapper = new ObjectMapper();
	  Status status = new Status();
	  status.setCode(String.valueOf(getExpectedResponse()));
  	  status.setMessageEn(localizedMessageService.localMessage(MessageConstants.LOYALTY_INQUIRY_MESSAGES,String.valueOf(getExpectedResponse()), Locales.EN)+" ");
  	  status.setMessageAr(localizedMessageService.localMessage(MessageConstants.LOYALTY_INQUIRY_MESSAGES,String.valueOf(getExpectedResponse()), Locales.AR)+" ");
  	  status.setSourceCode(String.valueOf(getExpectedResponse()));
  	  status.setSourceMessage(localizedMessageService.localMessage(MessageConstants.LOYALTY_INQUIRY_MESSAGES,String.valueOf(getExpectedResponse()), Locales.EN));
  	  status.setSourceCode(String.valueOf(getExpectedResponse()));
  	  status.setHelp(localizedMessageService.localMessage(MessageConstants.GENERIC_MESSAGE,MessageConstants.HELP_MESSAGE, Locales.EN));
	  return objectMapper.writeValueAsString(populateDummyResponse());
  }
}