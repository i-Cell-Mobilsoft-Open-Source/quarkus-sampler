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

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.rest.rest.BaseRestService;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.panache.IMongoPanacheModifyRest;
import hu.icellmobilsoft.quarkus.sampler.common.core.logging.LogMethodEntryAndExit;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ParamName;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.quarkus.sampler.panache.action.MongoModifyAction;

/**
 * REST service implementation for mongo panache modify actions.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@LogMethodEntryAndExit
public class MongoPanacheModifyRest extends BaseRestService implements IMongoPanacheModifyRest {

    MongoModifyAction mongoModifyAction;

    /**
     * Constructor with dependencies.
     *
     * @param mongoModifyAction
     *            Action for managing mongo modify operations.
     */
    public MongoPanacheModifyRest(MongoModifyAction mongoModifyAction) {
        this.mongoModifyAction = mongoModifyAction;
    }

    @Override
    public BaseResponse putModify() throws BaseException {
        return mongoModifyAction.putModify();
    }

    @Override
    public BaseResponse putBulkUpdateStatus(@ParamName("ids") List<String> ids, @ParamName("status") SampleStatusEnumType status)
            throws BaseException {
        return mongoModifyAction.putBulkUpdateStatus(ids, status);
    }

    @Override
    public BaseResponse deleteFailed() {
        return mongoModifyAction.deleteFailed();
    }

    @Override
    public BaseResponse deleteById(@ParamName("id") String id) throws BaseException {
        return mongoModifyAction.deleteById(id);
    }

    @Override
    public BaseResponse deleteFirstFind() {
        return mongoModifyAction.deleteFirstFind();
    }

}
