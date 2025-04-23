package com.mobily.loyalty.service.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;

import lombok.extern.slf4j.Slf4j;

/**
 * This class returns the schema for validating the service API request data
 * @author Mobily Info Tech (MIT)
 *
 */
@Component
@Slf4j
public class AppConfiguration {
	
    /**
     * returns json schema object with name as parameter
     * @param schema
     * @return
     */
    public JsonSchema jsonValidationSchema(String schemaName) {
    	
       log.info("loading validation schema "+schemaName);
        
        return JsonSchemaFactory
                .getInstance( SpecVersion.VersionFlag.V7 )
                .getSchema( getClass().getClassLoader().getResourceAsStream(schemaName) );
    }
}