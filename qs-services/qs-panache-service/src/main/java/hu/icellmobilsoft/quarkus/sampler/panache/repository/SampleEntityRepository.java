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

import org.hibernate.jpa.HibernateHints;

import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntityProjection;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity_;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

/**
 * Repository for executing database queries related to {@link SampleEntity}.
 * Supports various query types including JPQL, native SQL, and Panache methods.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
public class SampleEntityRepository implements PanacheRepositoryBase<SampleEntity, String> {

    private static final String FROM_PARAM = "from";
    private static final String TO_PARAM = "to";
    /**
     * hint for application
     */
    private static final String APPLICATION = "application='sample-panache-service', ";
    /**
     * hint for CONTROLLER
     */
    private static final  String CONTROLLER = "controller='', ";
    /**
     * hint for ACTION
     */
    private static final String ACTION = "action=SampleEntityRepository, ";
    /**
     * hint for FRAMEWORK
     */
    private static final String FRAMEWORK = "framework='quarkus-panache', ";
    /**
     * hint for DB_DRIVER
     */
    private static final String DB_DRIVER = "db_driver='h2' ";

    /**
     * selecting entity by status
     * 
     * @param status
     *            to select
     * @return list of sample entities
     */
    public List<SampleEntity> findAllByStatus(SampleStatus status) {
        return find(SampleEntity_.STATUS, status).page(0, 10)
                .withHint(HibernateHints.HINT_COMMENT, APPLICATION + CONTROLLER + ACTION + "route='findAllByStatus', " + FRAMEWORK + DB_DRIVER)
                .list();
    }

    public List<SampleEntity> getAllBetween(OffsetDateTime from, OffsetDateTime to) {
        return find(SampleEntity_.CREATION_DATE + " BETWEEN :from AND :to", Parameters.with(FROM_PARAM, from).and(TO_PARAM, to)).list();
    }

    public List<String> getAllIdsBetween(OffsetDateTime from, OffsetDateTime to) {
        return getEntityManager().createQuery("SELECT se.id FROM SampleEntity se WHERE se.creationDate BETWEEN :from AND :to", String.class)
                .setParameter(FROM_PARAM, from)
                .setParameter(TO_PARAM, to)
                .getResultList();
    }

    public List<SampleEntityProjection> getAllBetweenWithProjection(OffsetDateTime from, OffsetDateTime to) {
        return getEntityManager().createQuery("""
                SELECT new hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntityProjection(se.status, se.value)
                FROM SampleEntity se
                WHERE se.creationDate BETWEEN :from AND :to
                """, SampleEntityProjection.class)
                .setParameter(FROM_PARAM, from)
                .setParameter(TO_PARAM, to)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<SampleEntity> getAllBetweenNative(OffsetDateTime from, OffsetDateTime to) {
        return getEntityManager()
                .createNativeQuery("SELECT * FROM SAMPLE se WHERE se.X__INSDATE BETWEEN :from AND :to", SampleEntity.class)
                .setParameter(FROM_PARAM, from)
                .setParameter(TO_PARAM, to)
                .getResultList();
    }
}
