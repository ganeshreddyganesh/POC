package com.mobily.loyalty.service.exceptions;

/**
 * This is a custom exception thrown when an attempt un-authorized access to resource is made
 * @author  Mobily Info Tech (MIT)
 *
 */
public class UnauthorizedAccessException extends BaseException{

	/**
	 * construtor
	 * @param message
	 */
	public UnauthorizedAccessException(String code,String message) {
		super(code,message);
	}
}
