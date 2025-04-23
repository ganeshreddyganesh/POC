package com.mobily.loyalty.service.exceptions;

/**
 * This is a custom exception thrown when a session timed-out for a logged-in user, when in-active for X minutes
 * @author  Mobily Info Tech (MIT)
 *
 */
public class SessionTimeoutException extends BaseException{

	/**
	 * construtor
	 * @param message
	 */
	public SessionTimeoutException(String code,String message) {
		super(code,message);
	}
}
