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
package hu.icellmobilsoft.quarkus.sampler.jpa.service;

import java.util.List;

import jakarta.enterprise.inject.Model;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.common.jpa.service.BaseService;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.batch.JpaAssociation;

/**
 * Service for {@link JpaAssociation}.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Model
public class JpaAssociationService extends BaseService<JpaAssociation> {

    /**
     * Find {@link JpaAssociation} by ID.
     *
     * @param id
     *            ID of {@link JpaAssociation}.
     * @return {@link JpaAssociation}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public JpaAssociation findById(String id) throws BaseException {
        return super.findById(id, JpaAssociation.class);
    }

    /**
     * Find all {@link JpaAssociation}.
     * 
     * @return {@link JpaAssociation} list.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public List<JpaAssociation> findAll() throws BaseException {
        return super.findAll(JpaAssociation.class);
    }
}
