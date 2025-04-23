package com.mobily.loyalty.service.exceptions;

/**
 * This is a custom exception thrown when adding a new resource into the system and if the resource is already present in the system: 
 * @author  Mobily Info Tech (MIT)
 *
 */
public class ResourceAlreadyExistsException extends BaseException{

	/**
	 * construtor
	 * @param message
	 */
	public ResourceAlreadyExistsException(String code,String message) {
		super(code,message);
	}
}
