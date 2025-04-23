package com.mobily.loyalty.service.serviceclient;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Service Response returned when a service external to this build is consumed
 * @author Mobily Info Tech (MIT)
 *
 */

@NoArgsConstructor
@Getter
@Data
@Setter
public class ServiceResponse {

	/** http status code **/	
	@JsonProperty(
	        value="httpStatusCode",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private int httpStatusCode;
	
	/** response payload **/
	@JsonProperty(
	        value="false",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String responsePayload;
	
	/** message description **/
	@JsonProperty(
	        value="description",
	        required=false,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private String description;
	
	/** HTTP Headers **/	
	@JsonProperty(
	        value="httpHeaders",
	        required=false,
	        defaultValue="",
	        access= Access.READ_WRITE)
	private HttpHeaders httpHeaders;
}
