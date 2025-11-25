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
package hu.icellmobilsoft.quarkus.sampler.mongodb.service;

import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;

import org.bson.types.ObjectId;

import hu.icellmobilsoft.quarkus.sampler.common.core.exceptionhandling.HandleServiceExceptions;
import hu.icellmobilsoft.quarkus.sampler.common.core.logging.LogMethodEntryAndExit;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ParamName;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ValidateIncomingParameters;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.AbstractMongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Page;

/**
 * Base service class for handling common CRUD operations using PanacheMongoRepository for MongoDB.
 *
 * @param <E>
 *            The MongoDB entity type that extends AbstractMongoEntity.
 * @param <R>
 *            The PanacheMongoRepository type managing the entity.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@LogMethodEntryAndExit
@ValidateIncomingParameters
@HandleServiceExceptions
public abstract class BasePanacheMongoService<E extends AbstractMongoEntity, R extends PanacheMongoRepository<E>> {

    @Inject
    protected R repository;

    /**
     * Saves or updates an entity in the MongoDB database. If the entity is new, it will be persisted; otherwise, it will be merged.
     *
     * @param entity
     *            The entity to save.
     * @return The saved or updated entity.
     */
    public E save(@ParamName("entity") E entity) {
        repository.persistOrUpdate(entity);
        return entity;
    }

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id
     *            The entity's ObjectId ID.
     * @return The entity if found, otherwise {@code null}.
     */
    public E findById(@ParamName("id") ObjectId id) {
        return repository.findById(id);
    }

    /**
     * Retrieves an entity by its unique identifier as an {@link Optional}.
     *
     * @param id
     *            The entity's ObjectId ID.
     * @return An {@link Optional} containing the entity if found, otherwise empty.
     */
    public Optional<E> findByIdOptional(@ParamName("id") ObjectId id) {
        return Optional.ofNullable(repository.findById(id));
    }

    /**
     * Retrieves all entities from the MongoDB database.
     *
     * @return A list of all entities.
     */
    public List<E> findAll() {
        return repository.listAll();
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
     *            The ObjectId ID of the entity to delete.
     * @return {@code true} if deletion was successful, otherwise {@code false}.
     */
    public boolean deleteById(@ParamName("id") ObjectId id) {
        return repository.deleteById(id);
    }

    /**
     * Deletes the given entity from the MongoDB database.
     *
     * @param entity
     *            The entity to delete.
     */
    public void delete(@ParamName("entity") E entity) {
        repository.delete(entity);
    }

    /**
     * Counts the number of entities in the MongoDB database.
     *
     * @return The total count of entities.
     */
    public long count() {
        return repository.count();
    }

    /**
     * Finds an entity by String ID (converts to ObjectId).
     *
     * @param idString
     *            The entity's ID as String.
     * @return The entity if found, otherwise {@code null}.
     * @since 0.1.0
     */
    public E findByIdString(@ParamName("idString") String idString) {
        try {
            ObjectId id = new ObjectId(idString);
            return repository.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Deletes an entity by String ID (converts to ObjectId).
     *
     * @param idString
     *            The entity's ID as String.
     * @return {@code true} if deletion was successful, otherwise {@code false}.
     * @since 0.1.0
     */
    public boolean deleteByIdString(@ParamName("idString") String idString) {
        try {
            ObjectId id = new ObjectId(idString);
            return repository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
    }
}
