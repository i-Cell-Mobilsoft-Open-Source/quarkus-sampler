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
package hu.icellmobilsoft.quarkus.sampler.rest.test.localization;

import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.TechnicalFault;
import hu.icellmobilsoft.coffee.tool.utils.marshalling.MarshallingUtil;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.dto.exception.enums.FaultType;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.ServicePath;
import hu.icellmobilsoft.quarkus.sampler.common.rest.header.ProjectHeader;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

//@QuarkusTest
//@DisplayName("Testing Rest not found error")
class RestNotFoundErrorTest {
//
//    private static final String errorMsgHu = "Nem létező végpont!";
//    private static final String errorMsgDefault = "Non-existing endpoint!";
//
//    /**
//     * Server url
//     */
//    @TestHTTPResource
//    URL url;
//
//    @Test
//    @DisplayName("Testing /versionInfo1111 what is not alive endpoint Without Language header")
//    void versionInfo11111RestAssuredWithoutLanguageHeader() {
//        TechnicalFault technicalFault = RestAssured.given() //
//                .header("Accept", "application/json")
//                .baseUri(url.toString())
//                .when()
//                .log()
//                .all() //
//                .request()
//                .accept("application/xml")
//                .get(ServicePath.VERSION_INFO + "1111") //
//                .then()
//                .log()
//                .all() //
//                .statusCode(404) //
//                .extract()
//                .as(TechnicalFault.class);
//        Assertions.assertNotNull(technicalFault);
//        Assertions.assertEquals(errorMsgDefault, technicalFault.getMessage());
//        Assertions.assertEquals(FaultType.REST_NOT_FOUND.name(), technicalFault.getFaultType());
//
//    }
//
//    @Test
//    @DisplayName("Testing /versionInfo1111 what is not alive endpoint with HU header")
//    void versionInfo11111RestAssuredWithHULanguageHeader() throws Exception {
//        String responseText = RestAssured.given() //
//                .header("Accept", "application/json")
//                .header(ProjectHeader.HEADER_LANGUAGE, "HU")
//                .baseUri(url.toString())
//                .when()
//                .log()
//                .all() //
//                .request()
//                .accept("application/xml")
//                .get(ServicePath.VERSION_INFO + "1111") //
//                .then()
//                .log()
//                .all() //
//                .statusCode(404) //
//                .extract()
//                .asString();
//        Assertions.assertNotNull(responseText);
//        TechnicalFault technicalFault = MarshallingUtil.unmarshall(responseText, TechnicalFault.class);
//        Assertions.assertNotNull(technicalFault);
//        Assertions.assertEquals(errorMsgHu, technicalFault.getMessage());
//        Assertions.assertEquals(FaultType.REST_NOT_FOUND.name(), technicalFault.getFaultType());
//
//    }
}
