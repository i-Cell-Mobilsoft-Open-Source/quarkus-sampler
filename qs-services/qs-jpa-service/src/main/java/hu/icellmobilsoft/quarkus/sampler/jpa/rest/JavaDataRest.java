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
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.rest.rest.BaseRestService;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJavaDataRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.jpa.action.JavaDataAction;

/**
 * Implementation of {@link IJavaDataRest}.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
@Model
public class JavaDataRest extends BaseRestService implements IJavaDataRest {

    @Inject
    JavaDataAction javaDataAction;

    @Override
    public JavaDataResponse postInsertJavaDataEntityWithBatchService(JavaDataInsertRequest javaDataInsertRequest) throws BaseException {
        String methodName = "postInsertJavaDataEntityWithBatchService";
        return wrapPathParam1(javaDataAction::insertJavaData, javaDataInsertRequest, methodName, "javaDataInsertRequest");
    }

    @Override
    public JavaDataResponse putUpdateJavaDataEntityWithBatchService(String javaDataId, JavaDataUpdateRequest javaDataUpdateRequest)
            throws BaseException {
        return wrapPathParam2(
                javaDataAction::updateJavaData,
                javaDataId,
                javaDataUpdateRequest,
                "putUpdateJavaDataEntityWithBatchService",
                "javaDataId",
                "javaDataUpdateRequest");
    }

    @Override
    public BaseResponse deleteAllJavaDataEntitiesWithBatchService() throws BaseException {
        return wrapNoParam(javaDataAction::deleteAllJavaData, "deleteAllJavaDataEntitiesWithBatchService");
    }
}
