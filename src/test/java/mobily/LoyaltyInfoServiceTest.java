package mobily;

import com.mobily.loyalty.service.configs.AppConfigurationLoader;
import com.mobily.loyalty.service.model.ExtraInfo;
import com.mobily.loyalty.service.model.LoyaltyInfoRequest;
import com.mobily.loyalty.service.serviceclient.ServiceClient;
import com.mobily.loyalty.service.services.LoyaltyInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoyaltyInfoServiceTest {

    @Autowired
    private LoyaltyInfoService loyaltyInfoService;

    @Autowired
    private AppConfigurationLoader appConfigurationLoader;

    @Test
    public void testGetLoyaltyInformation_validRequest_shouldReturnLoyaltyInfo() {
        try {
            LoyaltyInfoRequest request = new LoyaltyInfoRequest();
            request.setVersion("1.0");
            request.setLanguage("EN");

            ExtraInfo extraInfo = new ExtraInfo();
            extraInfo.setKey("ACCOUNT_NUMBER");
            extraInfo.setValue("1234567890");

            List<ExtraInfo> extraInfos = new ArrayList<>();
            extraInfos.add(extraInfo);
            request.setExtraInfo(extraInfos);

            Map<String, String> headers = new HashMap<>();
            headers.put("X-Correlation-ID", UUID.randomUUID().toString());

            Object response = loyaltyInfoService.getLoyaltyInformation(request, headers);
            assertNotNull(response);

        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
