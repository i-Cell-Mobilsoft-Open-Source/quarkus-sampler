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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.java.enumtypes.rest.base;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJavaEnumTypesRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.BaseJavaEnumTypesType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.BatchOperationTypeType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.JavaEnumTypesInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.JavaEnumTypesInsertType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.JavaEnumTypesResponse;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;

/**
 * Base class for {@link IJavaEnumTypesRest} tests.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
public abstract class BaseJavaEnumTypesIT extends BaseSampleIT {

    /**
     * Creates a {@link IJavaEnumTypesRest} rest client.
     *
     * @return {@link IJavaEnumTypesRest}.
     */
    protected IJavaEnumTypesRest getRestClient() {
        return getRestClient(IJavaEnumTypesRest.class);
    }

    /**
     * Creates a JavaEnumTypes.
     *
     * @param javaEnumTypes
     *            {@link JavaEnumTypesInsertType}.
     * @return {@link JavaEnumTypesResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    protected JavaEnumTypesResponse createJavaEnumTypes(JavaEnumTypesInsertType javaEnumTypes) throws BaseException {
        JavaEnumTypesInsertRequest request = new JavaEnumTypesInsertRequest().withJavaEnumTypes(javaEnumTypes);
        request.setContext(DtoHelper.createContext());

        return getRestClient().postInsertJavaEnumTypesEntityWithBatchService(request);
    }

    /**
     * Fill {@link BaseJavaEnumTypesType} with enums.
     * 
     * @param javaEnumTypes
     *            {@link BaseJavaEnumTypesType}.
     */
    protected void fillJavaEnumTypes(BaseJavaEnumTypesType javaEnumTypes) {
        javaEnumTypes.setDefaultEnum(BatchOperationTypeType.INSERT);
        javaEnumTypes.setOrdinalEnum(BatchOperationTypeType.UPDATE);
        javaEnumTypes.setStringEnum(BatchOperationTypeType.DELETE);
    }

}
