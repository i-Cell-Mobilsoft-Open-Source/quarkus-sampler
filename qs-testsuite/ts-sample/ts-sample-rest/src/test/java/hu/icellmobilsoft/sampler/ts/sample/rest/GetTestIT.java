/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.sampler.ts.sample.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.test.ITestQuarkusSamplerServiceRest;
import hu.icellmobilsoft.quarkus.sampler.dto.test.test.TestResponse;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.sampler.ts.sample.rest.client.IXmlTestQuarkusSamplerServiceRest;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;

/**
 * Sample service {@link ITestQuarkusSamplerServiceRest#getTest(String, Integer, Long, Boolean)} test
 *
 * @author speter555
 * @since 0.1.0
 */
@DisplayName("Testing Sample service get")
@Tag(TestSuiteGroup.JAXRS)
class GetTestIT extends BaseSampleIT {

    @Test
    @DisplayName("test get - json")
    void testGet_json() throws BaseException {
        TestResponse response = getRestClient(ITestQuarkusSamplerServiceRest.class)
                .getTest("testString", Integer.MAX_VALUE, Long.MAX_VALUE, Boolean.TRUE);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("test get - xml")
    void testGet_xml() throws BaseException {
        TestResponse response = getRestClient(IXmlTestQuarkusSamplerServiceRest.class)
                .getTest("testString", Integer.MAX_VALUE, Long.MAX_VALUE, Boolean.TRUE);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }
}
