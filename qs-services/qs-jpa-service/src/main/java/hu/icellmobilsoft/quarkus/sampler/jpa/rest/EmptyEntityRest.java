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
package hu.icellmobilsoft.quarkus.sampler.jpa.rest;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.rest.rest.BaseRestService;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IEmptyEntityRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.emptyentity.EmptyEntityResponse;
import hu.icellmobilsoft.quarkus.sampler.jpa.action.EmptyEntityAction;

/**
 * Implementation of {@link IEmptyEntityRest}.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Model
public class EmptyEntityRest extends BaseRestService implements IEmptyEntityRest {

    @Inject
    EmptyEntityAction emptyEntityAction;

    @Override
    public EmptyEntityResponse postEmptyEntity(BaseRequest baseRequest) throws BaseException {
        return wrapPathParam1(emptyEntityAction::createEmptyEntity, baseRequest, "postEmptyEntity", "baseRequest");
    }
}
