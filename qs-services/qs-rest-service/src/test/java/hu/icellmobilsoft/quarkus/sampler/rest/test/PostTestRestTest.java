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
package hu.icellmobilsoft.quarkus.sampler.rest.test;

import java.net.URL;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.dto.common.commonservice.ContextType;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.coffee.tool.utils.string.RandomUtil;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.QuarkusSamplerPath;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.test.ITestQuarkusSamplerServiceRest;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleResponse;
import hu.icellmobilsoft.quarkus.sampler.rest.test.restclient.XmlITestQuarkusSamplerServiceRest;
import io.quarkus.test.common.http.TestHTTPResource;
import io.restassured.RestAssured;

/**
 * Quarkus test example for ITestQuarkusSamplerServiceRest interface test.
 */
@QuarkusTest
@QuarkusTestResource(value = EtcdResourceProfile.class)
class PostTestRestTest {

    /**
     * Server url like: http://localhost:8083
     */
    @TestHTTPResource
    URL url;

    @Test
    @DisplayName("test post method in the service with RestAssured")
    void postTestRestWithRestAssured() {

        SampleResponse sampleResponse = RestAssured.given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .queryParam(QuarkusSamplerPath.PARAM_TEST_STRING, RandomUtil.generateId())
                .queryParam(QuarkusSamplerPath.PARAM_TEST_INTEGER, Integer.MAX_VALUE)
                .queryParam(QuarkusSamplerPath.PARAM_TEST_LONG, Long.MAX_VALUE)
                .queryParam(QuarkusSamplerPath.PARAM_TEST_BOOLEAN, Boolean.FALSE)
                .baseUri(url.toString())
                .basePath(QuarkusSamplerPath.TEST_QUARKUS_SAMPLER_SERVICE)
                .body(createBaseRequest())
                .when()
                .log()
                .all()
                .post()
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .response()
                .as(SampleResponse.class);
        Assertions.assertNotNull(sampleResponse);
        Assertions.assertNotNull(sampleResponse.getContext());
        Assertions.assertNotNull(sampleResponse.getContext().getRequestId());
    }

    @Test
    @DisplayName("test post method in the service with RestClient")
    void postTest() throws BaseException {
        SampleResponse testResponse = RestClientBuilder.newBuilder()
                .baseUrl(url)
                .build(ITestQuarkusSamplerServiceRest.class)
                .postTest(createBaseRequest(), "test", 1, 1L, true);
        Assertions.assertNotNull(testResponse);
        Assertions.assertNotNull(testResponse.getContext());
        Assertions.assertNotNull(testResponse.getContext().getRequestId());
    }

    @Test
    @DisplayName("test post method in the service with RestClient With xml")
    void postTestXml() throws BaseException {
        SampleResponse testResponse = RestClientBuilder.newBuilder()
                .baseUrl(url)
                .build(XmlITestQuarkusSamplerServiceRest.class)
                .postTest(createBaseRequest(), "test", 1, 1L, true);
        Assertions.assertNotNull(testResponse);
        Assertions.assertNotNull(testResponse.getContext());
        Assertions.assertNotNull(testResponse.getContext().getRequestId());
    }

    @Test
    @DisplayName("test post method in the service with RestClient With error (dto without ContextType")
    void postTestWithoutContextType() {
        try {
            RestClientBuilder.newBuilder()
                    .baseUrl(url)
                    .build(XmlITestQuarkusSamplerServiceRest.class)
                    .postTest(new BaseRequest(), "test", 1, 1L, true);
        } catch (BaseException e) {
            Assertions.assertSame(CoffeeFaultType.INVALID_XML, e.getFaultTypeEnum());
        }
    }

    private BaseRequest createBaseRequest() {
        return new BaseRequest()
                .withContext(new ContextType().withTimestamp(DateUtil.nowUTCTruncatedToMillis()).withRequestId(RandomUtil.generateId()));
    }

}
