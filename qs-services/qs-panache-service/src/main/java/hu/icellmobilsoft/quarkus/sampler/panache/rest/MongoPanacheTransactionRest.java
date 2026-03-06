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
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.panache.IMongoPanacheTransactionRest;
import hu.icellmobilsoft.quarkus.sampler.common.core.logging.LogMethodEntryAndExit;
import hu.icellmobilsoft.quarkus.sampler.panache.action.MongoTransactionAction;

/**
 * Implementation of {@link IMongoPanacheTransactionRest}.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@LogMethodEntryAndExit
public class MongoPanacheTransactionRest extends BaseRestService implements IMongoPanacheTransactionRest {

    MongoTransactionAction mongoTransactionAction;

    /**
     * Constructor with dependencies.
     *
     * @param mongoTransactionAction
     *            Action for managing mongo transaction operations.
     */
    public MongoPanacheTransactionRest(MongoTransactionAction mongoTransactionAction) {
        this.mongoTransactionAction = mongoTransactionAction;
    }

    @Override
    public BaseResponse postFailedSave() {
        return mongoTransactionAction.postFailedSave();
    }

    @Override
    public BaseResponse postFailedTransaction() {
        return mongoTransactionAction.postFailedTransaction();
    }

    @Override
    public BaseResponse postSuccessSave() throws BaseException {
        return mongoTransactionAction.postSuccessSave();
    }

}
