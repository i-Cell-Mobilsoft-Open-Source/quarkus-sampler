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
import hu.icellmobilsoft.coffee.tool.utils.enums.EnumUtil;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ParamName;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ValidateIncomingParameters;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.MongoSampleEntity;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.mongodb.service.MongoSampleService;

/**
 * Action class for managing query operations on {@link MongoSampleEntity}. This class demonstrates various MongoDB queries using
 * PanacheMongoRepository.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@ValidateIncomingParameters
public class MongoQueryAction extends BaseAction {

    private static final String TEST_USER = "test_user";
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
    public MongoQueryAction(@ThisLogger AppLogger log, MongoSampleService service) {
        this.log = log;
        this.service = service;
    }

    /**
     * Attempts to find a non-existing MongoSampleEntity to demonstrate exception handling.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse getFindFailure() {

        log.info("Trying to find MongoSampleEntity...");
        try {
            service.findByIdString("NOT_EXISTING"); // Intentional failure to test exception handling
        } catch (Exception e) {
            log.error("Failed to find MongoSampleEntity!", e);
        }

        return createBaseResponse();
    }

    /**
     * Finds all MongoSampleEntity entries with the specified status.
     *
     * @param status
     *            the status to filter by
     * @return a BaseResponse containing the result of the operation
     */
    public BaseResponse getAllByStatus(@ParamName("status") SampleStatusEnumType status) {

        log.info("Finding MongoSampleEntity by status: {0}", status);
        SampleStatus sampleStatus = EnumUtil.convert(status, SampleStatus.class);
        List<MongoSampleEntity> result = service.findAll().stream().filter(entity -> entity.getStatus() == sampleStatus).toList();
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("Found {0} MongoSampleEntity entries with {1} status.", result.size(), status);
        } else {
            log.warn("No MongoSampleEntity entries found with {0} status.", status);
        }

        return createBaseResponse();
    }

    /**
     * Finds all MongoSampleEntity entries between the specified date range.
     *
     * @param fromString
     *            the start date
     * @param toString
     *            the end date
     * @return a BaseResponse containing the result of the operation
     */
    public BaseResponse getAllBetween(@ParamName("from") String fromString, @ParamName("to") String toString) throws BaseException {

        OffsetDateTime from = DateUtil.tryToParseAbsoluteRelativeDate(fromString);
        OffsetDateTime to = DateUtil.tryToParseAbsoluteRelativeDate(toString);

        log.info("Finding MongoSampleEntity between: {0} and {1}", from, to);
        List<MongoSampleEntity> result = service.findByUsernameAndManagementCodeAndStatusAndTimestampBetween(TEST_USER, 1, null, from, to);
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("Found {0} MongoSampleEntity entries in the given time range.", result.size());
        } else {
            log.warn("No MongoSampleEntity entries found in the given time range.");
        }

        return createBaseResponse();
    }

    /**
     * Finds all MongoSampleEntity IDs between the specified date range.
     *
     * @param fromString
     *            the start date
     * @param toString
     *            the end date
     * @return a BaseResponse containing the result of the operation
     */
    public BaseResponse getAllIdsBetween(@ParamName("from") String fromString, @ParamName("to") String toString) throws BaseException {

        OffsetDateTime from = DateUtil.tryToParseAbsoluteRelativeDate(fromString);
        OffsetDateTime to = DateUtil.tryToParseAbsoluteRelativeDate(toString);

        log.info("Finding MongoSampleEntity IDs between: {0} and {1}", from, to);
        List<String> ids = service.findByUsernameAndManagementCodeAndStatusAndTimestampBetween(TEST_USER, 1, null, from, to)
                .stream()
                .map(entity -> entity.getId().toString())
                .toList();
        if (CollectionUtils.isNotEmpty(ids)) {
            log.info("Found IDs: {0}", String.join(", ", ids));
        } else {
            log.warn("No MongoSampleEntity IDs found in the given time range.");
        }

        return createBaseResponse();
    }

    /**
     * Queries MongoSampleEntity entries based on status and date range with pagination.
     *
     * @param status
     *            the status to filter by
     * @param fromString
     *            the start date
     * @param toString
     *            the end date
     * @param page
     *            the page number
     * @param size
     *            the number of entries per page
     * @return a BaseResponse containing the result of the operation
     */
    public BaseResponse postQuery(@ParamName("status") SampleStatusEnumType status, @ParamName("from") String fromString,
            @ParamName("to") String toString, @ParamName("page") int page, @ParamName("size") int size) throws BaseException {

        OffsetDateTime from = DateUtil.tryToParseAbsoluteRelativeDate(fromString);
        OffsetDateTime to = DateUtil.tryToParseAbsoluteRelativeDate(toString);

        log.info("postQuery: status={0},from={1},to={2},page={3},size={4}", status, from, to, page, size);
        SampleStatus sampleStatus = EnumUtil.convert(status, SampleStatus.class);
        List<MongoSampleEntity> result = service.findByUsernameAndManagementCodeAndStatusAndTimestampBetween(TEST_USER, 1, sampleStatus, from, to)
                .stream()
                .skip((long) page * size)
                .limit(size)
                .toList();
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("Found {0} items", result.size());
            result.forEach(r -> log.info(r.toString()));
        } else {
            log.info("No items found");
        }

        return createBaseResponse();
    }

    /**
     * Finds all MongoSampleEntity entries by user name and management code.
     *
     * @param userName
     *            the user name to filter by
     * @param managementCode
     *            the management code to filter by
     * @return a BaseResponse containing the result of the operation
     */
    public BaseResponse getByUserAndManagementCode(@ParamName("userName") String userName, @ParamName("managementCode") int managementCode) {
        log.info("Finding MongoSampleEntity by userName: {0}, managementCode: {1}", userName, managementCode);
        List<MongoSampleEntity> result =
                service.findByUsernameAndManagementCodeAndStatusAndTimestampBetween(userName, managementCode, null, null, null);
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("Found {0} MongoSampleEntity entries.", result.size());
            result.forEach(r -> log.info(r.toString()));
        } else {
            log.warn("No MongoSampleEntity entries found with userName={0}, managementCode={1}", userName, managementCode);
        }
        return createBaseResponse();
    }

    /**
     * Gets MongoSampleEntity count by status.
     *
     * @param status
     *            the status to count by
     * @return a BaseResponse containing the count result
     */
    public BaseResponse getCountByStatus(@ParamName("status") SampleStatusEnumType status) {
        log.info("Counting MongoSampleEntity by status: {0}", status);
        SampleStatus sampleStatus = EnumUtil.convert(status, SampleStatus.class);
        long count = service.findAll().stream().filter(entity -> entity.getStatus() == sampleStatus).count();
        log.info("Found {0} MongoSampleEntity entries with status {1}", count, status);
        return createBaseResponse();
    }

    /**
     * Finds latest MongoSampleEntity entries by timestamp.
     *
     * @param limit
     *            number of entries to return
     * @return a BaseResponse containing the result of the operation
     */
    public BaseResponse getLatestEntries(@ParamName("limit") int limit) {
        log.info("Finding latest {0} MongoSampleEntity entries", limit);
        List<MongoSampleEntity> result = service.findAll()
                .stream()
                .filter(entity -> entity.getTimestamp() != null)
                .sorted((e1, e2) -> e2.getTimestamp().compareTo(e1.getTimestamp()))
                .limit(limit)
                .toList();
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("Found {0} latest entries", result.size());
            result.forEach(r -> log.info(r.toString()));
        } else {
            log.info("No MongoSampleEntity entries found");
        }
        return createBaseResponse();
    }
}
