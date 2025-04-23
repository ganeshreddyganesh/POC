package com.mobily.loyalty.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mobily.loyalty.service.services.entity.UserSessionEntity;

/**
 * Reposiry for all CRUD operations w.r.t User Session records
 * @author Mobily Info Tech (MIT)
 *
 */
@Repository
public interface UserSessionRepository extends CrudRepository<UserSessionEntity,String> { 
    
} 
