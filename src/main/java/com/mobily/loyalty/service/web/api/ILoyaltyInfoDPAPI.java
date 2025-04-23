package com.mobily.loyalty.service.web.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobily.loyalty.service.constants.APIConstants;
import com.mobily.loyalty.service.datapower.model.LoyaltyInquiryRequest;
import com.mobily.loyalty.service.exceptions.InternalServerException;
import com.mobily.loyalty.service.exceptions.InvalidRequestException;
import com.mobily.loyalty.service.exceptions.ResourceNotFoundException;
import com.mobily.loyalty.service.exceptions.UnauthorizedAccessException;
import com.mobily.loyalty.service.model.MasterResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

/**
 * user login API
 * @author ACLDigital
 *
 */
@RestController
@RequestMapping(APIConstants.BASE_CONTEXT_PATH)
public interface ILoyaltyInfoDPAPI {

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
	@PostMapping(APIConstants.LOYALTY_INFO_SERVICE_DP_PUBLIC_URL)
    @Consumes({APIConstants.CONSUMES_FORMAT})
    @Produces({APIConstants.PRODUCES_FORMAT})
	 @ApiOperation(value = "Queries for Loyalty Information", notes = "This Service API queries for Loyalty Information based on MSISDN", tags = {"Loyalty Information"})
	    @ApiResponses(value = {
	        @ApiResponse(code = 200, message = "*Success* - Success queries Laoyalty Information", response = MasterResponse.class),
	        @ApiResponse(code = 400, message = "Bad request - Required request details missing"),
	        @ApiResponse(code = 500, message = "Internal server error - Something went wrong")
	    })
    String getLoyaltyInfoDPPub(@RequestBody LoyaltyInquiryRequest loyaltyInquiryRequest,HttpServletRequest httpServletRequest) throws InternalServerException,InvalidRequestException,UnauthorizedAccessException,ResourceNotFoundException,Exception;

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
    String setLoyaltyInqueryResCodePub(HttpServletRequest httpServletRequest) throws InternalServerException,InvalidRequestException,UnauthorizedAccessException,ResourceNotFoundException,Exception;


}