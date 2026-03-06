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
package hu.icellmobilsoft.sampler.ts.sample.panache;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.panache.IPanacheQueryRest;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;

/**
 * Panache service {@link IPanacheQueryRest} tests
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@DisplayName("Testing Panache service - Query")
@Tag(TestSuiteGroup.JAXRS)
class PanacheQueryRestIT extends BaseSampleIT {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    OffsetDateTime now = DateUtil.nowUTC();
    String from = now.minusDays(1).format(formatter);
    String to = now.plusDays(1).format(formatter);

    @Test
    @DisplayName("Testing getFindFailure()")
    void testGetFindFailure() {
        BaseResponse response = getRestClient(IPanacheQueryRest.class).getFindFailure();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("Testing getAllByStatus()")
    void testGetAllByStatus() {
        BaseResponse response = getRestClient(IPanacheQueryRest.class).getAllByStatus(SampleStatusEnumType.DONE);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("Testing getAllBetween()")
    void testGetAllBetween() throws BaseException {
        BaseResponse response = getRestClient(IPanacheQueryRest.class).getAllBetween(from, to);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("Testing getAllIdsBetween()")
    void testGetCountByStatus() throws BaseException {
        BaseResponse response = getRestClient(IPanacheQueryRest.class).getAllIdsBetween(from, to);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("Testing getAllBetweenNative()")
    void testGetAllBetweenNative() throws BaseException {
        BaseResponse response = getRestClient(IPanacheQueryRest.class).getAllBetweenNative(from, to);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("Testing getAllIdsBetweenSampleEntityCreation()")
    void testGetAllIdsBetweenSampleEntityCreation() throws BaseException {
        BaseResponse response = getRestClient(IPanacheQueryRest.class).getAllIdsBetweenSampleEntityCreation(from, to);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("Testing postQuery()")
    void testPostQuery() throws BaseException {
        BaseResponse response = getRestClient(IPanacheQueryRest.class).postQuery(SampleStatusEnumType.DONE, from, to, 0, 10);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("Testing postQueryCriteria()")
    void testPostQueryCriteria() throws BaseException {
        BaseResponse response = getRestClient(IPanacheQueryRest.class).postQueryCriteria(SampleStatusEnumType.DONE, from, to, 0, 10);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

}
