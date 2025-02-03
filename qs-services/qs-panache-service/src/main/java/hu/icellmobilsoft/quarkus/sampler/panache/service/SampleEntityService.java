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

import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntityProjection;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.panache.annotation.ParamName;
import hu.icellmobilsoft.quarkus.sampler.panache.repository.SampleEntityRepository;
import io.quarkus.micrometer.runtime.MicrometerCounted;

/**
 * Service for {@link SampleEntity} querying. Represents only DB operations. Provides methods for fetching SampleEntity objects with different
 * filters.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@Model
public class SampleEntityService extends BasePanacheService<SampleEntity, SampleEntityRepository> {

    /**
     * Retrieves all elements associated with the given status.
     *
     * @param status
     *            sample status
     * @return A list of entities matching the given status.
     */
    @MicrometerCounted(value = "findAllByStatus", description = "Számolja hányszor lett a findAllByStatus meghívva")
    public List<SampleEntity> findAllByStatus(@ParamName("status") SampleStatus status) {
        return repository.findAllByStatus(status);
    }

    /**
     * Retrieves all entities between the given timestamps.
     *
     * @param from
     *            Start timestamp.
     * @param to
     *            End timestamp.
     * @return A list of entities in the given date range.
     */
    public List<SampleEntity> getAllBetween(@ParamName("from") OffsetDateTime from, @ParamName("to") OffsetDateTime to) {
        return repository.getAllBetween(from, to);
    }

    /**
     * Retrieves all entity IDs between the given timestamps.
     *
     * @param from
     *            Start timestamp.
     * @param to
     *            End timestamp.
     * @return A list of entity IDs in the given date range.
     */
    public List<String> getAllIdsBetween(@ParamName("from") OffsetDateTime from, @ParamName("to") OffsetDateTime to) {
        return repository.getAllIdsBetween(from, to);
    }

    /**
     * Retrieves projected DTOs for all entities in the given date range.
     *
     * @param from
     *            Start timestamp.
     * @param to
     *            End timestamp.
     * @return A list of projection DTOs within the given date range.
     */
    public List<SampleEntityProjection> getAllBetweenWithProjection(@ParamName("from") OffsetDateTime from, @ParamName("to") OffsetDateTime to) {
        return repository.getAllBetweenWithProjection(from, to);
    }
}
