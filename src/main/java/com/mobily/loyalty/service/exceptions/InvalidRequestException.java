package com.mobily.loyalty.service.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * This is a custom exception thrown when validating the service request and covers the validations:
 * 		Syntax ( ex: Date Format)
 *      Mandatory Fields Presence
 *      Data Range out of defined bounds
 * @author  Mobily Info Tech (MIT)
 *
 */
@Getter
@Setter
public class InvalidRequestException extends BaseException{

	/**
	 * construtor
	 * @param message
	 */
	public InvalidRequestException(String code,String message) {
		super(code,message);		
	}
}
