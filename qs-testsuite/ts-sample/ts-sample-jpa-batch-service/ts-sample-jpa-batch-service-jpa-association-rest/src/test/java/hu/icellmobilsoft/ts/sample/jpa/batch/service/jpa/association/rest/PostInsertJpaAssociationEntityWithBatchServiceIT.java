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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.jpa.association.rest;

import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaassociation.JpaAssociationResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaassociation.JpaAssociationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJpaAssociationRest;
import hu.icellmobilsoft.ts.sample.jpa.batch.service.jpa.association.rest.base.BaseJpaAssociationRestIT;

/**
 * IT tests for {@link IJpaAssociationRest#postInsertJpaAssociationEntityWithBatchService}.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing JpaAssociationRest.postInsertJpaAssociationEntityWithBatchService()")
@Tag(TestSuiteGroup.RESTASSURED)
class PostInsertJpaAssociationEntityWithBatchServiceIT extends BaseJpaAssociationRestIT {

    @Test
    @Order(1)
    void postInsertJpaAssociationEntityWithBatchServiceEmptyManyToOneTest() throws BaseException {
        // given

        // when
        JpaAssociationResponse response = createJpaAssociation(null);
        JpaAssociationType actualJpaAssociation = response.getJpaAssociation();

        // then
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode(), "functionCode");
        Assertions.assertAll(
                () -> Assertions.assertNull(actualJpaAssociation.getManyToOneId(), "manyToOneId"),
                () -> Assertions.assertNotNull(actualJpaAssociation.getJpaAssociationId(), "jpaAssociationId"));
    }

    @Test
    @Order(2)
    void postInsertJpaAssociationEntityWithBatchServiceExistingManyToOneTest() throws BaseException {
        // given
        String manyToOneId = createEmptyEntity().getEmptyEntityId();

        // when
        JpaAssociationResponse response = createJpaAssociation(manyToOneId);
        JpaAssociationType actualJpaAssociation = response.getJpaAssociation();

        // then
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode(), "functionCode");
        Assertions.assertAll(
                () -> Assertions.assertEquals(manyToOneId, actualJpaAssociation.getManyToOneId(), "manyToOneId"),
                () -> Assertions.assertNotNull(actualJpaAssociation.getJpaAssociationId(), "jpaAssociationId"));
    }

}
