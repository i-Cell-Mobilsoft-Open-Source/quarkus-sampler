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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.java.base.types.rest.base;

import java.math.BigDecimal;
import java.math.BigInteger;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJavaBaseTypesRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.BaseJavaBaseTypesType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesInsertType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesResponse;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;

/**
 * Base class for {@link IJavaBaseTypesRest} tests.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
public abstract class BaseJavaBaseTypesRestIT extends BaseSampleIT {

    /**
     * Creates a {@link IJavaBaseTypesRest} rest client.
     *
     * @return {@link IJavaBaseTypesRest}.
     */
    protected IJavaBaseTypesRest getRestClient() {
        return getRestClient(IJavaBaseTypesRest.class);
    }

    /**
     * Creates a JavaBaseTypes.
     *
     * @param javaBaseTypes
     *            {@link JavaBaseTypesInsertType}.
     * @return {@link JavaBaseTypesResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    protected JavaBaseTypesResponse createJavaBaseTypes(JavaBaseTypesInsertType javaBaseTypes) throws BaseException {
        JavaBaseTypesInsertRequest request = new JavaBaseTypesInsertRequest().withJavaBaseTypes(javaBaseTypes);
        request.setContext(DtoHelper.createContext());

        return getRestClient().postInsertJavaBaseTypesEntityWithBatchService(request);
    }

    /**
     * Fill {@link BaseJavaBaseTypesType} with Bases.
     *
     * @param javaBaseTypes
     *            {@link BaseJavaBaseTypesType}.
     */
    protected void fillJavaBaseTypes(BaseJavaBaseTypesType javaBaseTypes) {
        javaBaseTypes.setBytePrimitive((byte) 1);
        javaBaseTypes.setByteWrapper((byte) 2);
        javaBaseTypes.setShortPrimitive((short) 3);
        javaBaseTypes.setShortWrapper((short) 4);
        javaBaseTypes.setIntPrimitive(5);
        javaBaseTypes.setIntWrapper(6);
        javaBaseTypes.setLongPrimitive(7L);
        javaBaseTypes.setLongWrapper(8L);
        javaBaseTypes.setFloatPrimitive(9.5f);
        javaBaseTypes.setFloatWrapper(10.5f);
        javaBaseTypes.setDoublePrimitive(11.5);
        javaBaseTypes.setDoubleWrapper(12.5);
        javaBaseTypes.setBooleanPrimitive(true);
        javaBaseTypes.setBooleanWrapper(true);
        javaBaseTypes.setCharPrimitive("A");
        javaBaseTypes.setCharWrapper("B");
        javaBaseTypes.setString("TEST");
        javaBaseTypes.setBigInteger(BigInteger.valueOf(100));
        javaBaseTypes.setBigDecimal(BigDecimal.valueOf(100.25));
    }
}
