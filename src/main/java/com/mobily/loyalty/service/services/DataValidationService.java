package com.mobily.loyalty.service.services;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.mobily.loyalty.service.configs.AppConfiguration;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;

/**
 * Service request data validation
 * @author Mobily Info Tech (MIT)
 *
 */
@Service
public class DataValidationService{
   
  @Autowired
  private AppConfiguration appConfiguration;
  
  /**
   * validates the service request data
   * @param jsonNode
   * @return
   */
  public Set<ValidationMessage> validateJson(JsonNode jsonNode, String validationSchema){
	JsonSchema jsonSchema =  appConfiguration.jsonValidationSchema(validationSchema);
    Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);
    return errors;
  }
}
