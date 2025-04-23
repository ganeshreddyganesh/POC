package com.mobily.loyalty.service.services;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobily.loyalty.service.model.AuditLog;
import com.mobily.loyalty.service.model.LoyaltyInfoRequest;
import com.mobily.loyalty.service.repository.AuditLogRepository;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AuditAspect {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Before("execution(* com.mobily.loyalty.service.services.*.*(..))") // && args(username,..)
    public void logBeforeActivity(JoinPoint jp) {
    	log.info(" IN ASPECT J ==========================================================================================  >"+jp.getArgs()[0]);
    	log.info(" OBJECT ONE IS "+jp.getArgs()[0]);
    	log.info(" OBJECT ONE IS <<<<< >>>>>>>>>> "+jp.getArgs()[1]);
        AuditLog log = new AuditLog();
        log.setUsername("TEST");
        log.setAction("Resource Access");
        log.setTimestamp(new Date());
        LoyaltyInfoRequest loyaltyInquiryRequest = (LoyaltyInfoRequest)jp.getArgs()[0];
        log.setResource(loyaltyInquiryRequest.getJourney());
        log.setDetails(jp.getArgs()[1].toString());     
       
        auditLogRepository.save(log);
    }
    
    //@After("execution(* com.mobily.loyalty.service.services.*.*(..))") // && args(username,..)
    public void logAfterActivity(JoinPoint jp) {
    	log.info(" IN ASPECT J ===$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  >"+jp.getArgs()[0]);
    	log.info(" OBJECT ONE IS "+jp.getArgs()[0]);
    	
        AuditLog log = new AuditLog();
        log.setUsername("TEST");
        log.setAction("Resource Access");
        log.setTimestamp(new Date());
       // MasterResponse masterResponse = (MasterResponse)jp.getArgs()[0];
        log.setResource("KAKAKAKAK");
        log.setDetails("KKAKAKAKA");     
       
        auditLogRepository.save(log);
    }
}
