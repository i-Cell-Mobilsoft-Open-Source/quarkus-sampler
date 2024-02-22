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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.jpa.association.rest.base;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IEmptyEntityRest;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJpaAssociationRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.emptyentity.EmptyEntityType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaassociation.JpaAssociationInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaassociation.JpaAssociationInsertType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaassociation.JpaAssociationResponse;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;

/**
 * Base class for {@link IJpaAssociationRest} tests.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
public abstract class BaseJpaAssociationRestIT extends BaseSampleIT {

    /**
     * Creates a {@link IJpaAssociationRest} rest client.
     * 
     * @return {@link IJpaAssociationRest}.
     */
    protected IJpaAssociationRest getRestClient() {
        return getRestClient(IJpaAssociationRest.class);
    }

    /**
     * Creates an EmptyEntity.
     * 
     * @return the created EmptyEntity DTO.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    protected EmptyEntityType createEmptyEntity() throws BaseException {
        BaseRequest baseRequest = new BaseRequest().withContext(DtoHelper.createContext());
        IEmptyEntityRest restClient = getRestClient(IEmptyEntityRest.class);
        return restClient.postEmptyEntity(baseRequest).getEmptyEntity();
    }

    /**
     * Creates a JpaAssociation.
     * 
     * @param manyToOneId
     *            ID of ManyToOne.
     * @return {@link JpaAssociationResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    protected JpaAssociationResponse createJpaAssociation(String manyToOneId) throws BaseException {
        JpaAssociationInsertType jpaAssociation = new JpaAssociationInsertType().withManyToOneId(manyToOneId);

        JpaAssociationInsertRequest request = new JpaAssociationInsertRequest().withJpaAssociation(jpaAssociation);
        request.setContext(DtoHelper.createContext());

        return getRestClient().postInsertJpaAssociationEntityWithBatchService(request);
    }

}
