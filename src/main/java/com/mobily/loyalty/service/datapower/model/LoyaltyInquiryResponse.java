package com.mobily.loyalty.service.datapower.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

/**
 * Loyalty Inquiry DP Response
 * @author Mobily Info Tech (MIT)
 *
 */
@Getter
@Setter
public class LoyaltyInquiryResponse {
	
	/** transaction id **/	
	@JsonProperty(
	        value="transactionId",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String transactionId;
	
	/** status code **/
	@JsonProperty(
	        value="statusCode",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String statusCode;
	
	/** points earned **/
	@JsonProperty(
	        value="pointsLeft",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String pointsLeft;
	
	/** Total Earned Points **/
	@JsonProperty(
	        value="totalEarnedPoints",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String totalEarnedPoints;
	
	/** total reedemed points **/
	@JsonProperty(
	        value="totalRedeemedPoints",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String totalRedeemedPoints;
	
	/** total lost points **/
	@JsonProperty(
	        value="totalLostPoints",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String totalLostPoints;
	
	/** total expired points **/
	@JsonProperty(
	        value="totalExpiredPoints",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String totalExpiredPoints;
	
	/** redemption transaction limit **/
	@JsonProperty(
	        value="redemptionTransLimit",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String redemptionTransLimit;
		
}
