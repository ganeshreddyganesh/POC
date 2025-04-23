package com.mobily.loyalty.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

/**
 * This models the service status object
 * @author Mobily Info Tech (MIT)
 *
 */
@Getter
@Setter
public class Status {
	
	/** response code **/	
	@JsonProperty(
	        value="code",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
    private String code="";
    
    /** response source code **/
	@JsonProperty(
	        value="sourceCode",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
    private String sourceCode="";
    
    /** response source message **/
	@JsonProperty(
	        value="sourceMessage",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
    private String sourceMessage="";
    
    /**response message in Arabic **/
	@JsonProperty(
	        value="messageAr",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
    private String messageAr="";
    
    /** response message in English **/
	@JsonProperty(
	        value="messageEn",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
    private String messageEn="";
    
    /** response help message **/
	@JsonProperty(
	        value="help",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)
    private String help="";
}
