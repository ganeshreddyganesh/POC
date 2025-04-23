package com.mobily.loyalty.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Master Response Model
 * @author Mobily Info Tech (MIT)
 *
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
@JsonInclude(Include.NON_NULL)
public class MasterResponse {
	/** status  **/
	@JsonProperty(
	        value="status",
	        required=true,
	        defaultValue="",
	        access= Access.READ_WRITE)   
    private Status status;  
	
	/** status  **/
	@JsonProperty(
	        value="data",
	        required=false,
	        defaultValue="",
	        access= Access.READ_WRITE)
    private Object data;
}
