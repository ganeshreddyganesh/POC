package com.mobily.loyalty.service.advisors;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobily.loyalty.service.constants.MessageConstants;
import com.mobily.loyalty.service.exceptions.BaseException;
import com.mobily.loyalty.service.exceptions.InternalServerException;
import com.mobily.loyalty.service.exceptions.InvalidRequestException;
import com.mobily.loyalty.service.exceptions.ResourceAlreadyExistsException;
import com.mobily.loyalty.service.exceptions.ResourceNotFoundException;
import com.mobily.loyalty.service.exceptions.UnauthorizedAccessException;
import com.mobily.loyalty.service.model.MasterResponse;
import com.mobily.loyalty.service.model.Status;
import com.mobily.loyalty.service.resourcebundle.Locales;
import com.mobily.loyalty.service.resourcebundle.LocalizedMessageService;

import lombok.extern.slf4j.Slf4j;


/**
 * Handles all exceptions and returns the response accordingly
 * @author Mobily Info Tech(MIT)
 *
 */
@Slf4j
@ControllerAdvice
public class RESTResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private LocalizedMessageService localizedMessageService;

	/**
	 * handles ResourceNotFoundException
	 * @param ex
	 * @param request
	 * @return
	 */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseEntity<Object> handleResourceNotFoundException(final ResourceNotFoundException ex,
                                                             final WebRequest request) {
        return buildExceptionResponse(ex, HttpStatus.NOT_FOUND);
    }
    
    /**
	 * handles InternalServerException
	 * @param ex
	 * @param request
	 * @return
	 */
    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ResponseEntity<Object> handleInternalServerException(final InternalServerException ex,
                                                             final WebRequest request) {
        return buildExceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
   	 * handles InvalidRequestException
   	 * @param ex
   	 * @param request
   	 * @return
   	 */
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ResponseEntity<Object> handleBadRequestException(final InvalidRequestException ex,
                                                             final WebRequest request) {
        return buildExceptionResponse(ex, HttpStatus.BAD_REQUEST);
    }
    
    /**
	 * handles UnAuthorizedException
	 * @param ex
	 * @param request
	 * @return
	 */
    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public @ResponseBody ResponseEntity<Object> handleDUnAuthorizedException(final UnauthorizedAccessException ex,
                                                             final WebRequest request) {    	
        return buildExceptionResponse(ex, HttpStatus.UNAUTHORIZED);
    }
    
    /**
  	 * handles DuplicateKeyViolationException
  	 * @param ex
  	 * @param request
  	 * @return
  	 */
      @ExceptionHandler(ResourceAlreadyExistsException.class)
      @ResponseStatus(value = HttpStatus.CONFLICT)
      public @ResponseBody ResponseEntity<Object> handleResourceAlreadyExistsException(final ResourceAlreadyExistsException ex,
                                                               final WebRequest request) {
      	log.info("Entered into Resource Already Exists Exception");
          return buildExceptionResponse(ex, HttpStatus.CONFLICT);
      }

	/**
	 * Builds the exception response entity.
	 *
	 * @param baseException The custom base exception containing error details.
	 * @param httpStatus The HTTP status to return in the response.
	 * @return A ResponseEntity with populated MasterResponse and Status.
	 */

	private ResponseEntity<Object> buildExceptionResponse(final BaseException baseException, HttpStatus httpStatus) {
    
    	Status status = new Status();
    	MasterResponse masterResponse = new MasterResponse();
    	masterResponse.setStatus(status);    	
    	status.setCode(String.valueOf(httpStatus.value()));    	
    	status.setMessageEn(localizedMessageService.localMessage(MessageConstants.LOYALTY_INQUIRY_MESSAGES, baseException.getMessageCode(), Locales.EN)+" "+baseException.getMessage());    	    	    	
    	status.setMessageAr(localizedMessageService.localMessage(MessageConstants.LOYALTY_INQUIRY_MESSAGES, baseException.getMessageCode(), Locales.AR)+" "+baseException.getMessage());
    	status.setSourceCode(baseException.getMessageCode());
    	status.setSourceMessage(localizedMessageService.localMessage(MessageConstants.GENERIC_MESSAGE,baseException.getMessageCode(), Locales.EN));
    	status.setSourceCode(baseException.getMessageCode()); 
    	status.setHelp(localizedMessageService.localMessage(MessageConstants.GENERIC_MESSAGE,MessageConstants.HELP_MESSAGE, Locales.EN));    	    	
    	ResponseEntity<Object> responseEntity = new ResponseEntity<>(masterResponse, httpStatus);
        return responseEntity;
    }
}