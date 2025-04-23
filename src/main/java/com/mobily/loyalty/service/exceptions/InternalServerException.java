package com.mobily.loyalty.service.exceptions;

/**
 * This is a custom exception thrown when servicing the request, this abstracts most of the application specific erros like:
 * 		SQL Exception
 *      Null Pointer Exception
 *      Logic Errors etc..
 * @author  Mobily Info Tech (MIT)
 *
 */
public class InternalServerException extends BaseException{

	/**
	 * construtor
	 * @param message
	 */
	public InternalServerException(String code,String message) {
		super(code,message);
	}
}
