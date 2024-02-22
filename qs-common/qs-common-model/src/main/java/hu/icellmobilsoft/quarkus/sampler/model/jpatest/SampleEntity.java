/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.quarkus.sampler.model.jpatest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;
import jakarta.validation.constraints.Size;

/**
 * 
 * Sample table entity
 * 
 * @author imre.scheffer
 * @since 0.1.0
 */
@Entity
@Table(name = "SAMPLE")
public class SampleEntity extends AbstractIdentifiedAuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Sample enum stutus
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 10)
    private SampleStatus status;

    /**
     * Sample input enum value
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "ENUM_VALUE", length = 10)
    private SampleValue value;

    /**
     * Value of sample input data value
     */
    @Column(name = "INPUT_VALUE", length = 30)
    @Size(max = 30)
    private String inputValue;

    /**
     * Value of sample local date
     */
    @Column(name = "LOCAL_DATE")
    private LocalDateTime localDateTime;

    @Column(name = "MOD_LOCAL_DATE", insertable = false)
    private LocalDate modLocalDate;

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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDate getModLocalDate() {
        return modLocalDate;
    }

    public void setModLocalDate(LocalDate modLocalDate) {
        this.modLocalDate = modLocalDate;
    }
}
