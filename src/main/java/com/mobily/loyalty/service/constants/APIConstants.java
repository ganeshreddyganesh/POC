package com.mobily.loyalty.service.constants;


/**
 * Defines all constants for APIoperations
 * @author Mobily Info Tech (MIT)
 *
 */
public class APIConstants {

	/** base service URL **/
	public final static String BASE_SERVICE_URL="clientbe/api/v1/";
	
	/** base service URL **/
	public final static String BASE_CONTEXT_PATH="/";
	
	/** loyalty service URL **/
	public final static String LOYALTY_INFO_SERVICE_SECURED_URL= "/clientbe/loyalty/v1/info/";
	
	/** public URL pattern **/
	public final static String PUBLIC_RESOURCES_URL= "/public/**";
	
	/** public context for by passing spring token based security **/
	public final static String PUBLIC_CONTEXT= "/public";
	
	/** loyalty service URL **/
	public final static String LOYALTY_INFO_SERVICE_PUBLIC_URL= "public/loyalty/v1/info";
	
	/** loyalty service URL **/
	public final static String LOYALTY_INFO_SERVICE_DP_PUBLIC_URL= "public/loyalty/dp/v1/info";
	
	/** loyalty service URL **/
	public final static String LOYALTY_INFO_SERVICE_DP_RESP_CODE_PUBLIC_URL= "public/loyalty/dp/v1/code";
	
	/** public URL pattern **/
	public final static String LOYALTY_SERVICE_RESOURCES_CONTEXT= "/clientbe/**";
	
	/** Ping service URL **/
	public final static String PING_URL= "/ping";
	
	/** realm key **/
	public final static String REALM= "/realm";
	
	/** realms key **/
	public final static String REALMS= "/realms";
	
	/** user id header key **/
	public final static String CORRELATION_ID_HEADER_KEY="x-correlation-id";
	
	/** Source Channel header key **/
	public final static String CHANNEL_HEADER_KEY ="x-source-channel";
	
	/** Transaction ID header key **/
	public final static String TRANSACTION_ID_HEADER_KEY ="x-transaction-id";
	
	/** CEM KEY header key **/
	public final static String CEM_KEY_HEADER_KEY ="cem-key";
	
	/** User ID KEY header key **/
	public final static String X_USERID_HEADER_KEY ="x-user_id";
	
	/** User ID KEY header key **/
	public final static String X_USERID_KEY_VALUE ="testuser12345";
		
	/** services consumes format **/
	public final static String CONSUMES_FORMAT="application/json";
	
	/** service response produces format **/
	public final static String PRODUCES_FORMAT="application/json";
	
	/** API status code for resource conflict **/
	public final static int SERVICE_CONFLICT_STATUS_CODE=409;
	
	
	/** API status code for resource conflict **/
	public final static String LANGUAGE_HEADER_KEY="language";
	
	/** APIConstants.DP_SERVICE_URL **/
	public final static String DP_SERVICE_URL = "dpserviceurl";
	
}
