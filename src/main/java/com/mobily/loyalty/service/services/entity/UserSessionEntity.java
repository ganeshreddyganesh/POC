package com.mobily.loyalty.service.services.entity;


import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 
 
/**
 * user session details model
 * @author Mobily Info Tech (MIT)
 *
 */
@Data 
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "UserSession")  
public class UserSessionEntity { 
  
	/** unique id **/
    @Id
    @Indexed
    private String id; 
    
    /** user session token **/   
    private String token; 

   /** last access time **/
    private long   lastAccessTime;
}

