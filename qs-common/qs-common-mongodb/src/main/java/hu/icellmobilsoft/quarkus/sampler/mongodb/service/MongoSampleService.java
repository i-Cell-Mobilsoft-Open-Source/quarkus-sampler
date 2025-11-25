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
package hu.icellmobilsoft.quarkus.sampler.mongodb.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.enterprise.context.ApplicationScoped;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.model.Filters;

import hu.icellmobilsoft.quarkus.sampler.common.core.logging.LogMethodEntryAndExit;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ParamName;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.MongoSampleEntity;
import hu.icellmobilsoft.quarkus.sampler.mongodb.entity.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.mongodb.repository.MongoSampleRepository;

/**
 * MongoDB {@link MongoSampleEntity} panache service class.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@LogMethodEntryAndExit
public class MongoSampleService extends BasePanacheMongoService<MongoSampleEntity, MongoSampleRepository> {

    /**
     * Finds entities by userName, management code, status and timestamp between fromDate and toDate.
     *
     * @param userName
     *            userName
     * @param managementCode
     *            management code
     * @param status
     *            status (optional)
     * @param fromDate
     *            from date (optional)
     * @param toDate
     *            to date (optional)
     * @return list of found entities
     */
    public List<MongoSampleEntity> findByUsernameAndManagementCodeAndStatusAndTimestampBetween(@ParamName("userName") String userName,
            @ParamName("managementCode") int managementCode, @ParamName("status") SampleStatus status, @ParamName("fromDate") OffsetDateTime fromDate,
            @ParamName("toDate") OffsetDateTime toDate) {
        List<Bson> filters = new ArrayList<>();

        filters.add(Filters.eq("userName", userName));
        filters.add(Filters.eq("managementCode", managementCode));

        if (Objects.nonNull(status)) {
            filters.add(Filters.eq("status", status.name()));
        }

        if (Objects.nonNull(fromDate)) {
            filters.add(Filters.gte("timestamp", fromDate.toInstant()));
        }

        if (Objects.nonNull(toDate)) {
            filters.add(Filters.lte("timestamp", toDate.toInstant()));
        }

        return repository.list(Filters.and(filters));
    }

}
