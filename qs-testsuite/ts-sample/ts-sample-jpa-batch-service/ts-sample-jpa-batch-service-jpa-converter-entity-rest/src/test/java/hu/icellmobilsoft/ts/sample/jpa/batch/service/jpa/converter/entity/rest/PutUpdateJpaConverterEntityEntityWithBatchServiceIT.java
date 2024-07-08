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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.jpa.converter.entity.rest;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJpaConverterEntityRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityUpdateType;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.ts.sample.jpa.batch.service.jpa.converter.entity.rest.base.BaseJpaConverterEntityRestIT;

/**
 * IT tests for {@link IJpaConverterEntityRest#putUpdateJpaConverterEntityEntityWithBatchService}.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing JpaConverterEntityRest.putUpdateJpaConverterEntityEntityWithBatchService()")
@Tag(TestSuiteGroup.RESTASSURED)
class PutUpdateJpaConverterEntityEntityWithBatchServiceIT extends BaseJpaConverterEntityRestIT {

    private static final OffsetDateTime BASE_DATE_TIME = OffsetDateTime.now(ZoneId.of("UTC"));
    private static final int BASE_DAYS = 5;

    @Test
    @Order(1)
    void putUpdateJpaConverterEntityEntityWithBatchServiceChangeEmptyJpaConverterEntityToFilledTest() throws BaseException {
        // given
        String jpaConverterEntityId = createJpaConverterEntity(null, null).getJpaConverterEntity().getJpaConverterEntityId();

        JpaConverterEntityUpdateType jpaConverterEntityUpdateType = new JpaConverterEntityUpdateType() //
                .withBaseDateTime(BASE_DATE_TIME)
                .withBaseDays(BASE_DAYS);
        JpaConverterEntityUpdateRequest request = createRequest(jpaConverterEntityUpdateType);

        // when
        JpaConverterEntityResponse response = getRestClient().putUpdateJpaConverterEntityEntityWithBatchService(jpaConverterEntityId, request);
        JpaConverterEntityType actualJpaConverterEntity = response.getJpaConverterEntity();

        // then
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode(), "functionCode");
        Assertions.assertEquals(jpaConverterEntityId, actualJpaConverterEntity.getJpaConverterEntityId(), "jpaConverterEntityId");

    }

    @Test
    @Order(2)
    void putUpdateJpaConverterEntityEntityWithBatchServiceChangeFilledJpaConverterEntityToEmptyTest() throws BaseException {
        // given
        String jpaConverterEntityId = createJpaConverterEntity(BASE_DATE_TIME, BASE_DAYS).getJpaConverterEntity().getJpaConverterEntityId();

        JpaConverterEntityUpdateType jpaConverterEntityUpdateType = new JpaConverterEntityUpdateType();
        JpaConverterEntityUpdateRequest request = createRequest(jpaConverterEntityUpdateType);

        // when
        JpaConverterEntityResponse response = getRestClient().putUpdateJpaConverterEntityEntityWithBatchService(jpaConverterEntityId, request);
        JpaConverterEntityType actualJpaConverterEntity = response.getJpaConverterEntity();

        // then
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode(), "functionCode");
        Assertions.assertEquals(jpaConverterEntityId, actualJpaConverterEntity.getJpaConverterEntityId(), "jpaConverterEntityId");
    }

    private JpaConverterEntityUpdateRequest createRequest(JpaConverterEntityUpdateType jpaConverterEntity) {
        JpaConverterEntityUpdateRequest request = new JpaConverterEntityUpdateRequest().withJpaConverterEntity(jpaConverterEntity);
        request.setContext(DtoHelper.createContext());
        return request;
    }
}
