package com.mobily.loyalty.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * base exception class for all custom defined exceptions
 * @author Mobily Info Tech (MIT)
 *
 */
@Getter
@Setter
public class BaseException extends Exception{

	/** source **/
	private String messageCode;

	public BaseException(String code,String message) {
		super(message);		
		this.messageCode = code;
	}
		
}
