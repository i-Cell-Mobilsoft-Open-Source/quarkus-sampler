/*-
 * #%L
 * Quarkus-sampler
 * %%
 * Copyright (C) 2024 i-Cell Mobilsoft Zrt.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package hu.icellmobilsoft.quarkus.sampler.testsuite.quarkus.jpa.service.test;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jakarta.json.bind.Jsonb;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.dto.common.commonservice.ContextType;
import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.rest.provider.util.JsonbUtil;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.coffee.tool.utils.string.RandomUtil;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IEmptyEntityRest;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJavaBaseTypesRest;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.system.ISystemRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.emptyentity.EmptyEntityResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesResponse;
import hu.icellmobilsoft.quarkus.sampler.testsuite.quarkus.jpa.service.test.dto.HealthDto;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(EtcdResourceProfile.class)
class ServiceTest {

    /**
     * Server url like: http://localhost:8083
     */
    @TestHTTPResource
    URL url;

    @Test
    void testTransactional() throws BaseException {
        EmptyEntityResponse emptyEntityResponse = getRestClient(IEmptyEntityRest.class)
                .postEmptyEntity(new BaseRequest().withContext(createContext()));
        Assertions.assertNotNull(emptyEntityResponse);
        Assertions.assertEquals(FunctionCodeType.OK, emptyEntityResponse.getFuncCode());
        Assertions.assertNotNull(emptyEntityResponse.getEmptyEntity());
        Assertions.assertTrue(StringUtils.isNotBlank(emptyEntityResponse.getEmptyEntity().getEmptyEntityId()));

    }

    @Test
    void testExceptionMapper() {

        IJavaBaseTypesRest javaBaseTypesRestClient = getRestClient(IJavaBaseTypesRest.class);

        try {
            JavaBaseTypesResponse javaBaseTypesResponse = javaBaseTypesRestClient
                    .postInsertJavaBaseTypesEntityWithBatchService(new JavaBaseTypesInsertRequest());
            throw new RuntimeException("Rest endpoint is not validate request!");
        } catch (BaseException e) {
            Assertions.assertEquals(CoffeeFaultType.INVALID_XML, e.getFaultTypeEnum());
        }
    }

    @Test
    void testEtcd() {
        String configValue = ConfigProvider.getConfig().getOptionalValue("hello", String.class).orElse("default");
        Assertions.assertEquals("world", configValue);
    }

    @Test
    @DisplayName("Testing /q/health")
    void testHealthCheck() throws Exception {

        String status = HealthCheckResponse.Status.UP.name();

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url.toString() + "/q/health").openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Válasz olvasása, ha a kérés sikeres
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());

            // Válasz megjelenítése

            // JSON-B inicializálása
            try (Jsonb jsonb = JsonbUtil.getContext()) {
                // Válasz JSON tartalom átalakítása Java objektummá
                HealthDto myObject = jsonb.fromJson(new InputStreamReader(connection.getInputStream()), HealthDto.class);
                Assertions.assertEquals(status, myObject.getStatus());
            }

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Test
    @DisplayName("Testing 200 response at call /versionInfo over RestClient")
    void versionInfo() throws BaseException {
        String testResponse = RestClientBuilder.newBuilder().baseUrl(url).build(ISystemRest.class).versionInfo();
        Assertions.assertNotNull(testResponse);
    }

    private <T> T getRestClient(Class<T> restClientClass) {
        return RestClientBuilder.newBuilder().baseUrl(url).build(restClientClass);
    }

    private static ContextType createContext() {
        ContextType context = new ContextType();
        context.setRequestId(RandomUtil.generateId());
        context.setTimestamp(DateUtil.nowUTCTruncatedToMillis());
        return context;
    }
}
