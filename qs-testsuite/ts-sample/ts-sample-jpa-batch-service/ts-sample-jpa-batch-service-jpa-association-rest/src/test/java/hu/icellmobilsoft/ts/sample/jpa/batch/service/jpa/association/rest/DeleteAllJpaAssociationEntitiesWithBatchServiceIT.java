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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJpaAssociationRest;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.ts.sample.jpa.batch.service.jpa.association.rest.base.BaseJpaAssociationRestIT;

/**
 * IT tests for {@link IJpaAssociationRest#deleteAllJpaAssociationEntitiesWithBatchService}.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing JpaAssociationRest.deleteAllJpaAssociationEntitiesWithBatchService()")
@Tag(TestSuiteGroup.RESTASSURED)
class DeleteAllJpaAssociationEntitiesWithBatchServiceIT extends BaseJpaAssociationRestIT {

    @Test
    @Order(1)
    void deleteAllJpaAssociationEntitiesWithBatchServiceTest() throws BaseException {
        // given
        createJpaAssociation(null);

        // when
        BaseResponse response = getRestClient().deleteAllJpaAssociationEntitiesWithBatchService();

        // then
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode(), "functionCode");
    }
}
