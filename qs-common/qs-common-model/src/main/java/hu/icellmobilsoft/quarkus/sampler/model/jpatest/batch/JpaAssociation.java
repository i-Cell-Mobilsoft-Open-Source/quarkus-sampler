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

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;

/**
 * Entity for java associations.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Entity
@Table(name = "JPA_ASSOCIATION")
public class JpaAssociation extends AbstractIdentifiedAuditEntity {

    /**
     * ManyToOne
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANY_TO_ONE")
    private EmptyEntity manyToOne;

    public EmptyEntity getManyToOne() {
        return manyToOne;
    }

    public void setManyToOne(EmptyEntity manyToOne) {
        this.manyToOne = manyToOne;
    }
}
