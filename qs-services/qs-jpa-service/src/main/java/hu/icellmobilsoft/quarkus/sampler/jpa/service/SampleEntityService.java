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
package hu.icellmobilsoft.quarkus.sampler.jpa.service;

import java.util.List;

import hu.icellmobilsoft.coffee.jpa.service.BaseService;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.quarkus.sampler.jpa.repository.SampleEntityRepository;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;

/**
 * Service for {@link SampleEntity} querying. Represents only DB operations.
 * 
 * @author imre.scheffer
 * @since 0.1.0
 */
@Model
public class SampleEntityService extends BaseService<SampleEntity> {

    @Inject
    @ThisLogger
    AppLogger log;

    @Inject
    SampleEntityRepository sampleEntityRepository;

    /**
     * Elements associated with status
     *
     * @param status
     *            sample status
     * @return entity
     * @throws BaseException
     *             on error
     */
    public List<SampleEntity> findAllByStatus(SampleStatus status) throws BaseException {
        return wrapListValidated(sampleEntityRepository::findAllByStatus, status, "findAllByStatus", "status");
    }
}
