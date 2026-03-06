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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.enterprise.inject.Model;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ParamName;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity_;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.panache.dto.SampleEntityProjection;
import hu.icellmobilsoft.quarkus.sampler.panache.repository.SampleEntityRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

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
     * Retrieves projected DTOs for all entities with the given status.
     *
     * @param status
     *            sample status
     * @return A list of projection DTOs matching the given status.
     */
    public List<SampleEntityProjection> findAllByStatusProjection(@ParamName("status") SampleStatus status) {
        return repository.findAllByStatusProjection(status);
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

    /**
     * Retrieves all entities between the given timestamps, using Native query.
     *
     * @param from
     *            Start timestamp.
     * @param to
     *            End timestamp.
     * @return A list of entities in the given date range.
     */
    public List<SampleEntity> getAllBetweenNative(@ParamName("from") OffsetDateTime from, @ParamName("to") OffsetDateTime to) {
        return repository.getAllBetweenNative(from, to);
    }

    /**
     * Bulk updates the status of entities with the given IDs.
     *
     * @param ids
     *            List of entity IDs to update.
     * @param status
     *            New status to set.
     * @return The number of entities updated.
     * @throws BaseException
     *             if an error occurs during the update process.
     */
    public int bulkUpdateStatus(@ParamName("from") List<String> ids, @ParamName("from") SampleStatus status) {
        return repository.bulkUpdateStatus(ids, status);
    }

    /**
     * Dynamic search for SampleEntity based on optional parameters.
     *
     * @param status
     *            Optional status to filter by.
     * @param from
     *            Optional start date for filtering.
     * @param to
     *            Optional end date for filtering.
     * @param page
     *            Page number for pagination (0-based).
     * @param pageSize
     *            Number of items per page.
     * @return A list of SampleEntity objects matching the search criteria.
     */
    public List<SampleEntity> query(@ParamName("status") SampleStatus status, @ParamName("from") OffsetDateTime from,
            @ParamName("to") OffsetDateTime to, @ParamName("page") int page, @ParamName("pageSize") int pageSize) {

        StringBuilder where = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (Objects.nonNull(status)) {
            where.append(" and status = :status");
            params.put("status", status);
        }

        if (Objects.nonNull(from) && Objects.nonNull(to)) {
            where.append(" and creationDate between :from and :to");
            params.put("from", from);
            params.put("to", to);
        } else {
            if (Objects.nonNull(from)) {
                where.append(" and creationDate >= :from");
                params.put("from", from);
            }
            if (Objects.nonNull(to)) {
                where.append(" and creationDate <= :to");
                params.put("to", to);
            }
        }

        return repository.find(where.toString(), Sort.descending(SampleEntity_.CREATION_DATE), params).page(Page.of(page, pageSize)).list();
    }

    /**
     * Dynamic search for SampleEntity based on optional parameters using Criteria API.
     *
     * @param status
     *            Optional status to filter by.
     * @param from
     *            Optional start date for filtering.
     * @param to
     *            Optional end date for filtering.
     * @param page
     *            Page number for pagination (0-based).
     * @param pageSize
     *            Number of items per page.
     * @return A list of SampleEntity objects matching the search criteria.
     */
    public List<SampleEntity> queryCriteria(@ParamName("status") SampleStatus status, @ParamName("from") OffsetDateTime from,
            @ParamName("to") OffsetDateTime to, @ParamName("page") int page, @ParamName("pageSize") int pageSize) {

        EntityManager em = repository.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SampleEntity> cq = cb.createQuery(SampleEntity.class);
        Root<SampleEntity> root = cq.from(SampleEntity.class);

        Predicate predicates = cb.conjunction();
        if (Objects.nonNull(status)) {
            predicates = cb.and(predicates, cb.equal(root.get(SampleEntity_.status), status));
        }
        if (Objects.nonNull(from)) {
            predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get(SampleEntity_.creationDate), from));
        }
        if (Objects.nonNull(to)) {
            predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get(SampleEntity_.creationDate), to));
        }

        cq.where(predicates);
        cq.orderBy(cb.desc(root.get(SampleEntity_.creationDate)));

        return em.createQuery(cq).setFirstResult(page * pageSize).setMaxResults(pageSize).getResultList();
    }

}
