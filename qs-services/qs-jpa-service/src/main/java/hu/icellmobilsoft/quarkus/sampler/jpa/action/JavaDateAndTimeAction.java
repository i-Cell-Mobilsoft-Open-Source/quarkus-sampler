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

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequestType;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.jpa.helper.TransactionHelper;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.quarkus.sampler.common.jpa.service.BatchService;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadateandtime.JavaDateAndTimeInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadateandtime.JavaDateAndTimeResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadateandtime.JavaDateAndTimeType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadateandtime.JavaDateAndTimeUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.jpa.service.JavaDateAndTimeService;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.batch.JavaDateAndTime;

/**
 * Action class for {@link JavaDateAndTime}.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Model
public class JavaDateAndTimeAction extends BaseAction {

    @Inject
    BatchService batchService;

    @Inject
    TransactionHelper transactionHelper;

    @Inject
    JavaDateAndTimeService javaDateAndTimeService;

    /**
     * Creates and inserts a {@link JavaDateAndTime} entity with BatchService.
     *
     * @param javaDateAndTimeInsertRequest
     *            {@link JavaDateAndTimeInsertRequest}.
     * @return {@link JavaDateAndTimeResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public JavaDateAndTimeResponse insertJavaDateAndTime(JavaDateAndTimeInsertRequest javaDateAndTimeInsertRequest) throws BaseException {
        if (javaDateAndTimeInsertRequest == null) {
            throw new InvalidParameterException("javaDateAndTimeInsertRequest is NULL!");
        }

        OffsetDateTime baseDateTime = javaDateAndTimeInsertRequest.getJavaDateAndTime().getBaseDateTime();
        JavaDateAndTime javaDateAndTime = new JavaDateAndTime();
        fillJavaDateAndTime(javaDateAndTime, baseDateTime);

        List<JavaDateAndTime> javaDateAndTimeList = List.of(javaDateAndTime);

        transactionHelper.executeWithTransaction(batchService::batchInsertNative, javaDateAndTimeList, JavaDateAndTime.class);

        return createResponse(javaDateAndTimeInsertRequest, javaDateAndTime);
    }

    /**
     * Updates a {@link JavaDateAndTime} entity with BatchService.
     *
     * @param javaDateAndTimeId
     *            ID of {@link JavaDateAndTime}.
     * @param javaDateAndTimeUpdateRequest
     *            {@link JavaDateAndTimeUpdateRequest}.
     * @return {@link JavaDateAndTimeResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public JavaDateAndTimeResponse updateJavaDateAndTime(String javaDateAndTimeId, JavaDateAndTimeUpdateRequest javaDateAndTimeUpdateRequest)
            throws BaseException {
        if (StringUtils.isBlank(javaDateAndTimeId)) {
            throw new InvalidParameterException("javaDateAndTimeId is BLANK!");
        }
        if (javaDateAndTimeUpdateRequest == null) {
            throw new InvalidParameterException("javaDateAndTimeUpdateRequest is NULL!");
        }

        OffsetDateTime baseDateTime = javaDateAndTimeUpdateRequest.getJavaDateAndTime().getBaseDateTime();
        JavaDateAndTime javaDateAndTime = javaDateAndTimeService.findById(javaDateAndTimeId);
        fillJavaDateAndTime(javaDateAndTime, baseDateTime);

        List<JavaDateAndTime> javaDateAndTimeList = List.of(javaDateAndTime);

        transactionHelper.executeWithTransaction(batchService::batchUpdateNative, javaDateAndTimeList, JavaDateAndTime.class);

        return createResponse(javaDateAndTimeUpdateRequest, javaDateAndTime);
    }

    /**
     * Deletes all {@link JavaDateAndTime} entities with BatchService.
     *
     * @return {@link BaseResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public BaseResponse deleteAllJavaDateAndTime() throws BaseException {
        List<JavaDateAndTime> javaDateAndTimeList = javaDateAndTimeService.findAll();
        transactionHelper.executeWithTransaction(batchService::batchDeleteNative, javaDateAndTimeList, JavaDateAndTime.class);
        return createBaseResponse();
    }

    private void fillJavaDateAndTime(JavaDateAndTime javaDateAndTime, OffsetDateTime baseDateTime) {
        if (baseDateTime == null) {
            resetJavaDateAndTime(javaDateAndTime);
            return;
        }

        LocalDateTime localDateTime = baseDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        java.util.Date date = DateUtil.toDate(localDateTime);
        Calendar calendar = DateUtil.toCalendar(date);

        // java.time.*
        javaDateAndTime.setLocalDate(localDate);
        javaDateAndTime.setLocalTime(localTime);
        javaDateAndTime.setLocalDateTime(localDateTime);
        javaDateAndTime.setOffsetTime(baseDateTime.toOffsetTime());
        javaDateAndTime.setOffsetDateTime(baseDateTime);
        javaDateAndTime.setZonedDateTime(baseDateTime.toZonedDateTime());

        // oracle doesn't support it, even though hibernate.timezone.default_storage is set to NORMALIZE, "ORA-18716: not in any time zone"
        // javaDateAndTime.setInstant(DateUtil.toDate(localDateTime).toInstant());

        // java.sql.*
        javaDateAndTime.setSqlDate(java.sql.Date.valueOf(localDate));
        javaDateAndTime.setSqlTime(Time.valueOf(localTime));
        javaDateAndTime.setSqlTimestamp(Timestamp.valueOf(localDateTime));

        // java.util.*
        javaDateAndTime.setDateDefault(date);
        javaDateAndTime.setDateTemporalDate(date);
        javaDateAndTime.setDateTemporalTime(date);
        javaDateAndTime.setDateTemporalTimestamp(date);
        javaDateAndTime.setCalendarDefault(calendar);
        javaDateAndTime.setCalendarTemporalDate(calendar);
        javaDateAndTime.setCalendarTemporalTime(calendar);
        javaDateAndTime.setCalendarTemporalTimestamp(calendar);
    }

    private void resetJavaDateAndTime(JavaDateAndTime javaDateAndTime) {
        // java.time.*
        javaDateAndTime.setLocalDate(null);
        javaDateAndTime.setLocalTime(null);
        javaDateAndTime.setLocalDateTime(null);
        javaDateAndTime.setOffsetTime(null);
        javaDateAndTime.setOffsetDateTime(null);
        javaDateAndTime.setZonedDateTime(null);
        javaDateAndTime.setInstant(null);

        // java.sql.*
        javaDateAndTime.setSqlDate(null);
        javaDateAndTime.setSqlTime(null);
        javaDateAndTime.setSqlTimestamp(null);

        // java.util.*
        javaDateAndTime.setDateDefault(null);
        javaDateAndTime.setDateTemporalDate(null);
        javaDateAndTime.setDateTemporalTime(null);
        javaDateAndTime.setDateTemporalTimestamp(null);
        javaDateAndTime.setCalendarDefault(null);
        javaDateAndTime.setCalendarTemporalDate(null);
        javaDateAndTime.setCalendarTemporalTime(null);
        javaDateAndTime.setCalendarTemporalTimestamp(null);
    }

    private JavaDateAndTimeResponse createResponse(BaseRequestType request, JavaDateAndTime javaDateAndTime) {
        JavaDateAndTimeType javaDateAndTimeType = new JavaDateAndTimeType().withJavaDateAndTimeId(javaDateAndTime.getId());
        JavaDateAndTimeResponse response = new JavaDateAndTimeResponse().withJavaDateAndTime(javaDateAndTimeType);
        handleSuccessResultType(response, request);
        return response;
    }
}
