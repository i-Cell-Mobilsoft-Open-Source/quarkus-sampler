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
package hu.icellmobilsoft.quarkus.sampler.jpa.rest;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.rest.rest.BaseRestService;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJavaBaseTypesRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.jpa.action.JavaBaseTypesAction;

/**
 * Implementation of {@link IJavaBaseTypesRest}.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Model
public class JavaBaseTypesRest extends BaseRestService implements IJavaBaseTypesRest {

    @Inject
    JavaBaseTypesAction javaBaseTypesAction;

    @Override
    public JavaBaseTypesResponse postInsertJavaBaseTypesEntityWithBatchService(JavaBaseTypesInsertRequest javaBaseTypesInsertRequest)
            throws BaseException {
        String methodName = "postInsertJavaBaseTypesEntityWithBatchService";
        return wrapPathParam1(javaBaseTypesAction::insertJavaBaseTypes, javaBaseTypesInsertRequest, methodName, "javaBaseTypesInsertRequest");
    }

    @Override
    public JavaBaseTypesResponse putUpdateJavaBaseTypesEntityWithBatchService(String javaBaseTypesId,
            JavaBaseTypesUpdateRequest javaBaseTypesUpdateRequest) throws BaseException {
        return wrapPathParam2(
                javaBaseTypesAction::updateJavaBaseTypes,
                javaBaseTypesId,
                javaBaseTypesUpdateRequest,
                "putUpdateJavaBaseTypesEntityWithBatchService",
                "javaBaseTypesId",
                "javaBaseTypesUpdateRequest");
    }

    @Override
    public BaseResponse deleteAllJavaBaseTypesEntitiesWithBatchService() throws BaseException {
        return wrapNoParam(javaBaseTypesAction::deleteAllJavaBaseTypes, "deleteAllJavaBaseTypesEntitiesWithBatchService");
    }
}
