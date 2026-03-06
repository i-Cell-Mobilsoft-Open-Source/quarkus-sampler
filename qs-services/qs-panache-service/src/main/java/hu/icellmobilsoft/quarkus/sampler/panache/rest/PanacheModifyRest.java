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
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.panache.IPanacheModifyRest;
import hu.icellmobilsoft.quarkus.sampler.common.core.logging.LogMethodEntryAndExit;
import hu.icellmobilsoft.quarkus.sampler.common.core.parameter.ParamName;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.quarkus.sampler.panache.action.ModifyAction;

/**
 * REST service implementation for panache modify actions.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
@LogMethodEntryAndExit
public class PanacheModifyRest extends BaseRestService implements IPanacheModifyRest {

    ModifyAction modifyAction;

    /**
     * Constructor with dependencies.
     *
     * @param modifyAction
     *            Action for managing modify operations.
     */
    public PanacheModifyRest(ModifyAction modifyAction) {
        this.modifyAction = modifyAction;
    }

    @Override
    public BaseResponse putModify() throws BaseException {
        return modifyAction.putModify();
    }

    @Override
    public BaseResponse putBulkUpdateStatus(@ParamName("ids") List<String> ids, @ParamName("status") SampleStatusEnumType status)
            throws BaseException {
        return modifyAction.putBulkUpdateStatus(ids, status);
    }

    @Override
    public BaseResponse deleteFailed() {
        return modifyAction.deleteFailed();
    }

    @Override
    public BaseResponse deleteById(@ParamName("id") String id) throws BaseException {
        return modifyAction.deleteById(id);
    }

    @Override
    public BaseResponse deleteFirstFind() {
        return modifyAction.deleteFirstFind();
    }

}
