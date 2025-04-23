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
public class LoyaltyInfoRequest {
	
	/** version number **/
	@JsonProperty(
	        value="version",
	        required=true,
	        defaultValue="v1",
	        access= Access.READ_WRITE)
	private String version;
	
	/** journey name **/
	@JsonProperty(
	        value="journey",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String journey;
	
	/** operation name **/
	@JsonProperty(
	        value="operation",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String operation;
	
	/** operation name **/
	@JsonProperty(
	        value="language",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String language;
	
	/** operation name **/
	@JsonProperty(
	        value="extraInfo",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private List<ExtraInfo> extraInfo;
}
