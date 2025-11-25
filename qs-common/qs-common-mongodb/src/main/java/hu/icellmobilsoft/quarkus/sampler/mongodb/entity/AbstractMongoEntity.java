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

import org.bson.types.ObjectId;

/**
 * Abstract base class for MongoDB entities. Provides common fields and functionality for all MongoDB document entities.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
public abstract class AbstractMongoEntity {

    /**
     * Unique identifier
     */
    public ObjectId id;

    /**
     * Gets the unique identifier.
     *
     * @return the ObjectId identifier
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Sets the unique identifier.
     *
     * @param id
     *            the ObjectId identifier to set
     */
    public void setId(ObjectId id) {
        this.id = id;
    }
}
