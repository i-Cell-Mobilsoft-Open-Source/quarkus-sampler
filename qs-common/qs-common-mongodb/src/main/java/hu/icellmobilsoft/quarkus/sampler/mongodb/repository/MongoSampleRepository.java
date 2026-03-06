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
package hu.icellmobilsoft.quarkus.sampler.mongodb.repository;

import jakarta.enterprise.context.ApplicationScoped;

import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.MongoSampleEntity;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

/**
 * MongoDB {@link MongoSampleEntity} panache repository class.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
public class MongoSampleRepository implements PanacheMongoRepository<MongoSampleEntity> {

    public MongoSampleRepository() {
        // Default constructor for java 21
    }

}
