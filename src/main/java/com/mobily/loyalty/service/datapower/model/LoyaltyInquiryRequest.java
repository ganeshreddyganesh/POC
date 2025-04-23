package com.mobily.loyalty.service.datapower.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

/**
 * Loyalty Inquiry Request data model
 * @author Mobily Info Tech (MIT)
 *
 */
@Getter
@Setter
public class LoyaltyInquiryRequest {

	
	/** transaction id **/	
	@JsonProperty(
	        value="transactionId",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String transactionId;
	
	/** session  id **/
	@JsonProperty(
	        value="sessionId",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String sessionId;
	
	/** version of the request model**/
	@JsonProperty(
	        value="version",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String version;
	
	/** application id, consuming the service **/
	@JsonProperty(
	        value="appId",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String appId;
	
	/** tdevice id making the request **/
	@JsonProperty(
	        value="deviceId",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String deviceId;
	
	/** msisdn **/
	@JsonProperty(
	        value="msisdn",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String msisdn;
	
	/** user name **/
	@JsonProperty(
	        value="userName",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String userName;
	
	/** requested date **/
	@JsonProperty(
	        value="requestDate",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String requestDate;
	
	/** language **/
	@JsonProperty(
	        value="language",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String language;
}
