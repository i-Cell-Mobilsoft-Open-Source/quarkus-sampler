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

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.ServicePath;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.system.ISystemRest;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * Quarkus test example for VersionInfo endpoint.
 */
@QuarkusTest
@DisplayName("Testing versionInfo endpoint")
class VersionInfoTest {

    /**
     * Server url
     */
    @TestHTTPResource
    URL url;

    @Test
    @DisplayName("Testing 200 response at call /versionInfo over RestClient")
    void versionInfo() throws BaseException {
        String testResponse = RestClientBuilder.newBuilder().baseUrl(url).build(ISystemRest.class).versionInfo();
        Assertions.assertNotNull(testResponse);
    }

    @Test
    @DisplayName("Testing 200 response at call /versionInfo with RestAssured")
    void versionInfoRestAssured() {
        RestAssured.given() //
                .baseUri(url.toString())
                .when()
                .log()
                .all() //
                .request()
                .accept(ContentType.TEXT)
                .get(ServicePath.VERSION_INFO) //
                .then()
                .log()
                .all() //
                .statusCode(200) //
        ;
    }

}
