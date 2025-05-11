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
package hu.icellmobilsoft.quarkus.sampler.panache.service;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.enterprise.inject.Model;

import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleContainerEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.panache.annotation.ParamName;
import hu.icellmobilsoft.quarkus.sampler.panache.repository.SampleContainerEntityRepository;

/**
 * Service for {@link SampleContainerEntity} querying. Represents only DB operations.
 * 
 * @author balazs.joo
 * @since 0.1.0
 */
@Model
public class SampleContainerEntityService extends BasePanacheService<SampleContainerEntity, SampleContainerEntityRepository> {

    /**
     * Retrieves all {@link SampleContainerEntity#getId()} where the associated {@link SampleEntity#getCreationDate} falls within the specified range.
     *
     * @param from
     *            Start date for filtering.
     * @param to
     *            End date for filtering.
     * @return A list of matching entity IDs.
     */
    public List<String> getAllIdsBetweenSampleEntityCreation(@ParamName("from") OffsetDateTime from, @ParamName("to") OffsetDateTime to) {
        return repository.getAllIdsBetweenSampleEntityCreation(from, to);
    }
}
