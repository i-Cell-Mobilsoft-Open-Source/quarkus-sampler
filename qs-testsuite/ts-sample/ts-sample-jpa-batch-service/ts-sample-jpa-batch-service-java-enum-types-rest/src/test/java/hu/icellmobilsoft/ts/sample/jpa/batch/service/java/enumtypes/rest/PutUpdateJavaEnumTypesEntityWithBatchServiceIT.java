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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.java.enumtypes.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.enums.EnumUtil;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJavaEnumTypesRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.JavaEnumTypesInsertType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.JavaEnumTypesResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.JavaEnumTypesType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.JavaEnumTypesUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.JavaEnumTypesUpdateType;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.ts.sample.jpa.batch.service.java.enumtypes.rest.base.BaseJavaEnumTypesIT;

/**
 * IT tests for {@link IJavaEnumTypesRest#putUpdateJavaEnumTypesEntityWithBatchService}.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing JavaEnumTypesRest.putUpdateJavaEnumTypesEntityWithBatchService()")
@Tag(TestSuiteGroup.RESTASSURED)
class PutUpdateJavaEnumTypesEntityWithBatchServiceIT extends BaseJavaEnumTypesIT {

    @Test
    @Order(1)
    void putUpdateJavaEnumTypesEntityWithBatchServiceChangeEmptyJavaEnumTypesToFilledTest() throws BaseException {
        // given
        String javaEnumTypesId = createJavaEnumTypes(new JavaEnumTypesInsertType()).getJavaEnumTypes().getJavaEnumTypesId();

        JavaEnumTypesUpdateType expectedJavaEnumTypes = new JavaEnumTypesUpdateType();
        fillJavaEnumTypes(expectedJavaEnumTypes);
        JavaEnumTypesUpdateRequest request = createRequest(expectedJavaEnumTypes);

        // when
        JavaEnumTypesResponse response = getRestClient().putUpdateJavaEnumTypesEntityWithBatchService(javaEnumTypesId, request);
        JavaEnumTypesType actualJavaEnumTypes = response.getJavaEnumTypes();

        // then
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode(), "functionCode");
        Assertions.assertAll(
                () -> Assertions
                        .assertTrue(EnumUtil.equalName(expectedJavaEnumTypes.getDefaultEnum(), actualJavaEnumTypes.getDefaultEnum()), "defaultEnum"),
                () -> Assertions
                        .assertTrue(EnumUtil.equalName(expectedJavaEnumTypes.getOrdinalEnum(), actualJavaEnumTypes.getOrdinalEnum()), "ordinalEnum"),
                () -> Assertions
                        .assertTrue(EnumUtil.equalName(expectedJavaEnumTypes.getStringEnum(), actualJavaEnumTypes.getStringEnum()), "stringEnum"),
                () -> Assertions.assertEquals(javaEnumTypesId, actualJavaEnumTypes.getJavaEnumTypesId(), "javaEnumTypesId"));
    }

    @Test
    @Order(2)
    void putUpdateJavaEnumTypesEntityWithBatchServiceChangeFilledJavaEnumTypesToEmptyTest() throws BaseException {
        // given
        JavaEnumTypesInsertType javaEnumTypesInsert = new JavaEnumTypesInsertType();
        fillJavaEnumTypes(javaEnumTypesInsert);

        String javaEnumTypesId = createJavaEnumTypes(javaEnumTypesInsert).getJavaEnumTypes().getJavaEnumTypesId();

        JavaEnumTypesUpdateType expectedJavaEnumTypes = new JavaEnumTypesUpdateType();
        JavaEnumTypesUpdateRequest request = createRequest(expectedJavaEnumTypes);

        // when
        JavaEnumTypesResponse response = getRestClient().putUpdateJavaEnumTypesEntityWithBatchService(javaEnumTypesId, request);
        JavaEnumTypesType actualJavaEnumTypes = response.getJavaEnumTypes();

        // then
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode(), "functionCode");
        Assertions.assertAll(
                () -> Assertions.assertNull(actualJavaEnumTypes.getDefaultEnum(), "defaultEnum"),
                () -> Assertions.assertNull(actualJavaEnumTypes.getOrdinalEnum(), "ordinalEnum"),
                () -> Assertions.assertNull(actualJavaEnumTypes.getStringEnum(), "stringEnum"),
                () -> Assertions.assertEquals(javaEnumTypesId, actualJavaEnumTypes.getJavaEnumTypesId(), "javaEnumTypesId"));
    }

    private JavaEnumTypesUpdateRequest createRequest(JavaEnumTypesUpdateType javaEnumTypes) {
        JavaEnumTypesUpdateRequest request = new JavaEnumTypesUpdateRequest().withJavaEnumTypes(javaEnumTypes);
        request.setContext(DtoHelper.createContext());
        return request;
    }
}
