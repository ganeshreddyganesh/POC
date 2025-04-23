package com.mobily.loyalty.service.exceptions;

/**
 * This is a custom exception thrown when a requested application configuration is not found
 * @author  Mobily Info Tech (MIT)
 *
 */
public class ConfigItemNotFoundException extends BaseException{

	/**
	 * construtor
	 * @param message
	 */
	public ConfigItemNotFoundException(String code,String message) {
		super(code,message);
	}
}
