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
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.panache.IPanacheModifyRest;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.panache.IPanacheTransactionRest;
import hu.icellmobilsoft.quarkus.sampler.common.core.logging.LogMethodEntryAndExit;
import hu.icellmobilsoft.quarkus.sampler.panache.action.TransactionAction;

/**
 * Implementation of {@link IPanacheModifyRest}.
 * 
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@LogMethodEntryAndExit
public class PanacheTransactionRest extends BaseRestService implements IPanacheTransactionRest {

    TransactionAction transactionAction;

    /**
     * Constructor with dependencies.
     *
     * @param transactionAction
     *            Action for managing transaction operations.
     */
    public PanacheTransactionRest(TransactionAction transactionAction) {
        this.transactionAction = transactionAction;
    }

    @Override
    public BaseResponse postFailedSave() {
        return transactionAction.postFailedSave();
    }

    @Override
    public BaseResponse postFailedTransaction() {
        return transactionAction.postFailedTransaction();
    }

    @Override
    public BaseResponse postSuccessSave() throws BaseException {
        return transactionAction.postSuccessSave();
    }

    @Override
    public BaseResponse postSuccessSaveWithContainer() throws BaseException {
        return transactionAction.postSuccessSaveWithContainer();
    }

}
