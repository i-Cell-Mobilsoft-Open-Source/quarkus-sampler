/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 - 2023 i-Cell Mobilsoft Zrt.
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

import java.time.Duration;
import java.time.MonthDay;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;

import hu.icellmobilsoft.quarkus.sampler.model.jpatest.converter.DurationAttributeConverter;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.converter.MonthDayAttributeConverter;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.converter.PeriodAttributeConverter;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.converter.YearAttributeConverter;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.converter.YearMonthAttributeConverter;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.converter.ZoneIdAttributeConverter;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.converter.ZoneOffsetAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;

/**
 * Entity for jpa converters.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Entity
@Table(name = "JPA_CONVERTER_ENTITY")
public class JpaConverterEntity extends AbstractIdentifiedAuditEntity {

    /**
     * ZoneID with {@link ZoneIdAttributeConverter}.
     */
    @Convert(converter = ZoneIdAttributeConverter.class)
    @Column(name = "ZONE_ID")
    private ZoneId zoneId;

    /**
     * ZoneOffset with {@link ZoneOffsetAttributeConverter}.
     */
    @Convert(converter = ZoneOffsetAttributeConverter.class)
    @Column(name = "ZONE_OFFSET")
    private ZoneOffset zoneOffset;

    /**
     * Year with {@link YearAttributeConverter}.
     */
    @Convert(converter = YearAttributeConverter.class)
    @Column(name = "YEAR")
    private Year year;

    /**
     * YearMonth with {@link YearMonthAttributeConverter}.
     */
    @Convert(converter = YearMonthAttributeConverter.class)
    @Column(name = "YEAR_MONTH")
    private YearMonth yearMonth;

    /**
     * MonthDay with {@link MonthDayAttributeConverter}.
     */
    @Convert(converter = MonthDayAttributeConverter.class)
    @Column(name = "MONTH_DAY")
    private MonthDay monthDay;

    /**
     * Duration with {@link DurationAttributeConverter}.
     */
    @Convert(converter = DurationAttributeConverter.class)
    @Column(name = "DURATION")
    private Duration duration;

    /**
     * Period with {@link PeriodAttributeConverter}.
     */
    @Convert(converter = PeriodAttributeConverter.class)
    @Column(name = "PERIOD")
    private Period period;

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneOffset getZoneOffset() {
        return zoneOffset;
    }

    public void setZoneOffset(ZoneOffset zoneOffset) {
        this.zoneOffset = zoneOffset;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public MonthDay getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
