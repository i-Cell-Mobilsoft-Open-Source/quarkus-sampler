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
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.panache.dto.SampleEntityProjection;
import hu.icellmobilsoft.quarkus.sampler.panache.service.SampleEntityService;

/**
 * Action class for managing projection operations on {@link hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity}. This class demonstrates
 * various database queries that return projections of the entity using Panache.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@ValidateIncomingParameters
public class ProjectionAction extends BaseAction {

    AppLogger log;
    SampleEntityService service;

    /**
     * Constructor with dependencies.
     *
     * @param log
     *            Logger instance for logging messages.
     * @param service
     *            Service for managing SampleEntity operations.
     */
    public ProjectionAction(@ThisLogger AppLogger log, SampleEntityService service) {
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
        List<SampleEntityProjection> result = service.getAllBetweenWithProjection(from, to);
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("Found {0} items", result.size());
            result.forEach(r -> log.info(r.toString()));
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
        List<SampleEntityProjection> result = service.findAllByStatusProjection(EnumUtil.convert(status, SampleStatus.class));
        if (CollectionUtils.isNotEmpty(result)) {
            log.info("Found {0} items", result.size());
            result.forEach(r -> log.info(r.toString()));
        } else {
            log.info("No items found");
        }
        return createBaseResponse();
    }
}
