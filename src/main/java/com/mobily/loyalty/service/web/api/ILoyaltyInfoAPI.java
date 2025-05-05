package com.mobily.loyalty.service.web.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobily.loyalty.service.constants.APIConstants;
import com.mobily.loyalty.service.exceptions.InternalServerException;
import com.mobily.loyalty.service.exceptions.InvalidRequestException;
import com.mobily.loyalty.service.exceptions.ResourceNotFoundException;
import com.mobily.loyalty.service.exceptions.UnauthorizedAccessException;
import com.mobily.loyalty.service.model.LoyaltyInfoRequest;
import com.mobily.loyalty.service.model.MasterResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Loyalty Information API
 * @author Mobily Info Tech (MIT)
 */
@RestController
@RequestMapping(APIConstants.BASE_CONTEXT_PATH)
@Tag(name = "Loyalty Information", description = "APIs for managing loyalty information")
public interface ILoyaltyInfoAPI {

	/**
	 * Queries for loyalty information - This is secure version with JST Token
	 * @param loyaltyInfoRequest The loyalty information request
	 * @param httpServletRequest The HTTP request
	 * @return MasterResponse containing loyalty information
	 * @throws InternalServerException If there's an internal server error
	 * @throws InvalidRequestException If the request is invalid
	 * @throws UnauthorizedAccessException If the user is not authorized
	 * @throws ResourceNotFoundException If the resource is not found
	 * @throws Exception For other exceptions
	 */
	@PostMapping(APIConstants.LOYALTY_INFO_SERVICE_SECURED_URL)
	@Consumes({APIConstants.CONSUMES_FORMAT})
	@Produces({APIConstants.PRODUCES_FORMAT})
	@Operation(
		summary = "Get Loyalty Information (Secured)",
		description = "Retrieves loyalty information for a customer using JWT token authentication"
	)
	@APIResponse(
		responseCode = "200",
		description = "Successfully retrieved loyalty information",
		content = @Content(schema = @Schema(implementation = MasterResponse.class))
	)
	@APIResponse(
		responseCode = "400",
		description = "Bad request - Required request details missing"
	)
	@APIResponse(
		responseCode = "401",
		description = "Unauthorized - Invalid or missing JWT token"
	)
	@APIResponse(
		responseCode = "500",
		description = "Internal server error"
	)
	@RequestBody(
		content = @Content(
			schema = @Schema(implementation = LoyaltyInfoRequest.class),
			examples = @ExampleObject(
				value = "{\"version\":\"v1\",\"journey\":\"loyalty\",\"operation\":\"loyalty-inquiry\",\"language\":\"EN\",\"extraInfo\":[{\"key\":\"accountNumber\",\"value\":\"1234567890\"}]}"
			)
		)
	)
	MasterResponse getLoyaltyInfo(LoyaltyInfoRequest loyaltyInfoRequest, HttpServletRequest httpServletRequest) 
		throws InternalServerException, InvalidRequestException, UnauthorizedAccessException, ResourceNotFoundException, Exception;

	/**
	 * Queries for loyalty information - This is public version
	 * @param loyaltyInfoRequest The loyalty information request
	 * @param httpServletRequest The HTTP request
	 * @return MasterResponse containing loyalty information
	 * @throws InternalServerException If there's an internal server error
	 * @throws InvalidRequestException If the request is invalid
	 * @throws UnauthorizedAccessException If the user is not authorized
	 * @throws ResourceNotFoundException If the resource is not found
	 * @throws Exception For other exceptions
	 */
	@PostMapping(APIConstants.LOYALTY_INFO_SERVICE_PUBLIC_URL)
	@Consumes({APIConstants.CONSUMES_FORMAT})
	@Produces({APIConstants.PRODUCES_FORMAT})
	@Operation(
		summary = "Get Loyalty Information (Public)",
		description = "Retrieves loyalty information for a customer using public access"
	)
	@APIResponse(
		responseCode = "200",
		description = "Successfully retrieved loyalty information",
		content = @Content(schema = @Schema(implementation = MasterResponse.class))
	)
	@APIResponse(
		responseCode = "400",
		description = "Bad request - Required request details missing"
	)
	@APIResponse(
		responseCode = "500",
		description = "Internal server error"
	)
	@RequestBody(
		content = @Content(
			schema = @Schema(implementation = LoyaltyInfoRequest.class),
			examples = @ExampleObject(
				value = "{\"version\":\"v1\",\"journey\":\"loyalty\",\"operation\":\"loyalty-inquiry\",\"language\":\"EN\",\"extraInfo\":[{\"key\":\"accountNumber\",\"value\":\"1234567890\"}]}"
			)
		)
	)
	MasterResponse getLoyaltyInfoPub(LoyaltyInfoRequest loyaltyInfoRequest, HttpServletRequest httpServletRequest) 
		throws InternalServerException, InvalidRequestException, UnauthorizedAccessException, ResourceNotFoundException, Exception;
}