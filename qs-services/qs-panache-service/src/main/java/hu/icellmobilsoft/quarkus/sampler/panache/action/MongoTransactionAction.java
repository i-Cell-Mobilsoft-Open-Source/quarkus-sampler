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
import java.time.OffsetDateTime;

import jakarta.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.RandomUtils;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ValidateIncomingParameters;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.MongoSampleEntity;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.enums.SampleValue;
import hu.icellmobilsoft.quarkus.sampler.mongodb.service.MongoSampleService;

/**
 * Action class for managing transaction operations on {@link MongoSampleEntity}. This class demonstrates various MongoDB transactions, including
 * creation of entities using PanacheMongoRepository and transaction management.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@ValidateIncomingParameters
public class MongoTransactionAction extends BaseAction {

    @ThisLogger
    AppLogger log;

    MongoSampleService service;

    /**
     * Constructor with dependencies.
     *
     * @param log
     *            Logger instance for logging messages.
     * @param service
     *            Service for managing MongoSampleEntity operations.
     */
    public MongoTransactionAction(@ThisLogger AppLogger log, MongoSampleService service) {
        this.log = log;
        this.service = service;
    }

    /**
     * Attempts to save a null MongoSampleEntity to demonstrate exception handling.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse postFailedSave() {

        try {
            log.info("Creating a null MongoSampleEntity...");
            service.save(null); // Intentional failure to test exception handling
        } catch (Exception e) {
            log.error("Failed to save null MongoSampleEntity!", e);
        }

        return createBaseResponse();
    }

    /**
     * Attempts to save a MongoSampleEntity without proper fields to demonstrate error handling.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse postFailedTransaction() {

        try {
            log.info("Creating a new empty MongoSampleEntity...");
            service.save(new MongoSampleEntity()); // Intentional failure, to test exception handling
        } catch (Exception e) {
            log.error("Failed to save new empty MongoSampleEntity!", e);
        }

        return createBaseResponse();
    }

    /**
     * Saves a new MongoSampleEntity within a transaction.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse postSuccessSave() {

        log.info("Creating a new MongoSampleEntity in transaction...");
        MongoSampleEntity entity = service.save(createMongoSampleEntity());
        log.info("MongoSampleEntity created: " + entity);

        return createBaseResponse();
    }

    private MongoSampleEntity createMongoSampleEntity() {
        MongoSampleEntity entity = new MongoSampleEntity();
        entity.setUserName("test_user_" + RandomUtils.secure().randomInt(1, 1000));
        entity.setManagementCode(RandomUtils.secure().randomInt(1, 100));
        entity.setStatus(SampleStatus.PROCESSING);
        entity.setValue(SampleValue.VALUE_A);
        entity.setInputValue("Test input " + RandomUtils.secure().randomInt(1, 1000));
        entity.setTimestamp(OffsetDateTime.now());
        entity.setLocalDateTime(LocalDateTime.now());
        return entity;
    }
}
