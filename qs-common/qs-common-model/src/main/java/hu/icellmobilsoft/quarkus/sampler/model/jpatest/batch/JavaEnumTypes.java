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

import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.BatchOperationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;

/**
 * Entity for java enum types.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Entity
@Table(name = "JAVA_ENUM_TYPES")
public class JavaEnumTypes extends AbstractIdentifiedAuditEntity {

    /**
     * Default enum
     */
    @Column(name = "DEFAULT_ENUM")
    private BatchOperationType defaultEnum;

    /**
     * Ordinal enum
     */
    @Column(name = "ORDINAL_ENUM")
    @Enumerated(value = EnumType.ORDINAL)
    private BatchOperationType ordinalEnum;

    /**
     * String enum
     */
    @Column(name = "STRING_ENUM")
    @Enumerated(value = EnumType.STRING)
    private BatchOperationType stringEnum;

    public BatchOperationType getDefaultEnum() {
        return defaultEnum;
    }

    public void setDefaultEnum(BatchOperationType defaultEnum) {
        this.defaultEnum = defaultEnum;
    }

    public BatchOperationType getOrdinalEnum() {
        return ordinalEnum;
    }

    public void setOrdinalEnum(BatchOperationType ordinalEnum) {
        this.ordinalEnum = ordinalEnum;
    }

    public BatchOperationType getStringEnum() {
        return stringEnum;
    }

    public void setStringEnum(BatchOperationType stringEnum) {
        this.stringEnum = stringEnum;
    }
}
