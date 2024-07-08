/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 - 2024 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.java.date.and.time.rest;

import java.time.OffsetDateTime;

import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJavaDateAndTimeRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadateandtime.JavaDateAndTimeResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadateandtime.JavaDateAndTimeType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadateandtime.JavaDateAndTimeUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadateandtime.JavaDateAndTimeUpdateType;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullSource;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.ts.sample.jpa.batch.service.java.date.and.time.rest.base.BaseJavaDateAndTimeRestIT;
import hu.icellmobilsoft.ts.sample.jpa.batch.service.java.date.and.time.rest.provider.BaseDateTimeArgumentsProvider;

/**
 * IT tests for {@link IJavaDateAndTimeRest#putUpdateJavaDateAndTimeEntityWithBatchService}.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing JavaDateAndTimeRest.putUpdateJavaDateAndTimeEntityWithBatchService()")
@Tag(TestSuiteGroup.RESTASSURED)
class PutUpdateJavaDateAndTimeEntityWithBatchServiceIT extends BaseJavaDateAndTimeRestIT {

    @Order(1)
    @NullSource
    @ArgumentsSource(BaseDateTimeArgumentsProvider.class)
    @ExplicitParamInjection
    @ParameterizedTest(name = "[{index}] - baseDateTime: [{0}]")
    void putUpdateJavaDateAndTimeEntityWithBatchServiceChangeEmptyByBaseDateTimeTest(OffsetDateTime baseDateTime) throws BaseException {
        // given
        String javaDateAndTimeId = createJavaDateAndTime(null).getJavaDateAndTime().getJavaDateAndTimeId();

        JavaDateAndTimeUpdateRequest request = createRequest(baseDateTime);

        // when
        JavaDateAndTimeResponse response = getRestClient().putUpdateJavaDateAndTimeEntityWithBatchService(javaDateAndTimeId, request);
        JavaDateAndTimeType javaDateAndTime = response.getJavaDateAndTime();

        // then
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode(), "functionCode");
        Assertions.assertEquals(javaDateAndTimeId, javaDateAndTime.getJavaDateAndTimeId(), "javaDateAndTimeId");
    }

    @Order(2)
    @NullSource
    @ArgumentsSource(BaseDateTimeArgumentsProvider.class)
    @ExplicitParamInjection
    @ParameterizedTest(name = "[{index}] - baseDateTime: [{0}]")
    void putUpdateJavaDateAndTimeEntityWithBatchServiceChangeFilledToEmptyTest(OffsetDateTime baseDateTime) throws BaseException {
        // given
        String javaDateAndTimeId = createJavaDateAndTime(baseDateTime).getJavaDateAndTime().getJavaDateAndTimeId();

        JavaDateAndTimeUpdateRequest request = createRequest(null);

        // when
        JavaDateAndTimeResponse response = getRestClient().putUpdateJavaDateAndTimeEntityWithBatchService(javaDateAndTimeId, request);
        JavaDateAndTimeType javaDateAndTime = response.getJavaDateAndTime();

        // then
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode(), "functionCode");
        Assertions.assertEquals(javaDateAndTimeId, javaDateAndTime.getJavaDateAndTimeId(), "javaDateAndTimeId");
    }

    private JavaDateAndTimeUpdateRequest createRequest(OffsetDateTime baseDateTime) {
        JavaDateAndTimeUpdateType javaDateAndTime = new JavaDateAndTimeUpdateType().withBaseDateTime(baseDateTime);
        JavaDateAndTimeUpdateRequest request = new JavaDateAndTimeUpdateRequest().withJavaDateAndTime(javaDateAndTime);
        request.setContext(DtoHelper.createContext());
        return request;
    }
}
