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

import org.apache.commons.collections4.CollectionUtils;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.quarkus.sample.common.util.enums.EnumUtil;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ParamName;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ValidateIncomingParameters;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.MongoSampleEntity;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.mongodb.service.MongoSampleService;

/**
 * Action class for managing projection operations on {@link MongoSampleEntity}. This class demonstrates various MongoDB queries that return
 * projections or filtered results using PanacheMongoRepository.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@ValidateIncomingParameters
public class MongoProjectionAction extends BaseAction {

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
    public MongoProjectionAction(@ThisLogger AppLogger log, MongoSampleService service) {
        this.log = log;
        this.service = service;
    }

    /**
     * Finds entities between the specified date range and returns a list of projections.
     *
     * @param fromString
     *            the start date of the range (inclusive)
     * @param toString
     *            the end date of the range (inclusive)
     * @return a BaseResponse containing the list of projections
     */
    public BaseResponse getAllBetweenWithProjection(@ParamName("from") String fromString, @ParamName("to") String toString) throws BaseException {

        OffsetDateTime from = DateUtil.tryToParseAbsoluteRelativeDate(fromString);
        OffsetDateTime to = DateUtil.tryToParseAbsoluteRelativeDate(toString);

        log.info("getAllBetweenWithProjection: from={0},to={1}", from, to);
        List<MongoSampleEntity> result = service.findByUsernameAndManagementCodeAndStatusAndTimestampBetween("test_user", 1, null, from, to);
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("Found {0} items", result.size());
            result.forEach(
                    r -> log.info(
                            "Projection: id={0}, userName={1}, managementCode={2}, status={3}",
                            r.getId(),
                            r.getUserName(),
                            r.getManagementCode(),
                            r.getStatus()));
        } else {
            log.info("No items found");
        }

        return createBaseResponse();
    }

    /**
     * Finds entities by their status and returns a list of projections.
     *
     * @param status
     *            the status to filter entities by
     * @return a BaseResponse containing the list of projections
     */
    public BaseResponse getAllByStatusProjection(@ParamName("status") SampleStatusEnumType status) {
        log.info("projectByStatus: status={0}", status);
        SampleStatus sampleStatus = EnumUtil.convert(status, SampleStatus.class);
        List<MongoSampleEntity> result =
                service.findByUsernameAndManagementCodeAndStatusAndTimestampBetween("test_user", 1, sampleStatus, null, null);
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("Found {0} items", result.size());
            result.forEach(r -> log.info("Projection: id={0}, status={1}, inputValue={2}", r.getId(), r.getStatus(), r.getInputValue()));
        } else {
            log.info("No items found");
        }
        return createBaseResponse();
    }

    /**
     * Gets count of entities by status.
     *
     * @param status
     *            the status to count entities by
     * @return a BaseResponse containing the count
     */
    public BaseResponse getCountByStatus(@ParamName("status") SampleStatusEnumType status) {
        log.info("countByStatus: status={0}", status);
        SampleStatus sampleStatus = EnumUtil.convert(status, SampleStatus.class);
        long count = service.findAll().stream().filter(e -> e.getStatus() == sampleStatus).count();
        log.info("Found {0} items with status {1}", count, status);
        return createBaseResponse();
    }

    /**
     * Gets distinct user names from all entities.
     *
     * @return a BaseResponse containing the list of distinct user names
     */
    public BaseResponse getDistinctUserNames() {
        log.info("Getting distinct user names...");
        List<String> userNames = service.findAll().stream().map(MongoSampleEntity::getUserName).distinct().sorted().toList();
        if (CollectionUtils.isNotEmpty(userNames)) {
            log.info("Found {0} distinct user names: {1}", userNames.size(), String.join(", ", userNames));
        } else {
            log.info("No user names found");
        }
        return createBaseResponse();
    }
}
