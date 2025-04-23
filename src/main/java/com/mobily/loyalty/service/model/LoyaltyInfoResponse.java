package com.mobily.loyalty.service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Loyalty Information Request Model
 * @author Mobily Info Tech (MIT)
 *
 */
@Getter
@Setter
@Slf4j
@NoArgsConstructor
public class LoyaltyInfoResponse {	
	/** version number **/
	@JsonProperty(
	        value="transactionId",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String transactionId;
	
	/** journey name **/
	@JsonProperty(
	        value="statusCode",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String statusCode;
	
	/** operation name **/
	@JsonProperty(
	        value="pointsLeft",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String pointsLeft;
	
	/** operation name **/
	@JsonProperty(
	        value="totalEarnedPoints",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String totalEarnedPoints;
	
	/** operation name **/
	@JsonProperty(
	        value="totalExpiredPoints",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String totalExpiredPoints;
	
	/** operation name **/
	@JsonProperty(
	        value="redemptionTransLimit",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String redemptionTransLimit;
	
	/** operation name **/
	@JsonProperty(
	        value="totalRedeemedPoints",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String totalRedeemedPoints;
	
	/** operation name **/
	@JsonProperty(
	        value="totalLostPoints",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String totalLostPoints;
}
