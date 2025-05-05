package com.mobily.loyalty.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;

import com.mobily.loyalty.service.configs.AppConfigurationLoader;
import com.mobily.loyalty.service.constants.APIConstants;
import com.mobily.loyalty.service.exceptions.InternalServerException;
import com.mobily.loyalty.service.exceptions.InvalidRequestException;
import com.mobily.loyalty.service.exceptions.ResourceNotFoundException;
import com.mobily.loyalty.service.model.ExtraInfo;
import com.mobily.loyalty.service.model.LoyaltyInfoRequest;
import com.mobily.loyalty.service.model.LoyaltyInfoResponse;
import com.mobily.loyalty.service.resourcebundle.LocalizedMessageService;
import com.mobily.loyalty.service.serviceclient.ServiceClient;
import com.mobily.loyalty.service.serviceclient.ServiceResponse;
import com.mobily.loyalty.service.services.DataValidationService;
import com.mobily.loyalty.service.services.LoyaltyInfoService;

@ExtendWith(MockitoExtension.class)
public class LoyaltyInfoServiceTest {

    @Mock
    private ServiceClient serviceClient;

    @Mock
    private AppConfigurationLoader appConfigurationLoader;

    @Mock
    private LocalizedMessageService localizedMessageService;

    @Mock
    private DataValidationService dataValidationService;

    @InjectMocks
    private LoyaltyInfoService loyaltyInfoService;

    private LoyaltyInfoRequest request;
    private Map<String, String> headers;

    @BeforeEach
    void setUp() {
        // Initialize test data
        request = new LoyaltyInfoRequest();
        request.setVersion("v1");
        request.setJourney("loyalty");
        request.setOperation("loyalty-inquiry");
        request.setLanguage("EN");
        
        List<ExtraInfo> extraInfo = new ArrayList<>();
        ExtraInfo accountInfo = new ExtraInfo();
        accountInfo.setKey("ACCOUNT_NUMBER");
        accountInfo.setValue("1234567890");
        extraInfo.add(accountInfo);
        request.setExtraInfo(extraInfo);

        // Set up required headers
        headers = new HashMap<>();
        headers.put(APIConstants.CORRELATION_ID_HEADER_KEY, "test-correlation-id");
        headers.put(APIConstants.TRANSACTION_ID_HEADER_KEY, "test-transaction-id");
        headers.put(APIConstants.CEM_KEY_HEADER_KEY, "test-cem-key");
        headers.put(APIConstants.CHANNEL_HEADER_KEY.toLowerCase(), "test-channel");
    }

    @Test
    void testGetLoyaltyInformation_Success() throws Exception {
        // Given
        String mockResponseJson = "{\"transactionId\":\"123\",\"statusCode\":\"200\",\"pointsLeft\":\"100\",\"totalEarnedPoints\":\"500\",\"totalExpiredPoints\":\"100\",\"totalRedeemedPoints\":\"300\",\"redemptionTransLimit\":\"1000\"}";
        ServiceResponse mockServiceResponse = new ServiceResponse();
        mockServiceResponse.setHttpStatusCode(200);
        mockServiceResponse.setResponsePayload(mockResponseJson);

        when(appConfigurationLoader.getLoyaltyInquiryDPURL()).thenReturn("http://test-url");
        when(serviceClient.execute(anyString(), any(HttpMethod.class), anyString(), any())).thenReturn(mockServiceResponse);
        when(dataValidationService.validateJson(any(), anyString())).thenReturn(null);

        // When
        Object response = loyaltyInfoService.getLoyaltyInformation(request, headers);

        // Then
        assertNotNull(response);
        assertTrue(response instanceof LoyaltyInfoResponse);
        LoyaltyInfoResponse loyaltyResponse = (LoyaltyInfoResponse) response;
        assertEquals("123", loyaltyResponse.getTransactionId());
        assertEquals("100", loyaltyResponse.getPointsLeft());
        assertEquals("500", loyaltyResponse.getTotalEarnedPoints());
        assertEquals("100", loyaltyResponse.getTotalExpiredPoints());
        assertEquals("300", loyaltyResponse.getTotalRedeemedPoints());
        assertEquals("1000", loyaltyResponse.getRedemptionTransLimit());
    }

    @Test
    void testGetLoyaltyInformation_MissingMsisdn() {
        // Given
        request.getExtraInfo().clear(); // Remove account number

        // When/Then
        assertThrows(InvalidRequestException.class, () -> {
            loyaltyInfoService.getLoyaltyInformation(request, headers);
        });
    }

    @Test
    void testGetLoyaltyInformation_InvalidLanguage() {
        // Given
        request.setLanguage("INVALID");

        // When/Then
        assertThrows(InvalidRequestException.class, () -> {
            loyaltyInfoService.getLoyaltyInformation(request, headers);
        });
    }

    @Test
    void testGetLoyaltyInformation_ServiceError() throws Exception {
        // Given
        ServiceResponse mockServiceResponse = new ServiceResponse();
        mockServiceResponse.setHttpStatusCode(500);
        mockServiceResponse.setResponsePayload("Internal Server Error");

        when(appConfigurationLoader.getLoyaltyInquiryDPURL()).thenReturn("http://test-url");
        when(serviceClient.execute(anyString(), any(HttpMethod.class), anyString(), any())).thenReturn(mockServiceResponse);
        when(dataValidationService.validateJson(any(), anyString())).thenReturn(null);

        // When/Then
        assertThrows(InternalServerException.class, () -> {
            loyaltyInfoService.getLoyaltyInformation(request, headers);
        });
    }
}