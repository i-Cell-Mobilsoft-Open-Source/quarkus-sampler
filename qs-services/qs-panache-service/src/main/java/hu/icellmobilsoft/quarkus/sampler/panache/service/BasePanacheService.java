/*-
 * #%L
 * Quarkus-sampler
 * %%
 * Copyright (C) 2024 - 2025 i-Cell Mobilsoft Zrt.
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

import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;
import hu.icellmobilsoft.quarkus.sampler.common.core.logging.LogMethodEntryAndExit;
import hu.icellmobilsoft.quarkus.sampler.panache.annotation.HandleServiceExceptions;
import hu.icellmobilsoft.quarkus.sampler.panache.annotation.ParamName;
import hu.icellmobilsoft.quarkus.sampler.panache.annotation.TracedMethods;
import hu.icellmobilsoft.quarkus.sampler.panache.annotation.ValidateIncomingParameters;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;

/**
 * Base service class for handling common CRUD operations using PanacheRepository.
 *
 * @param <E>
 *            The entity type, which extends AbstractIdentifiedAuditEntity.
 * @param <R>
 *            The Panache repository type managing the entity.
 */
@LogMethodEntryAndExit
@ValidateIncomingParameters
@HandleServiceExceptions
@TracedMethods
public abstract class BasePanacheService<E extends AbstractIdentifiedAuditEntity, R extends PanacheRepositoryBase<E, String>> {

    @Inject
    R repository;

    /**
     * Saves or updates an entity in the database. If the entity is new, it will be persisted; otherwise, it will be merged.
     *
     * @param entity
     *            The entity to save.
     * @return The saved or updated entity.
     */
    public E save(@ParamName("entity") E entity) {
        var em = repository.getEntityManager();
        E savedEntity = em.merge(entity);
        em.flush();
        em.refresh(savedEntity);
        return savedEntity;
    }

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id
     *            The entity's ID.
     * @return The entity if found, otherwise {@code null}.
     */
    public E findById(@ParamName("id") String id) {
        return repository.findById(id);
    }

    /**
     * Retrieves an entity by its unique identifier as an {@link Optional}.
     *
     * @param id
     *            The entity's ID.
     * @return An {@link Optional} containing the entity if found, otherwise empty.
     */
    public Optional<E> findByIdOptional(@ParamName("id") String id) {
        return repository.findByIdOptional(id);
    }

    /**
     * Retrieves all entities from the database.
     *
     * @return A list of all entities.
     */
    public List<E> findAll() {
        return repository.findAll().list();
    }

    /**
     * Retrieves entities in a paginated form.
     *
     * @param page
     *            The index of the page (0-based).
     * @param size
     *            The number of records per page.
     * @return A paginated list of entities.
     */
    public List<E> findAllPaged(@ParamName("page") int page, @ParamName("size") int size) {
        return repository.findAll().page(Page.of(page, size)).list();
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id
     *            The ID of the entity to delete.
     * @return {@code true} if deletion was successful, otherwise {@code false}.
     */
    public boolean deleteById(@ParamName("id") String id) {
        return repository.deleteById(id);
    }

    /**
     * Deletes the given entity from the database.
     *
     * @param entity
     *            The entity to delete.
     */
    public void delete(@ParamName("entity") E entity) {
        repository.delete(entity);
    }

    /**
     * Counts the number of entities in the database.
     *
     * @return The total count of entities.
     */
    public long count() {
        return repository.count();
    }
}
