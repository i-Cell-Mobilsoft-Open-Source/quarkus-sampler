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
package hu.icellmobilsoft.quarkus.sampler.panache.rest;

import jakarta.enterprise.context.ApplicationScoped;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.rest.rest.BaseRestService;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.panache.IMongoPanacheProjectionRest;
import hu.icellmobilsoft.quarkus.sampler.common.core.logging.LogMethodEntryAndExit;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ParamName;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.quarkus.sampler.panache.action.MongoProjectionAction;

/**
 * Implementation of {@link IMongoPanacheProjectionRest}.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@LogMethodEntryAndExit
public class MongoPanacheProjectionRest extends BaseRestService implements IMongoPanacheProjectionRest {

    MongoProjectionAction mongoProjectionAction;

    /**
     * Constructor with dependencies.
     *
     * @param mongoProjectionAction
     *            Action for managing mongo projection operations.
     */
    public MongoPanacheProjectionRest(MongoProjectionAction mongoProjectionAction) {
        this.mongoProjectionAction = mongoProjectionAction;
    }

    @Override
    public BaseResponse getAllBetweenWithProjection(@ParamName("from") String fromString, @ParamName("to") String toString) throws BaseException {
        return mongoProjectionAction.getAllBetweenWithProjection(fromString, toString);
    }

    @Override
    public BaseResponse getAllByStatusProjection(@ParamName("status") SampleStatusEnumType status) {
        return mongoProjectionAction.getAllByStatusProjection(status);
    }

}
