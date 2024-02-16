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

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.quarkus.sampler.rest.test.dto.HealthDto;
import io.quarkus.test.common.http.TestHTTPResource;
import io.restassured.RestAssured;

//@QuarkusTest
//@Disabled
//@QuarkusTestResource(value = EtcdResourceProfile.class)
class MPHealthTest {
//
//    private static final String STATUS_JSON_PATH = "status";
//
//    /*
//     * Server url like: http://localhost:8083
//     */
//    @TestHTTPResource
//    URL url;
//
//    @Test
//    @DisplayName("Testing /q/health")
//    void testHealthCheck() throws Exception {
//
//        String status = HealthCheckResponse.Status.UP.name();
//
//        HttpURLConnection connection = null;
//        try {
//            connection = (HttpURLConnection) new URL(url.toString() + "/q/health").openConnection();
//
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
//
//            // Válasz olvasása, ha a kérés sikeres
//            Assertions.assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
//
//            // Válasz megjelenítése
//
//            // JSON-B inicializálása
//            try (Jsonb jsonb = JsonbBuilder.create()) {
//                // Válasz JSON tartalom átalakítása Java objektummá
//                HealthDto myObject = jsonb.fromJson(new InputStreamReader(connection.getInputStream()), HealthDto.class);
//                Assertions.assertEquals(status, myObject.getStatus());
//            }
//
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//    }
//
//    @Test
//    @DisplayName("Testing /q/health with Restassured")
//    void testHealthCheckWithRestAssured() {
//        String status = HealthCheckResponse.Status.UP.name();
//        String responseStatus = RestAssured //
//                .given()
//                .baseUri(url.toString()) //
//                .when()
//                .log()
//                .all()
//                .get("/q/health") //
//                .then()
//                .log()
//                .all() //
//                .extract()
//                .response()
//                .body()
//                .jsonPath()
//                .get(STATUS_JSON_PATH);
//
//        Assertions.assertEquals(status, responseStatus);
//    }
}
