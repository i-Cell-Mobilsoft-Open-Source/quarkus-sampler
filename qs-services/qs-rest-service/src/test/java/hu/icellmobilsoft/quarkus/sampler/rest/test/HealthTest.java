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

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
@DisplayName("Testing mp-health")
class HealthTest {

    private static final String STATUS_JSON_PATH = "status";

    /**
     * Server url like: http://localhost:8083
     */
    @TestHTTPResource
    URL url;

    @Test
    @DisplayName("Testing /q/health")
    void testHealthCheck() {
        String status = HealthCheckResponse.Status.UP.name();
        String responseStatus = RestAssured //
                .given()
                .baseUri(url.toString()) //
                .when()
                .log()
                .all()
                .get("/q/health") //
                .then()
                .log()
                .all() //
                .extract()
                .response()
                .body()
                .jsonPath()
                .get(STATUS_JSON_PATH);

        Assertions.assertEquals(status, responseStatus);
    }
}
