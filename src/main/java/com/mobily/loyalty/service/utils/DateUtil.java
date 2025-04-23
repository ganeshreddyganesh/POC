package com.mobily.loyalty.service.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Date Util Methods
 * @author Mobily Info Tech (MIT)
 *
 */
public class DateUtil {

	/** Date Pattern **/
	private static final String DATE_PATTERN="dd-MM-yyyy HH:mm:ss.SSS";
	
	
	/**
	 * returns current date format in dd-MM-yyyy HH:mm:ss.SSS format
	 * @return
	 */
	public static String getCurrentDate() {			
		return LocalDateTime.now()
			       .format(DateTimeFormatter.ofPattern(DATE_PATTERN));
	}
}
