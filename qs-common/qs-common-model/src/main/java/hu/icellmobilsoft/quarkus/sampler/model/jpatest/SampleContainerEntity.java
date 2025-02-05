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

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;

/**
 * 
 * Sample container table entity
 * 
 * @author balazs.joo
 * @since 0.1.0
 */
@Entity
@Table(name = "SAMPLE_CONTAINER")
public class SampleContainerEntity extends AbstractIdentifiedAuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * {@link SampleEntity}
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SAMPLE", nullable = false)
    @NotNull
    private SampleEntity sampleEntity;

    public SampleEntity getSampleEntity() {
        return sampleEntity;
    }

    public void setSampleEntity(SampleEntity sampleEntity) {
        this.sampleEntity = sampleEntity;
    }
}
