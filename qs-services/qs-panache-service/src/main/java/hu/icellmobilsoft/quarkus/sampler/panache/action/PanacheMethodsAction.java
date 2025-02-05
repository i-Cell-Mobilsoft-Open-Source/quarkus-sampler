/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 - 2024 i-Cell Mobilsoft Zrt.
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

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.jpa.helper.TransactionHelper;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.coffee.tool.utils.validation.ParamValidatorUtil;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleContainerEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.panache.dto.SampleEntityProjection;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.batch.EmptyEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.panache.service.SampleContainerEntityService;
import hu.icellmobilsoft.quarkus.sampler.panache.service.SampleEntityService;

/**
 * Action class for managing operations on {@link EmptyEntity}. This class demonstrates various database transactions, including creation, retrieval,
 * and deletion of entities using Panache and transaction management.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@Model
public class PanacheMethodsAction extends BaseAction {

    private static final String FAILED_TO_DELETE_SAMPLE_ENTITY = "Failed to delete sampleEntity!";

    @Inject
    @ThisLogger
    AppLogger log;

    @Inject
    SampleEntityService sampleEntityService;

    @Inject
    SampleContainerEntityService sampleContainerEntityService;

    @Inject
    TransactionHelper transactionHelper;

    /**
     * Creates an {@link EmptyEntity} and performs various database operations.
     *
     * @param baseRequest
     *            The incoming request containing necessary data.
     * @return {@link BaseResponse} containing the result of the operation.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public BaseResponse postPanacheMethods(BaseRequest baseRequest) throws BaseException {
        ParamValidatorUtil.requireNonNull(baseRequest, "baseRequest");

        BaseResponse response = new BaseResponse();

        try {
            log.info("Creating a new SampleEntity...");
            sampleEntityService.save(null); // Intentional failure to test exception handling
        } catch (Exception e) {
            log.warn("Failed to save sampleEntity!", e);
        }

        // Creating and updating entity status
        SampleEntity se = new SampleEntity();
        se.setStatus(SampleStatus.PROCESSING);
        se = saveSampleEntity(se);

        se.setStatus(SampleStatus.DONE);
        se = saveSampleEntity(se);

        // Querying different data retrieval methods
        log.debug("Querying entities...");
        var now = DateUtil.nowUTC();

        List<SampleEntity> list1 = sampleEntityService.findAllByStatus(SampleStatus.DONE);
        if (CollectionUtils.isNotEmpty(list1)) {
            log.info("Found {0} SampleEntity entries with DONE status.", list1.size());
        } else {
            log.warn("No SampleEntity entries found with DONE status.");
        }

        List<SampleEntity> list2 = sampleEntityService.getAllBetween(now.minusHours(1), now.plusMinutes(1));
        if (CollectionUtils.isEmpty(list2)) {
            log.warn("No SampleEntity entries found in the given time range.");
        }

        List<String> list3 = sampleEntityService.getAllIdsBetween(now.minusHours(1), now.plusMinutes(1));
        if (CollectionUtils.isEmpty(list3)) {
            log.warn("No SampleEntity IDs found in the given time range.");
        }

        List<SampleEntityProjection> list4 = sampleEntityService.getAllBetweenWithProjection(now.minusHours(1), now.plusMinutes(1));
        if (CollectionUtils.isEmpty(list4)) {
            log.warn("No projected SampleEntity entries found in the given time range.");
        }

        // Persisting SampleContainerEntity in a transaction
        log.debug("Saving SampleContainerEntity...");
        var sce = new SampleContainerEntity();
        sce.setSampleEntity(se);
        sce = saveSampleContainerEntity(sce);

        // Querying SampleContainerEntity by SampleEntity creation timestamp
        List<String> list5 = sampleContainerEntityService.getAllIdsBetweenSampleEntityCreation(now.minusHours(1), now.plusMinutes(1));
        if (CollectionUtils.isEmpty(list5)) {
            log.warn("No SampleContainerEntity entries found with SampleEntity creation date in the given range.");
        }

        // Handling deletion operations
        handleDeleteOperations(se, sce);

        handleSuccessResultType(response, baseRequest);
        return response;
    }

    private SampleContainerEntity saveSampleContainerEntity(SampleContainerEntity sce) throws BaseException {
        return transactionHelper.executeWithTransaction(() -> sampleContainerEntityService.save(sce));
    }

    /**
     * Handles deletion operations for SampleEntity and SampleContainerEntity. Ensures transactions and exception handling are correctly managed.
     *
     * @param se
     *            The SampleEntity to delete.
     * @param sce
     *            The SampleContainerEntity associated with the SampleEntity.
     * @throws BaseException
     *             if any deletion operation fails.
     */
    private void handleDeleteOperations(SampleEntity se, SampleContainerEntity sce) throws BaseException {
        transactionHelper.executeWithTransaction(() -> {
            try {
                log.info("Deleting entities...");
                sampleContainerEntityService.delete(sce);
                sampleEntityService.deleteById(se.getId());
            } catch (Exception e) {
                log.warn(FAILED_TO_DELETE_SAMPLE_ENTITY, e);
            }
        });

        try {
            deleteByIdFailed(se);
        } catch (Exception e) {
            log.warn(FAILED_TO_DELETE_SAMPLE_ENTITY, e);
        }
    }

    /**
     * Attempts to delete an entity using its ID, ensuring transactional safety.
     *
     * @param sampleEntity
     *            The entity to delete.
     */
    private void deleteByIdFailed(SampleEntity sampleEntity) {
        sampleEntityService.deleteById(sampleEntity.getId());
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
    private SampleEntity saveSampleEntity(SampleEntity sampleEntity) throws BaseException {
        return transactionHelper.executeWithTransaction(() -> sampleEntityService.save(sampleEntity));
    }
}
