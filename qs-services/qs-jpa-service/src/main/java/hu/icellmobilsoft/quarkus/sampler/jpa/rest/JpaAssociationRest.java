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
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJpaAssociationRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaassociation.JpaAssociationInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaassociation.JpaAssociationResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaassociation.JpaAssociationUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.jpa.action.JpaAssociationAction;

/**
 * Implementation of {@link IJpaAssociationRest}.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Model
public class JpaAssociationRest extends BaseRestService implements IJpaAssociationRest {

    @Inject
    JpaAssociationAction jpaAssociationAction;

    @Override
    public JpaAssociationResponse postInsertJpaAssociationEntityWithBatchService(JpaAssociationInsertRequest jpaAssociationInsertRequest)
            throws BaseException {
        String methodName = "postInsertJpaAssociationEntityWithBatchService";
        return wrapPathParam1(jpaAssociationAction::insertJpaAssociation, jpaAssociationInsertRequest, methodName, "jpaAssociationInsertRequest");
    }

    @Override
    public JpaAssociationResponse putUpdateJpaAssociationEntityWithBatchService(String jpaAssociationId,
            JpaAssociationUpdateRequest jpaAssociationUpdateRequest) throws BaseException {
        return wrapPathParam2(
                jpaAssociationAction::updateJpaAssociation,
                jpaAssociationId,
                jpaAssociationUpdateRequest,
                "putUpdateJpaAssociationEntityWithBatchService",
                "jpaAssociationId",
                "jpaAssociationUpdateRequest");
    }

    @Override
    public BaseResponse deleteAllJpaAssociationEntitiesWithBatchService() throws BaseException {
        return wrapNoParam(jpaAssociationAction::deleteAllJpaAssociation, "deleteAllJpaAssociationEntitiesWithBatchService");
    }
}
