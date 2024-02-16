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
package hu.icellmobilsoft.quarkus.sampler.jpa.action;

import java.time.LocalDate;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.jpa.annotation.MyTransactional;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.test.test.TestResponse;
import hu.icellmobilsoft.quarkus.sampler.jpa.service.SampleEntityService;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleValue;

/**
 * Service for JPA querying. Represents only DB operations.
 * 
 * @author imre.scheffer
 * @since 0.1.0
 */
@Model
public class JpaSampleGetAction extends BaseAction {

    @Inject
    SampleEntityService sampleEntityService;

    /**
     * Dummy sample reponse
     * 
     * @return SampleResponse Sample response with random id
     * @throws BaseException
     *             if error
     */
    @MyTransactional
    public TestResponse sample() throws BaseException {

        var none = sampleEntityService.findOptionalById("4HAC3YGW0EGX0503", SampleEntity.class);

        SampleEntity entity = new SampleEntity();
        entity.setStatus(SampleStatus.PROCESSING);
        entity.setModLocalDate(LocalDate.now());
        entity.setInputValue("value");
        entity.setValue(SampleValue.VALUE_A);
        entity = sampleEntityService.save(entity);
        TestResponse response = new TestResponse();
        handleSuccessResultType(response);
        return response;
    }
}
