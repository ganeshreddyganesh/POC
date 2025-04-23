package com.mobily.loyalty.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Models for Extra Information part of Loyalty Information Request
 * @author Mobily Info Tech (MIT)
 *
 */
@Getter
@Setter
public class ExtraInfo {
	
	/** key name **/
	@JsonProperty(
	        value="key",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String key;
	
	/** value **/
	@JsonProperty(
	        value="value",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String value;	
}
