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
package hu.icellmobilsoft.quarkus.sampler.jpa.repository;

import java.util.List;

import jakarta.persistence.QueryHint;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.hibernate.jpa.HibernateHints;

import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;

/**
 * SQL select collector for {@link SampleEntity} table.
 * 
 * @author imre.scheffer
 * @since 0.1.0
 */
@Repository
public interface SampleEntityRepository extends EntityRepository<SampleEntity, String>, CriteriaSupport<SampleEntity> {

    /**
     * hint for application
     */
    String APPLICATION = "application='sample-jpa-service', ";
    /**
     * hint for CONTROLLER
     */
    String CONTROLLER = "controller='', ";
    /**
     * hint for ACTION
     */
    String ACTION = "action=SampleEntityRepository, ";
    /**
     * hint for FRAMEWORK
     */
    String FRAMEWORK = "framework='coffee', ";
    /**
     * hint for DB_DRIVER
     */
    String DB_DRIVER = "db_driver='h2' ";

    /**
     * selecting entity by status
     * 
     * @param status
     *            to select
     * @return list of sample entities
     */
    @Query(value = "SELECT s FROM SampleEntity s WHERE s.status = ?1", hints = @QueryHint(name = HibernateHints.HINT_COMMENT,
            value = APPLICATION + CONTROLLER + ACTION + "route='findAllByStatus', " + FRAMEWORK + DB_DRIVER))
    List<SampleEntity> findAllByStatus(SampleStatus status);

}
