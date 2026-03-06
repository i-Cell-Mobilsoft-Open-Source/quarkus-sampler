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
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.panache.service.SampleContainerEntityService;
import hu.icellmobilsoft.quarkus.sampler.panache.service.SampleEntityService;

/**
 * Action class for managing query operations on {@link SampleEntity}. This class demonstrates various database queries using Panache.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@ValidateIncomingParameters
public class QueryAction extends BaseAction {

    AppLogger log;
    SampleEntityService service;
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
     */
    public QueryAction(@ThisLogger AppLogger log, SampleEntityService service, SampleContainerEntityService containerService) {
        this.log = log;
        this.service = service;
        this.containerService = containerService;
    }

    /**
     * Attempts to find a non-existing SampleEntity to demonstrate exception handling.
     *
     * @return A BaseResponse indicating the result of the operation.
     */
    public BaseResponse getFindFailure() {

        log.info("Trying to find SampleEntity...");
        try {
            service.findById("NOT_EXISTING"); // Intentional failure to test exception handling
        } catch (Exception e) {
            log.error("Failed to find sampleEntity!", e);
        }

        return createBaseResponse();
    }

    /**
     * Finds all SampleEntity entries with the specified status.
     *
     * @param status
     *            the status to filter by
     * @return a BaseResponse containing the result of the operation
     */
    public BaseResponse getAllByStatus(@ParamName("status") SampleStatusEnumType status) {

        log.info("Finding SampleEntity by status: {0}", status);
        List<SampleEntity> list1 = service.findAllByStatus(EnumUtil.convert(status, SampleStatus.class));
        if (CollectionUtils.isNotEmpty(list1)) {
            log.info("Found {0} SampleEntity entries with {1} status.", list1.size(), status);
        } else {
            log.warn("No SampleEntity entries found with {0} status.", status);
        }

        return createBaseResponse();
    }

    /**
     * Finds all SampleEntity entries between the specified date range.
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

        log.info("Finding SampleEntity between: {0} and {1}", from, to);
        List<SampleEntity> list = service.getAllBetween(from, to);
        if (CollectionUtils.isNotEmpty(list)) {
            log.info("Found {0} SampleEntity entries in the given time range.", list.size());
        } else {
            log.warn("No SampleEntity entries found in the given time range.");
        }

        return createBaseResponse();
    }

    /**
     * Finds all SampleEntity IDs between the specified date range.
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

        log.info("Finding SampleEntity IDs between: {0} and {1}", from, to);
        List<String> list = service.getAllIdsBetween(from, to);
        if (CollectionUtils.isNotEmpty(list)) {
            log.info("Found IDs: {0}", String.join(", ", list));
        } else {
            log.warn("No SampleEntity IDs found in the given time range.");
        }

        return createBaseResponse();
    }

    /**
     * Finds all SampleEntity entries between the specified date range using a native query.
     *
     * @param fromString
     *            the start date
     * @param toString
     *            the end date
     * @return a BaseResponse containing the result of the operation
     */
    public BaseResponse getAllBetweenNative(@ParamName("from") String fromString, @ParamName("to") String toString) throws BaseException {

        OffsetDateTime from = DateUtil.tryToParseAbsoluteRelativeDate(fromString);
        OffsetDateTime to = DateUtil.tryToParseAbsoluteRelativeDate(toString);

        log.info("Finding SampleEntity (native) between: {0} and {1}", from, to);
        List<SampleEntity> list = service.getAllBetweenNative(from, to);
        if (CollectionUtils.isNotEmpty(list)) {
            log.info("Found {0} SampleEntity entries in the given time range.", list.size());
        } else {
            log.warn("No SampleEntity entries found in the given time range.");
        }

        return createBaseResponse();
    }

    /**
     * Finds all SampleContainerEntity IDs where the associated SampleEntity creation date falls between the specified range.
     *
     * @param fromString
     *            the start date
     * @param toString
     *            the end date
     * @return a BaseResponse containing the result of the operation
     */
    public BaseResponse getAllIdsBetweenSampleEntityCreation(@ParamName("from") String fromString, @ParamName("to") String toString)
            throws BaseException {

        OffsetDateTime from = DateUtil.tryToParseAbsoluteRelativeDate(fromString);
        OffsetDateTime to = DateUtil.tryToParseAbsoluteRelativeDate(toString);

        log.info("Finding SampleContainerEntity IDs between SampleEntity creation: {0} and {1}", from, to);
        List<String> list = containerService.getAllIdsBetweenSampleEntityCreation(from, to);
        if (CollectionUtils.isNotEmpty(list)) {
            log.info("Found IDs: {0}", String.join(", ", list));
        } else {
            log.warn("No SampleContainerEntity IDs found with SampleEntity creation date in the given range.");
        }

        return createBaseResponse();
    }

    /**
     * Queries SampleEntity entries based on status and date range with pagination.
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
        List<SampleEntity> result = service.query(EnumUtil.convert(status, SampleStatus.class), from, to, page, size);
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("Found {0} items", result.size());
            result.forEach(r -> log.info(r.toString()));
        } else {
            log.info("No items found");
        }

        return createBaseResponse();
    }

    /**
     * Queries SampleEntity entries based on status and date range with pagination using criteria API.
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
    public BaseResponse postQueryCriteria(@ParamName("status") SampleStatusEnumType status, @ParamName("from") String fromString,
            @ParamName("to") String toString, @ParamName("page") int page, @ParamName("size") int size) throws BaseException {

        OffsetDateTime from = DateUtil.tryToParseAbsoluteRelativeDate(fromString);
        OffsetDateTime to = DateUtil.tryToParseAbsoluteRelativeDate(toString);

        log.info("postQueryCriteria: status={0},from={1},to={2},page={3},size={4}", status, from, to, page, size);
        List<SampleEntity> result = service.queryCriteria(EnumUtil.convert(status, SampleStatus.class), from, to, page, size);
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("Found {0} items", result.size());
            result.forEach(r -> log.info(r.toString()));
        } else {
            log.info("No items found");
        }

        return createBaseResponse();
    }
}
