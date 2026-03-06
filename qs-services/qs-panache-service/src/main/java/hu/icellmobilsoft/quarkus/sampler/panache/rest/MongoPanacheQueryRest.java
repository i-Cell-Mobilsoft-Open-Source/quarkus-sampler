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
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.panache.IMongoPanacheQueryRest;
import hu.icellmobilsoft.quarkus.sampler.common.core.logging.LogMethodEntryAndExit;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ParamName;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.quarkus.sampler.panache.action.MongoQueryAction;

/**
 * Implementation of {@link IMongoPanacheQueryRest}.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@LogMethodEntryAndExit
public class MongoPanacheQueryRest extends BaseRestService implements IMongoPanacheQueryRest {

    MongoQueryAction mongoQueryAction;

    /**
     * Constructor with dependencies.
     *
     * @param mongoQueryAction
     *            Action for managing mongo query operations.
     */
    public MongoPanacheQueryRest(MongoQueryAction mongoQueryAction) {
        this.mongoQueryAction = mongoQueryAction;
    }

    @Override
    public BaseResponse getFindFailure() {
        return mongoQueryAction.getFindFailure();
    }

    @Override
    public BaseResponse getAllByStatus(@ParamName("status") SampleStatusEnumType status) {
        return mongoQueryAction.getAllByStatus(status);
    }

    @Override
    public BaseResponse getAllBetween(@ParamName("from") String fromString, @ParamName("to") String toString) throws BaseException {
        return mongoQueryAction.getAllBetween(fromString, toString);
    }

    @Override
    public BaseResponse getAllIdsBetween(@ParamName("from") String fromString, @ParamName("to") String toString) throws BaseException {
        return mongoQueryAction.getAllIdsBetween(fromString, toString);
    }

    @Override
    public BaseResponse postQuery(@ParamName("status") SampleStatusEnumType status, @ParamName("from") String fromString,
            @ParamName("to") String toString, @ParamName("page") int page, @ParamName("size") int size) throws BaseException {
        return mongoQueryAction.postQuery(status, fromString, toString, page, size);
    }
}
