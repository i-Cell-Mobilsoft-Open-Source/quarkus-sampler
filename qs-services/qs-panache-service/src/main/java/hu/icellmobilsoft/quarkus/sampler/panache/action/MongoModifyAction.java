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

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import org.apache.commons.collections.CollectionUtils;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.quarkus.sample.common.util.enums.EnumUtil;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ParamName;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ValidateIncomingParameters;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.MongoSampleEntity;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.enums.SampleValue;
import hu.icellmobilsoft.quarkus.sampler.mongodb.service.MongoSampleService;

/**
 * Action class for managing modification operations on {@link MongoSampleEntity}. This class demonstrates various MongoDB operations, including
 * creation and bulk update of entities using PanacheMongoRepository.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@ValidateIncomingParameters
public class MongoModifyAction extends BaseAction {

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
    public MongoModifyAction(@ThisLogger AppLogger log, MongoSampleService service) {
        this.log = log;
        this.service = service;
    }

    /**
     * Creates a new MongoSampleEntity, modifies its status, and saves the changes.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse putModify() {

        log.info("Creating MongoSampleEntity...");
        MongoSampleEntity entity = new MongoSampleEntity();
        entity.setStatus(SampleStatus.PROCESSING);
        entity.setUserName("test_user");
        entity.setManagementCode(1);
        entity.setTimestamp(OffsetDateTime.now());
        entity.setValue(SampleValue.VALUE_A);
        entity.setInputValue("Test input");
        entity = service.save(entity);
        log.info("MongoSampleEntity created: " + entity);

        log.info("Updating MongoSampleEntity status to DONE...");
        entity.setStatus(SampleStatus.DONE);
        entity = service.save(entity);
        log.info("MongoSampleEntity updated: " + entity);

        return createBaseResponse();
    }

    /**
     * Bulk updates the status of multiple MongoSampleEntity records identified by their IDs.
     *
     * @param ids
     *            List of MongoSampleEntity ObjectIds to update.
     * @param status
     *            New status to set for the specified entities.
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse putBulkUpdateStatus(@ParamName("ids") List<String> ids, @ParamName("status") SampleStatusEnumType status) {

        log.info("Updating MongoSampleEntity entities...");
        SampleStatus sampleStatus = EnumUtil.convert(status, SampleStatus.class);
        int updatedCount = 0;
        for (String id : ids) {
            MongoSampleEntity entity = service.findByIdString(id);
            if (entity != null) {
                entity.setStatus(sampleStatus);
                service.save(entity);
                updatedCount++;
            }
        }
        log.info("MongoSampleEntity entities updated: " + updatedCount);

        return createBaseResponse();
    }

    /**
     * Attempts to delete a non-existing MongoSampleEntity to demonstrate exception handling.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse deleteFailed() {
        try {
            service.deleteByIdString("NOT_EXISTING"); // Intentional failure to test exception handling
        } catch (Exception e) {
            log.error("Failed to delete MongoSampleEntity!", e);
        }
        return createBaseResponse();
    }

    /**
     * Deletes a MongoSampleEntity by its ID.
     *
     * @param id
     *            The ObjectId of the entity to delete (as String).
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse deleteById(@ParamName("id") String id) {
        service.deleteByIdString(id);
        return createBaseResponse();
    }

    /**
     * Finds and deletes a MongoSampleEntity to demonstrate error handling.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse deleteFirstFind() {

        try {
            List<MongoSampleEntity> all = service.findAll();
            if (CollectionUtils.isNotEmpty(all)) {
                var first = all.getFirst();
                service.delete(first);
                log.info("Deleted first found MongoSampleEntity with id: " + first.getId());
            } else {
                log.warn("No MongoSampleEntity entries found to delete.");
            }
        } catch (Exception e) {
            log.error("Failed to delete first found entity!", e);
        }

        return createBaseResponse();
    }
}
