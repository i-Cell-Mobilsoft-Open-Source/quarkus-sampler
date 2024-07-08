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
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJavaEnumTypesRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.JavaEnumTypesInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.JavaEnumTypesResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javaenumtypes.JavaEnumTypesUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.jpa.action.JavaEnumTypesAction;

/**
 * Implementation of {@link IJavaEnumTypesRest}.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Model
public class JavaEnumTypesRest extends BaseRestService implements IJavaEnumTypesRest {

    @Inject
    JavaEnumTypesAction javaEnumTypesAction;

    @Override
    public JavaEnumTypesResponse postInsertJavaEnumTypesEntityWithBatchService(JavaEnumTypesInsertRequest javaEnumTypesInsertRequest)
            throws BaseException {
        String methodName = "postInsertJavaEnumTypesEntityWithBatchService";
        return wrapPathParam1(javaEnumTypesAction::insertJavaEnumTypes, javaEnumTypesInsertRequest, methodName, "javaEnumTypesInsertRequest");
    }

    @Override
    public JavaEnumTypesResponse putUpdateJavaEnumTypesEntityWithBatchService(String javaEnumTypesId,
            JavaEnumTypesUpdateRequest javaEnumTypesUpdateRequest) throws BaseException {
        return wrapPathParam2(
                javaEnumTypesAction::updateJavaEnumTypes,
                javaEnumTypesId,
                javaEnumTypesUpdateRequest,
                "putUpdateJavaEnumTypesEntityWithBatchService",
                "javaEnumTypesId",
                "javaEnumTypesUpdateRequest");
    }

    @Override
    public BaseResponse deleteAllJavaEnumTypesEntitiesWithBatchService() throws BaseException {
        return wrapNoParam(javaEnumTypesAction::deleteAllJavaEnumTypes, "deleteAllJavaEnumTypesEntitiesWithBatchService");
    }
}
