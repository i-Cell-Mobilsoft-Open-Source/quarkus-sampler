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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.panache.IPanacheServiceRest;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;

/**
 * Panache service {@link IPanacheServiceRest#postPanacheMethods(BaseRequest)}
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@DisplayName("Testing Panache service postPanacheMethods")
@Tag(TestSuiteGroup.JAXRS)
class PostPanacheMethodsIT extends BaseSampleIT {

    @Test
    @DisplayName("Testing JSON: empty request")
    void testEmptyJsonBaseRequest() throws BaseException {
        BaseResponse response = getRestClient(IPanacheServiceRest.class).postPanacheMethods(new BaseRequest().withContext(DtoHelper.createContext()));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
    }

}
