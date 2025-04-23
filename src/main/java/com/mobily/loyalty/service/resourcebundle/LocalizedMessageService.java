package com.mobily.loyalty.service.resourcebundle;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import com.mobily.loyalty.service.constants.MessageConstants;
import com.mobily.loyalty.service.constants.ServiceConstants;

/**
 * This class provides the localized messages
 * @author Mobily Info Tech (MIT)
 *
 */
@Service
public class LocalizedMessageService {

	/**
	 * returns local message
	 * @param key
	 * @param journey
	 * @param locale
	 * @return
	 */
	public String localMessage(String journey,String key,String locale) {		
		ResourceBundle loyaltyJourneyMessages = ResourceBundle.getBundle(journey+MessageConstants.RESOURCE_BUNDLE_FILE_NAME_SUFFIX, new Locale(locale, ServiceConstants.LOCALE_CODE_VALUE));
    	return loyaltyJourneyMessages.getString(key);
	}
}
