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
package hu.icellmobilsoft.quarkus.sampler.panache.repository;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleContainerEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

/**
 * SQL select collector for {@link SampleContainerEntity} table.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
public class SampleContainerEntityRepository implements PanacheRepositoryBase<SampleContainerEntity, String> {

    private static final String FROM_PARAM = "from";
    private static final String TO_PARAM = "to";

    /**
     * Retrieves all {@link SampleContainerEntity#getId()} where the associated {@link SampleEntity#getCreationDate} falls within the specified range.
     *
     * @param from
     *            Start date for filtering.
     * @param to
     *            End date for filtering.
     * @return A list of matching entity IDs.
     */
    public List<String> getAllIdsBetweenSampleEntityCreation(OffsetDateTime from, OffsetDateTime to) {
        return getEntityManager()
                .createQuery(
                        "SELECT sce.id FROM SampleContainerEntity sce join sce.sampleEntity se WHERE se.creationDate BETWEEN :from AND :to",
                        String.class)
                .setParameter(FROM_PARAM, from)
                .setParameter(TO_PARAM, to)
                .getResultList();
    }

}
