package com.mobily.loyalty.service.configs;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import org.apache.http.HttpStatus;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.mobily.loyalty.service.constants.APIConstants;
import com.mobily.loyalty.service.constants.ServiceConstants;
import com.mobily.loyalty.service.exceptions.InternalServerException;
import com.mobily.loyalty.service.exceptions.SessionTimeoutException;
import com.mobily.loyalty.service.exceptions.UnauthorizedAccessException;
import com.mobily.loyalty.service.services.UserSessionService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


/**
 * This is a custom filter for all service requests.
 * @author Mobily Info Tech (MIT)
 *
 */
@Component
@Slf4j
public class CustomFilter implements Filter {	

	/** user session service **/
	@Autowired
	private UserSessionService userSessionService;
  
     /**
      * Filters the Request Data
      */
  	 @Override	
	  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain chain) throws IOException, ServletException {
	  
		  HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		  HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
		  Enumeration enum1=httpServletRequest.getHeaderNames();
		 
		  if(!ObjectUtils.isEmpty(httpServletRequest.getRequestURI()) && !httpServletRequest.getRequestURI().startsWith(APIConstants.PUBLIC_CONTEXT)) {			   
			  String sessionToken = httpServletRequest.getHeader(ServiceConstants.AUTHORIZATION);
			  
			
			  if(ObjectUtils.isEmpty(sessionToken)) {
				  httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
			  }
			  if(sessionToken.startsWith(ServiceConstants.BEARER)) {
				  sessionToken = sessionToken.replace(ServiceConstants.BEARER, "");
			  }
			  try {				  			
				  userSessionService.isUserSessionValid(sessionToken);
			  }catch(InternalServerException internalServerException) {
				  httpServletResponse.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			  }catch(UnauthorizedAccessException unauthorizedAccessException) {
				  httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
			  }catch(SessionTimeoutException sessionTimeoutException) {
				  httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
			  }catch(Exception exception) {
				  httpServletResponse.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			  }
		  }
		  if(!ObjectUtils.isEmpty(httpServletResponse.getHeader(APIConstants.CORRELATION_ID_HEADER_KEY))){			  
			  httpServletResponse.setHeader(APIConstants.CORRELATION_ID_HEADER_KEY,httpServletResponse.getHeader(APIConstants.CORRELATION_ID_HEADER_KEY)); 
			
		  }else {
			  httpServletResponse.setHeader(APIConstants.CORRELATION_ID_HEADER_KEY,UUID.randomUUID().toString());
			  log.info(" Request Received withour CORRELATION ID, set to "+httpServletResponse.getHeader(APIConstants.CORRELATION_ID_HEADER_KEY)); 
		  }
		  
		  MDC.put(APIConstants.CORRELATION_ID_HEADER_KEY,httpServletResponse.getHeader(APIConstants.CORRELATION_ID_HEADER_KEY));
		  httpServletResponse.setHeader("Content-Type","application/json");
          httpServletResponse.setHeader(ServiceConstants.ACCESS_CONTROL_ALLOW_ORIGIN_KEY, ServiceConstants.ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
          httpServletResponse.setHeader(ServiceConstants.ACCESS_CONTROL_ALLOW_METHODS_KEY, ServiceConstants.ACCESS_CONTROL_ALLOW_METHODS_VALUE);
          httpServletResponse.setHeader(ServiceConstants.ACCESS_CONTROL_ALLOW_HEADERS_KEY, ServiceConstants.ACCESS_CONTROL_ALLOW_HEADERS_VALUE);
          httpServletResponse.setHeader(ServiceConstants.ACCESS_CONTROL_ALLOW_CREDENTIALS_KEY,ServiceConstants.ACCESS_CONTROL_ALLOW_CREDENTIALS_VALUE);
		  chain.doFilter(httpServletRequest, httpServletResponse);
		  MDC.clear();
		  log.info(" Returning the response  ");
	 }	 
}
