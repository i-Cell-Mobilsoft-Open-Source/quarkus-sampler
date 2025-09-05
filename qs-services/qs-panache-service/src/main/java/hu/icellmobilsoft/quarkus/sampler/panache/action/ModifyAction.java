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
package hu.icellmobilsoft.quarkus.sampler.panache.action;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import org.apache.commons.collections.CollectionUtils;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.jpa.helper.TransactionHelper;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sample.common.util.enums.EnumUtil;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ParamName;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ValidateIncomingParameters;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleContainerEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.panache.service.SampleContainerEntityService;
import hu.icellmobilsoft.quarkus.sampler.panache.service.SampleEntityService;

/**
 * Action class for managing modification operations on {@link SampleEntity}. This class demonstrates various database transactions, including
 * creation and bulk update of entities using Panache and transaction management.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@ValidateIncomingParameters
public class ModifyAction extends BaseAction {

    AppLogger log;
    SampleEntityService service;
    TransactionHelper transactionHelper;
    SampleContainerEntityService containerService;

    /**
     * Constructor with dependencies.
     *
     * @param log
     *            Logger instance for logging messages.
     * @param service
     *            Service for managing SampleEntity operations.
     * @param containerService
     *            Service for managing SampleContainerEntity operations.
     * @param transactionHelper
     *            Helper for managing transactions.
     */
    public ModifyAction(@ThisLogger AppLogger log, SampleEntityService service, SampleContainerEntityService containerService, TransactionHelper transactionHelper) {
        this.log = log;
        this.service = service;
        this.containerService = containerService;
        this.transactionHelper = transactionHelper;
    }

    /**
     * Creates a new SampleEntity, modifies its status, and saves the changes within transactional scopes.
     *
     * @return A BaseResponse indicating the result of the operation.
     * @throws BaseException
     *             if any operation fails.
     */
    public BaseResponse putModify() throws BaseException {

        log.info("Creating SampleEntity...");
        SampleEntity se = new SampleEntity();
        se.setStatus(SampleStatus.PROCESSING);
        se = saveSampleEntity(se);
        log.info("SampleEntity created: " + se);

        log.info("Updating SampleEntity status to DONE...");
        se.setStatus(SampleStatus.DONE);
        se = saveSampleEntity(se);
        log.info("SampleEntity updated: " + se);

        return createBaseResponse();
    }

    /**
     * Bulk updates the status of multiple SampleEntity records identified by their IDs.
     *
     * @param ids
     *            List of SampleEntity IDs to update.
     * @param status
     *            New status to set for the specified entities.
     * @return A BaseResponse indicating the result of the operation.
     * @throws BaseException
     *             if the update operation fails.
     */
    public BaseResponse putBulkUpdateStatus(@ParamName("ids") List<String> ids, @ParamName("status") SampleStatusEnumType status)
            throws BaseException {

        log.info("Updating SampleEntities...");
        int updatedCount = transactionHelper.executeWithTransaction(() -> service.bulkUpdateStatus(ids, EnumUtil.convert(status, SampleStatus.class)));
        log.info("SampleEntities updated: " + updatedCount);

        return createBaseResponse();
    }

    /**
     * Attempts to delete a non-existing SampleEntity to demonstrate exception handling.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse deleteFailed() {
        try {
            transactionHelper.executeWithTransaction(() -> service.deleteById("NOT_EXISTING")); // Intentional failure to test exception handling
        } catch (Exception e) {
            log.error("Failed to delete sampleEntity!", e);
        }
        return createBaseResponse();
    }

    /**
     * Deletes a SampleEntity by its ID within a transactional scope.
     *
     * @param id
     *            The ID of the entity to delete.
     * @return A BaseResponse indicating the result of the operation.
     * @throws BaseException
     *             if the delete operation fails.
     */
    public BaseResponse deleteById(@ParamName("id") String id) throws BaseException {
        transactionHelper.executeWithTransaction(() -> service.deleteById(id));
        return createBaseResponse();
    }

    /**
     * Finds the first SampleContainerEntity, deletes it, and also deletes the associated SampleEntity within a transactional scope.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse deleteFirstFind() {

        try {
            List<SampleContainerEntity> all = containerService.findAll();
            if (CollectionUtils.isNotEmpty(all)) {
                transactionHelper.executeWithTransaction(() -> {
                    var first = all.getFirst();
                    containerService.delete(first);
                    service.deleteById(first.getSampleEntity().getId());
                });
            } else {
                log.warn("No SampleContainerEntity entries found to delete.");
            }
        } catch (Exception e) {
            log.error("Failed to delete first found entity!", e);
        }

        return createBaseResponse();
    }

    /**
     * Saves a {@link SampleEntity} within a transactional scope.
     *
     * @param sampleEntity
     *            The entity to save.
     * @return The saved entity.
     * @throws BaseException
     *             if the save operation fails.
     */
    private SampleEntity saveSampleEntity(@ParamName("sampleEntity") SampleEntity sampleEntity) throws BaseException {
        return transactionHelper.executeWithTransaction(() -> service.save(sampleEntity));
    }
}
