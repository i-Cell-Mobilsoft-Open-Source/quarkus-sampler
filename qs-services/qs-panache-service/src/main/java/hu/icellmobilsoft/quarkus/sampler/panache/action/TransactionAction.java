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

import java.time.LocalDateTime;

import jakarta.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.RandomUtils;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.jpa.helper.TransactionHelper;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ValidateIncomingParameters;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleContainerEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleValue;
import hu.icellmobilsoft.quarkus.sampler.panache.service.SampleContainerEntityService;
import hu.icellmobilsoft.quarkus.sampler.panache.service.SampleEntityService;

/**
 * Action class for managing transaction operations on {@link SampleEntity} and {@link SampleContainerEntity}. This class demonstrates various
 * database transactions, including creation of entities using Panache and transaction management.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@ValidateIncomingParameters
public class TransactionAction extends BaseAction {

    AppLogger log;
    SampleEntityService sampleEntityService;
    SampleContainerEntityService sampleContainerEntityService;
    TransactionHelper transactionHelper;

    /**
     * Constructor with dependencies.
     *
     * @param log
     *            Logger instance for logging messages.
     * @param sampleEntityService
     *            Service for managing SampleEntity operations.
     * @param sampleContainerEntityService
     *            Service for managing SampleContainerEntity operations.
     * @param transactionHelper
     *            Helper for managing transactions.
     */
    public TransactionAction(@ThisLogger AppLogger log, SampleEntityService sampleEntityService,
            SampleContainerEntityService sampleContainerEntityService, TransactionHelper transactionHelper) {
        this.log = log;
        this.sampleEntityService = sampleEntityService;
        this.sampleContainerEntityService = sampleContainerEntityService;
        this.transactionHelper = transactionHelper;
    }

    /**
     * Attempts to save a null SampleEntity to demonstrate exception handling.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse postFailedSave() {

        try {
            log.info("Creating a null SampleEntity...");
            sampleEntityService.save(null); // Intentional failure to test exception handling
        } catch (Exception e) {
            log.error("Failed to save null sampleEntity!", e);
        }

        return createBaseResponse();
    }

    /**
     * Attempts to save a SampleEntity without a transaction to demonstrate exception handling.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse postFailedTransaction() {

        try {
            log.info("Creating a new empty SampleEntity...");
            sampleEntityService.save(new SampleEntity()); // Intentional failure, no transaction, to test exception handling
        } catch (Exception e) {
            log.error("Failed to save new empty sampleEntity!", e);
        }

        return createBaseResponse();
    }

    /**
     * Saves a new SampleEntity within a transaction.
     *
     * @return A BaseResponse indicating the result of the operation.
     * @throws BaseException
     *             if any exception occurs during the transaction.
     */
    public BaseResponse postSuccessSave() throws BaseException {

        log.info("Creating a new SampleEntity in transaction...");
        SampleEntity se = transactionHelper.executeWithTransaction(() -> sampleEntityService.save(createSampleEntity()));
        log.info("SampleEntity created: " + se);

        return createBaseResponse();
    }

    /**
     * Saves a new SampleContainerEntity with an associated SampleEntity within a transaction.
     *
     * @return A BaseResponse indicating the result of the operation.
     * @throws BaseException
     *             if any exception occurs during the transaction.
     */
    public BaseResponse postSuccessSaveWithContainer() throws BaseException {

        log.info("Creating a new SampleContainerEntity with SampleEntity in transaction...");
        SampleContainerEntity sce = transactionHelper.executeWithTransaction(() -> {
            var ise = sampleEntityService.save(createSampleEntity());
            return sampleContainerEntityService.save(createSampleContainerEntity(ise));
        });
        log.info("SampleContainerEntity with SampleEntity created: " + sce);

        return createBaseResponse();
    }

    private SampleEntity createSampleEntity() {
        SampleEntity se = new SampleEntity();
        se.setInputValue("Test input " + RandomUtils.secure().randomInt(1, 1000));
        se.setStatus(SampleStatus.PROCESSING);
        se.setValue(SampleValue.VALUE_A);
        LocalDateTime now = LocalDateTime.now();
        se.setLocalDateTime(now);
        se.setModLocalDate(now.toLocalDate());
        return se;
    }

    private SampleContainerEntity createSampleContainerEntity(SampleEntity sampleEntity) {
        SampleContainerEntity sce = new SampleContainerEntity();
        sce.setSampleEntity(sampleEntity);
        return sce;
    }
}
