package com.mobily.loyalty.service.exceptions;

/**
 * This is a custom exception thrown when requested resource is not found within the system ex: For Loyalty Information, the input field MSISDN is not found in the system 
 * @author  Mobily Info Tech (MIT)
 *
 */
public class ResourceNotFoundException extends BaseException{

	/**
	 * construtor
	 * @param message
	 */
	public ResourceNotFoundException(String code,String message) {
		super(code,message);
	}
}
