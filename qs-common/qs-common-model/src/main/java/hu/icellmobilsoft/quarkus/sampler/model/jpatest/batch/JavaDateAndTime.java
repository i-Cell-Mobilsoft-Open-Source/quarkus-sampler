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
package hu.icellmobilsoft.quarkus.sampler.model.jpatest.batch;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;

/**
 * Entity for java date and time.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Entity
@Table(name = "JAVA_DATE_AND_TIME")
public class JavaDateAndTime extends AbstractIdentifiedAuditEntity {

    // --------------------
    // java.time.*
    // --------------------

    /**
     * LocalDate
     */
    @Column(name = "LOCAL_DATE")
    private LocalDate localDate;

    /**
     * LocalTime
     */
    @Column(name = "LOCAL_TIME")
    private LocalTime localTime;

    /**
     * LocalDateTime
     */
    @Column(name = "LOCAL_DATE_TIME")
    private LocalDateTime localDateTime;

    //FIXME HIBERNATE 6.4+ timezone fix 
    /**
     * OffsetTime
     */
//    @Column(name = "OFFSET_TIME")
//    private OffsetTime offsetTime;

    /**
     * OffsetDateTime
     */
    @Column(name = "OFFSET_DATE_TIME")
    private OffsetDateTime offsetDateTime;

    /**
     * ZonedDateTime
     */
    @Column(name = "ZONED_DATE_TIME")
    private ZonedDateTime zonedDateTime;

    /**
     * Instant
     */
    @Column(name = "INSTANT")
    private Instant instant;

    // --------------------
    // java.sql.*
    // --------------------

    /**
     * SQL Date
     */
    @Column(name = "SQL_DATE")
    private java.sql.Date sqlDate;

    /**
     * SQL Time
     */
    @Column(name = "SQL_TIME")
    private Time sqlTime;

    /**
     * SQL Timestamp
     */
    @Column(name = "SQL_TIMESTAMP")
    private Timestamp sqlTimestamp;

    // --------------------
    // java.util.*
    // --------------------

    /**
     * UTIL Date default
     */
    @Column(name = "DATE_DEFAULT")
    private Date dateDefault;

    /**
     * UTIL Date as {@link TemporalType#DATE}
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_TEMPORAL_DATE")
    private Date dateTemporalDate;

    /**
     * UTIL Date as {@link TemporalType#TIME}
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "DATE_TEMPORAL_TIME")
    private Date dateTemporalTime;

    /**
     * UTIL Date as {@link TemporalType#TIMESTAMP}
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_TEMPORAL_TIMESTAMP")
    private Date dateTemporalTimestamp;

    /**
     * UTIL Calendar default
     */
    @Column(name = "CALENDAR_DEFAULT")
    private Calendar calendarDefault;

    /**
     * UTIL Calendar as {@link TemporalType#DATE}
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "CALENDAR_TEMPORAL_DATE")
    private Calendar calendarTemporalDate;

    /**
     * UTIL Date as {@link TemporalType#TIME}
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "CALENDAR_TEMPORAL_TIME")
    private Calendar calendarTemporalTime;

    /**
     * UTIL Date as {@link TemporalType#TIMESTAMP}
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CALENDAR_TEMPORAL_TIMESTAMP")
    private Calendar calendarTemporalTimestamp;

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

//    public OffsetTime getOffsetTime() {
//        return offsetTime;
//    }
//
//    public void setOffsetTime(OffsetTime offsetTime) {
//        this.offsetTime = offsetTime;
//    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public java.sql.Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(java.sql.Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    public Time getSqlTime() {
        return sqlTime;
    }

    public void setSqlTime(Time sqlTime) {
        this.sqlTime = sqlTime;
    }

    public Timestamp getSqlTimestamp() {
        return sqlTimestamp;
    }

    public void setSqlTimestamp(Timestamp sqlTimestamp) {
        this.sqlTimestamp = sqlTimestamp;
    }

    public Date getDateDefault() {
        return dateDefault;
    }

    public void setDateDefault(Date dateDefault) {
        this.dateDefault = dateDefault;
    }

    public Date getDateTemporalDate() {
        return dateTemporalDate;
    }

    public void setDateTemporalDate(Date dateTemporalDate) {
        this.dateTemporalDate = dateTemporalDate;
    }

    public Date getDateTemporalTime() {
        return dateTemporalTime;
    }

    public void setDateTemporalTime(Date dateTemporalTime) {
        this.dateTemporalTime = dateTemporalTime;
    }

    public Date getDateTemporalTimestamp() {
        return dateTemporalTimestamp;
    }

    public void setDateTemporalTimestamp(Date dateTemporalTimestamp) {
        this.dateTemporalTimestamp = dateTemporalTimestamp;
    }

    public Calendar getCalendarDefault() {
        return calendarDefault;
    }

    public void setCalendarDefault(Calendar calendarDefault) {
        this.calendarDefault = calendarDefault;
    }

    public Calendar getCalendarTemporalDate() {
        return calendarTemporalDate;
    }

    public void setCalendarTemporalDate(Calendar calendarTemporalDate) {
        this.calendarTemporalDate = calendarTemporalDate;
    }

    public Calendar getCalendarTemporalTime() {
        return calendarTemporalTime;
    }

    public void setCalendarTemporalTime(Calendar calendarTemporalTime) {
        this.calendarTemporalTime = calendarTemporalTime;
    }

    public Calendar getCalendarTemporalTimestamp() {
        return calendarTemporalTimestamp;
    }

    public void setCalendarTemporalTimestamp(Calendar calendarTemporalTimestamp) {
        this.calendarTemporalTimestamp = calendarTemporalTimestamp;
    }
}
