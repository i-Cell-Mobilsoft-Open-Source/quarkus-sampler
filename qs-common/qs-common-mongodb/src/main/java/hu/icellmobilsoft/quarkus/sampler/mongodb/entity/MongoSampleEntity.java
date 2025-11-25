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
package hu.icellmobilsoft.quarkus.sampler.mongodb.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.enums.SampleValue;
import io.quarkus.mongodb.panache.common.MongoEntity;

/**
 * MongoDB sample entity.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@MongoEntity(collection = "sample")
public class MongoSampleEntity extends AbstractMongoEntity {

    /**
     * User name for filtering
     */
    private String userName;

    /**
     * Management code for filtering
     */
    private int managementCode;

    /**
     * Sample enum status
     */
    private SampleStatus status;

    /**
     * Sample input enum value
     */
    private SampleValue value;

    /**
     * Value of sample input data value
     */
    private String inputValue;

    /**
     * Timestamp for filtering (uses OffsetDateTime)
     */
    private OffsetDateTime timestamp;

    /**
     * Value of sample local dateTime
     */
    private LocalDateTime localDateTime;

    /**
     * Value of sample local date
     */
    private LocalDate localDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getManagementCode() {
        return managementCode;
    }

    public void setManagementCode(int managementCode) {
        this.managementCode = managementCode;
    }

    public SampleStatus getStatus() {
        return status;
    }

    public void setStatus(SampleStatus status) {
        this.status = status;
    }

    public SampleValue getValue() {
        return value;
    }

    public void setValue(SampleValue value) {
        this.value = value;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
