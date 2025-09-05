/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.sampler.ts.sample.panache;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.panache.IPanacheModifyRest;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;

/**
 * Panache service {@link IPanacheModifyRest} tests
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@DisplayName("Testing Panache service - Modify")
@Tag(TestSuiteGroup.JAXRS)
class PanacheModifyRestIT extends BaseSampleIT {

    @Test
    @DisplayName("Testing putModify()")
    void testPutModify() throws BaseException {
        BaseResponse response = getRestClient(IPanacheModifyRest.class).putModify();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("Testing putBulkModifyStatus()")
    void testPutBulkModifyStatus() throws BaseException {
        BaseResponse response = getRestClient(IPanacheModifyRest.class).putBulkUpdateStatus(List.of("1", "2"), SampleStatusEnumType.DONE);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("Testing deleteFailed()")
    void testDeleteFailed() throws BaseException {
        BaseResponse response = getRestClient(IPanacheModifyRest.class).deleteFailed();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("Testing deleteById()")
    void testDeleteById() throws BaseException {
        BaseResponse response = getRestClient(IPanacheModifyRest.class).deleteById("1");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

    @Test
    @DisplayName("Testing deleteFirst()")
    void testDeleteFirst() throws BaseException {
        BaseResponse response = getRestClient(IPanacheModifyRest.class).deleteFirstFind();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

}
