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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.java.base.types.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJavaBaseTypesRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesInsertType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesUpdateType;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.ts.sample.jpa.batch.service.java.base.types.rest.base.BaseJavaBaseTypesRestIT;

/**
 * IT tests for {@link IJavaBaseTypesRest#putUpdateJavaBaseTypesEntityWithBatchService}.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing JavaBaseTypesRest.putUpdateJavaBaseTypesEntityWithBatchService()")
@Tag(TestSuiteGroup.RESTASSURED)
class PutUpdateJavaBaseTypesEntityWithBatchServiceIT extends BaseJavaBaseTypesRestIT {

    @Test
    @Order(1)
    void putUpdateJavaBaseTypesEntityWithBatchServiceChangeEmptyJavaBaseTypesToFilledTest() throws BaseException {
        // given
        String javaBaseTypesId = createJavaBaseTypes(new JavaBaseTypesInsertType()).getJavaBaseTypes().getJavaBaseTypesId();

        JavaBaseTypesUpdateType expectedJavaBaseTypes = new JavaBaseTypesUpdateType();
        fillJavaBaseTypes(expectedJavaBaseTypes);
        JavaBaseTypesUpdateRequest request = createRequest(expectedJavaBaseTypes);

        // when
        JavaBaseTypesResponse response = getRestClient().putUpdateJavaBaseTypesEntityWithBatchService(javaBaseTypesId, request);
        JavaBaseTypesType actualJavaBaseTypes = response.getJavaBaseTypes();

        // then
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode(), "functionCode");
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getBytePrimitive(), actualJavaBaseTypes.getBytePrimitive(), "bytePrimitive"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getByteWrapper(), actualJavaBaseTypes.getByteWrapper(), "byteWrapper"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getShortPrimitive(), actualJavaBaseTypes.getShortPrimitive(), "shortPrimitive"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getShortWrapper(), actualJavaBaseTypes.getShortWrapper(), "shortWrapper"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getIntPrimitive(), actualJavaBaseTypes.getIntPrimitive(), "intPrimitive"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getIntWrapper(), actualJavaBaseTypes.getIntWrapper(), "intWrapper"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getLongPrimitive(), actualJavaBaseTypes.getLongPrimitive(), "longPrimitive"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getLongWrapper(), actualJavaBaseTypes.getLongWrapper(), "longWrapper"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getFloatPrimitive(), actualJavaBaseTypes.getFloatPrimitive(), "floatPrimitive"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getFloatWrapper(), actualJavaBaseTypes.getFloatWrapper(), "floatWrapper"),
                () -> Assertions
                        .assertEquals(expectedJavaBaseTypes.getDoublePrimitive(), actualJavaBaseTypes.getDoublePrimitive(), "doublePrimitive"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getDoubleWrapper(), actualJavaBaseTypes.getDoubleWrapper(), "doubleWrapper"),
                () -> Assertions
                        .assertEquals(expectedJavaBaseTypes.isBooleanPrimitive(), actualJavaBaseTypes.isBooleanPrimitive(), "booleanPrimitive"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.isBooleanWrapper(), actualJavaBaseTypes.isBooleanWrapper(), "booleanWrapper"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getCharPrimitive(), actualJavaBaseTypes.getCharPrimitive(), "charPrimitive"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getCharWrapper(), actualJavaBaseTypes.getCharWrapper(), "charWrapper"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getString(), actualJavaBaseTypes.getString(), "string"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getBigInteger(), actualJavaBaseTypes.getBigInteger(), "bigInteger"),
                () -> Assertions.assertEquals(expectedJavaBaseTypes.getBigDecimal(), actualJavaBaseTypes.getBigDecimal(), "bigDecimal"),
                () -> Assertions.assertEquals(javaBaseTypesId, actualJavaBaseTypes.getJavaBaseTypesId(), "javaBaseTypesId"));
    }

    @Test
    @Order(2)
    void putUpdateJavaBaseTypesEntityWithBatchServiceChangeFilledJavaBaseTypesToEmptyTest() throws BaseException {
        // given
        JavaBaseTypesInsertType javaBaseTypesInsert = new JavaBaseTypesInsertType();
        fillJavaBaseTypes(javaBaseTypesInsert);

        String javaBaseTypesId = createJavaBaseTypes(javaBaseTypesInsert).getJavaBaseTypes().getJavaBaseTypesId();

        JavaBaseTypesUpdateType expectedJavaBaseTypes = new JavaBaseTypesUpdateType();
        JavaBaseTypesUpdateRequest request = createRequest(expectedJavaBaseTypes);

        // when
        JavaBaseTypesResponse response = getRestClient().putUpdateJavaBaseTypesEntityWithBatchService(javaBaseTypesId, request);
        JavaBaseTypesType actualJavaBaseTypes = response.getJavaBaseTypes();

        // then
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode(), "functionCode");
        Assertions.assertAll(
                () -> Assertions.assertEquals(0, actualJavaBaseTypes.getBytePrimitive(), "bytePrimitive"),
                () -> Assertions.assertNull(actualJavaBaseTypes.getByteWrapper(), "byteWrapper"),
                () -> Assertions.assertEquals(0, actualJavaBaseTypes.getShortPrimitive(), "shortPrimitive"),
                () -> Assertions.assertNull(actualJavaBaseTypes.getShortWrapper(), "shortWrapper"),
                () -> Assertions.assertEquals(0, actualJavaBaseTypes.getIntPrimitive(), "intPrimitive"),
                () -> Assertions.assertNull(actualJavaBaseTypes.getIntWrapper(), "intWrapper"),
                () -> Assertions.assertEquals(0, actualJavaBaseTypes.getLongPrimitive(), "longPrimitive"),
                () -> Assertions.assertNull(actualJavaBaseTypes.getLongWrapper(), "longWrapper"),
                () -> Assertions.assertEquals(0, actualJavaBaseTypes.getFloatPrimitive(), "floatPrimitive"),
                () -> Assertions.assertNull(actualJavaBaseTypes.getFloatWrapper(), "floatWrapper"),
                () -> Assertions.assertEquals(0, actualJavaBaseTypes.getDoublePrimitive(), "doublePrimitive"),
                () -> Assertions.assertNull(actualJavaBaseTypes.getDoubleWrapper(), "doubleWrapper"),
                () -> Assertions.assertFalse(actualJavaBaseTypes.isBooleanPrimitive(), "booleanPrimitive"),
                () -> Assertions.assertNull(actualJavaBaseTypes.isBooleanWrapper(), "booleanWrapper"),
                () -> Assertions.assertEquals("0", actualJavaBaseTypes.getCharPrimitive(), "charPrimitive"),
                () -> Assertions.assertNull(actualJavaBaseTypes.getCharWrapper(), "charWrapper"),
                () -> Assertions.assertNull(actualJavaBaseTypes.getString(), "string"),
                () -> Assertions.assertNull(actualJavaBaseTypes.getBigInteger(), "bigInteger"),
                () -> Assertions.assertNull(actualJavaBaseTypes.getBigDecimal(), "bigDecimal"),
                () -> Assertions.assertEquals(javaBaseTypesId, actualJavaBaseTypes.getJavaBaseTypesId(), "javaBaseTypesId"));
    }

    private JavaBaseTypesUpdateRequest createRequest(JavaBaseTypesUpdateType javaBaseTypes) {
        JavaBaseTypesUpdateRequest request = new JavaBaseTypesUpdateRequest().withJavaBaseTypes(javaBaseTypes);
        request.setContext(DtoHelper.createContext());
        return request;
    }
}
