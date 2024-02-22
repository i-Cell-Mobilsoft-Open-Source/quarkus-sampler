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
package hu.icellmobilsoft.quarkus.sampler.jpa.action;

import java.time.Duration;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequestType;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.jpa.helper.TransactionHelper;
import hu.icellmobilsoft.quarkus.sampler.common.jpa.service.BatchService;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityInsertType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityUpdateType;
import hu.icellmobilsoft.quarkus.sampler.jpa.service.JpaConverterEntityService;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.batch.JpaConverterEntity;

/**
 * Action class for {@link JpaConverterEntity}.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Model
public class JpaConverterEntityAction extends BaseAction {

    @Inject
    BatchService batchService;

    @Inject
    TransactionHelper transactionHelper;

    @Inject
    JpaConverterEntityService jpaConverterEntityService;

    /**
     * Creates and inserts a {@link JpaConverterEntity} entity with BatchService.
     *
     * @param jpaConverterEntityInsertRequest
     *            {@link JpaConverterEntityInsertRequest}.
     * @return {@link JpaConverterEntityResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public JpaConverterEntityResponse insertJpaConverterEntity(JpaConverterEntityInsertRequest jpaConverterEntityInsertRequest) throws BaseException {
        if (jpaConverterEntityInsertRequest == null) {
            throw new InvalidParameterException("jpaConverterEntityInsertRequest is NULL!");
        }

        JpaConverterEntityInsertType jpaConverterEntityInsertType = jpaConverterEntityInsertRequest.getJpaConverterEntity();
        OffsetDateTime baseDateTime = jpaConverterEntityInsertType.getBaseDateTime();
        Integer baseDays = jpaConverterEntityInsertType.getBaseDays();

        JpaConverterEntity jpaConverterEntity = new JpaConverterEntity();
        fillJpaConverterEntity(jpaConverterEntity, baseDateTime, baseDays);

        List<JpaConverterEntity> jpaConverterEntityList = List.of(jpaConverterEntity);

        transactionHelper.executeWithTransaction(batchService::batchInsertNative, jpaConverterEntityList, JpaConverterEntity.class);

        return createResponse(jpaConverterEntityInsertRequest, jpaConverterEntity.getId());
    }

    /**
     * Updates a {@link JpaConverterEntity} entity with BatchService.
     *
     * @param jpaConverterEntityId
     *            ID of {@link JpaConverterEntity}.
     * @param jpaConverterEntityUpdateRequest
     *            {@link JpaConverterEntityUpdateRequest}.
     * @return {@link JpaConverterEntityResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public JpaConverterEntityResponse updateJpaConverterEntity(String jpaConverterEntityId,
            JpaConverterEntityUpdateRequest jpaConverterEntityUpdateRequest) throws BaseException {
        if (StringUtils.isBlank(jpaConverterEntityId)) {
            throw new InvalidParameterException("jpaConverterEntityId is BLANK!");
        }
        if (jpaConverterEntityUpdateRequest == null) {
            throw new InvalidParameterException("jpaConverterEntityUpdateRequest is NULL!");
        }

        JpaConverterEntityUpdateType jpaConverterEntityUpdateType = jpaConverterEntityUpdateRequest.getJpaConverterEntity();
        OffsetDateTime baseDateTime = jpaConverterEntityUpdateType.getBaseDateTime();
        Integer baseDays = jpaConverterEntityUpdateType.getBaseDays();

        JpaConverterEntity jpaConverterEntity = jpaConverterEntityService.findById(jpaConverterEntityId);
        fillJpaConverterEntity(jpaConverterEntity, baseDateTime, baseDays);

        List<JpaConverterEntity> jpaConverterEntityList = List.of(jpaConverterEntity);

        transactionHelper.executeWithTransaction(batchService::batchUpdateNative, jpaConverterEntityList, JpaConverterEntity.class);

        return createResponse(jpaConverterEntityUpdateRequest, jpaConverterEntityId);
    }

    /**
     * Deletes all {@link JpaConverterEntity} entities with BatchService.
     *
     * @return {@link BaseResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public BaseResponse deleteAllJpaConverterEntity() throws BaseException {
        List<JpaConverterEntity> jpaConverterEntityList = jpaConverterEntityService.findAll();
        transactionHelper.executeWithTransaction(batchService::batchDeleteNative, jpaConverterEntityList, JpaConverterEntity.class);
        return createBaseResponse();
    }

    private void fillJpaConverterEntity(JpaConverterEntity jpaConverterEntity, OffsetDateTime baseDateTime, Integer baseDays) {
        if (baseDateTime != null) {
            jpaConverterEntity.setZoneId(ZoneId.from(baseDateTime));
            jpaConverterEntity.setZoneOffset(ZoneOffset.from(baseDateTime));
            jpaConverterEntity.setYearNumber(Year.from(baseDateTime));
            jpaConverterEntity.setYearMonth(YearMonth.from(baseDateTime));
            jpaConverterEntity.setMonthDay(MonthDay.from(baseDateTime));
        } else {
            jpaConverterEntity.setZoneId(null);
            jpaConverterEntity.setZoneOffset(null);
            jpaConverterEntity.setYearNumber(null);
            jpaConverterEntity.setYearMonth(null);
            jpaConverterEntity.setMonthDay(null);
        }

        if (baseDays != null) {
            jpaConverterEntity.setDuration(Duration.ofDays(baseDays));
            jpaConverterEntity.setPeriod(Period.ofDays(baseDays));
        } else {
            jpaConverterEntity.setDuration(null);
            jpaConverterEntity.setPeriod(null);
        }
    }

    private JpaConverterEntityResponse createResponse(BaseRequestType request, String jpaConverterEntityId) {
        JpaConverterEntityType jpaConverterEntity = new JpaConverterEntityType().withJpaConverterEntityId(jpaConverterEntityId);
        JpaConverterEntityResponse response = new JpaConverterEntityResponse().withJpaConverterEntity(jpaConverterEntity);
        handleSuccessResultType(response, request);
        return response;
    }
}
