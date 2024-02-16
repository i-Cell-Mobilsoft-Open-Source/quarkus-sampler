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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.jpa.converter.entity.rest.base;

import java.time.OffsetDateTime;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJpaConverterEntityRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityInsertType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityResponse;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;

/**
 * Base class for {@link IJpaConverterEntityRest} tests.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
public abstract class BaseJpaConverterEntityRestIT extends BaseSampleIT {

    /**
     * Creates a {@link IJpaConverterEntityRest} rest client.
     *
     * @return {@link IJpaConverterEntityRest}.
     */
    protected IJpaConverterEntityRest getRestClient() {
        return getRestClient(IJpaConverterEntityRest.class);
    }

    /**
     * Creates a JpaConverterEntity.
     *
     * @param baseDateTime
     *            base date time.
     * @param baseDays
     *            base days.
     * @return {@link JpaConverterEntityResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    protected JpaConverterEntityResponse createJpaConverterEntity(OffsetDateTime baseDateTime, Integer baseDays) throws BaseException {
        JpaConverterEntityInsertType jpaConverterEntity = new JpaConverterEntityInsertType().withBaseDateTime(baseDateTime).withBaseDays(baseDays);

        JpaConverterEntityInsertRequest request = new JpaConverterEntityInsertRequest().withJpaConverterEntity(jpaConverterEntity);
        request.setContext(DtoHelper.createContext());

        return getRestClient().postInsertJpaConverterEntityEntityWithBatchService(request);
    }
}
