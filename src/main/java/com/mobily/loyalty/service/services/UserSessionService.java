package com.mobily.loyalty.service.services;

import java.util.Optional;

import javax.ws.rs.InternalServerErrorException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobily.loyalty.service.configs.AppConfigurationLoader;
import com.mobily.loyalty.service.constants.MessageConstants;
import com.mobily.loyalty.service.exceptions.InternalServerException;
import com.mobily.loyalty.service.exceptions.SessionTimeoutException;
import com.mobily.loyalty.service.exceptions.UnauthorizedAccessException;
import com.mobily.loyalty.service.repository.UserSessionRepository;
import com.mobily.loyalty.service.services.entity.UserSessionEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * This class holds the current users sessions
 * This data is queried for user session validity like if no service calls for X minutes, 
 * then session is treated as in-active and user to re-login again
 * When the user login, session details are pushed into the REDIS persistence layer
 * @author Mobily Info Tech (MIT)
 *
 */
@Service
@Slf4j
public class UserSessionService {
	
	/** User Session Repository **/
	@Autowired
	private UserSessionRepository userSessionRepository;
	
	/** Application Configuration Provider **/
	@Autowired
	private AppConfigurationLoader appConfigurationLoader;
	
	/** Split Delimiter **/
	private final static String SPLIT_DELIMTER="\\.";
	
	/** Split Delimiter **/
	private final static String SUBJECT_NAME_KEY="sub";
	
	/**
	 * Adds the User Session, once the user successfully logs into the system
	 * @param userSessionEntity
	 * @return
	 * @throws Exception
	 */
	public UserSessionEntity addUserSession(UserSessionEntity userSessionEntity) throws Exception{
		log.info("New user session addition");
		userSessionRepository.save(userSessionEntity);
		return userSessionEntity;
	}
	
	/**
	 * Updates the user session, after requesting for extending the security token
	 * @param userSessionEntity
	 * @return
	 * @throws Exception
	 */
	public UserSessionEntity updateUserSession(UserSessionEntity userSessionEntity) throws Exception{
		log.info("Updating existing user session");
		userSessionRepository.save(userSessionEntity);
		return userSessionEntity;
	}
	
	/**
	 * Deletes a user session details, once the user logs out or session is invalid
	 * @param userId
	 * @throws Exception
	 */
	public void deleteUserSession(String userId) throws Exception{
		log.info("deleting user session");
		userSessionRepository.deleteById(userId);
	}
	
	/**
	 * Returns the User Session details for a particular session
	 * @param userId
	 * @return
	 * @throws UnauthorizedAccessException
	 */
	public UserSessionEntity getUserSession(String userId) throws UnauthorizedAccessException{
		log.info("querying existing user session");
		if(ObjectUtils.isEmpty(userId)) {
			throw new UnauthorizedAccessException(""," Invalid user id "+userId);
		}
		
		Optional<UserSessionEntity> optUserSessionEntity = userSessionRepository.findById(userId);		
		if(ObjectUtils.isEmpty(optUserSessionEntity) || ObjectUtils.isEmpty(optUserSessionEntity.get())) {
			throw new UnauthorizedAccessException(""," user session does not exist ");
		}
		
		return optUserSessionEntity.get();
	}
	
	/**
	 * Validates the user security token
	 * @param jwtToken
	 * @throws SessionTimeoutException
	 * @throws UnauthorizedAccessException
	 * @throws InternalServerException
	 */
	public void isUserSessionValid(String jwtToken) throws SessionTimeoutException, UnauthorizedAccessException,InternalServerException{
		UserSessionEntity userSessionEntity = getUserSession(decodeJWTToken(jwtToken));
	    isSessionExpired(userSessionEntity);
	    userSessionEntity.setLastAccessTime(System.currentTimeMillis());
	}
	
	/**
	 * decodes the user JWT token
	 * @param jwtToken
	 * @return
	 * @throws SessionTimeoutException
	 * @throws UnauthorizedAccessException
	 * @throws InternalServerException
	 */
	private String decodeJWTToken(String jwtToken) throws SessionTimeoutException, UnauthorizedAccessException,InternalServerException{
		
		String[] split_string = null;
		String base64EncodedHeader = null;
		String base64EncodedBody = null;
		String base64EncodedSignature = null;
		String body = null;
		ObjectMapper objectMapper = null;
		JsonNode jsonNode = null;
		Base64 base64Url = null;
		UserSessionEntity userSessionEntity = null;
		 
		if(ObjectUtils.isEmpty(jwtToken)) {
			throw new UnauthorizedAccessException(""," Not a valid access token ");
		}
		
		try {
				split_string = jwtToken.split(SPLIT_DELIMTER);
				base64EncodedHeader = split_string[0];
				base64EncodedBody = split_string[1];
				base64EncodedSignature = split_string[2];
			    base64Url = new Base64(true);
			    body = new String(base64Url.decode(base64EncodedBody));
	            objectMapper = new ObjectMapper();
	            jsonNode = objectMapper.readTree(body);
	 	        return jsonNode.get(SUBJECT_NAME_KEY).asText();
	 	        
	    }catch(Exception e) {
	      throw new InternalServerException("","Error occurred while decoding the token ");
	    }
	}
	
	/**
	 * checks whether user session timed-out or not, that is there is no requests from client session for a period of X minutes
	 * @param userSessionEntity
	 * @throws SessionTimeoutException
	 * @throws InternalServerException
	 */
	private void isSessionExpired(UserSessionEntity userSessionEntity) throws SessionTimeoutException, InternalServerException{
		
		long lastAccessTime = userSessionEntity.getLastAccessTime();
		long currentTime    = System.currentTimeMillis();
		long allowedSessionTimeout = 120000;
		
		try {			
			if((currentTime-lastAccessTime)>=allowedSessionTimeout) {
				log.info(" User Session TimedOut");
				throw new SessionTimeoutException(MessageConstants.MAPI_401,"Session TimedOut");
			}
		}catch(Exception e) {		
			log.error(" Error occurred while checking for session timeout",e);
			throw new InternalServerException(MessageConstants.MAPI_500,"Error while checking session expiration operation ");
		}				
	}
}
