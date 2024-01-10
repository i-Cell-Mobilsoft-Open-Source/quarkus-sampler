/*-
 * #%L
 * Quarkus-sampler
 * %%
 * Copyright (C) 2024 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.quarkus.sampler.rest.action;

import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.ContextType;
import hu.icellmobilsoft.coffee.rest.action.AbstractBaseAction;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.coffee.tool.utils.string.RandomUtil;

/**
 * Base class for all other business logic action class
 *
 * @author speter555
 *
 */
public class BaseAction extends AbstractBaseAction {

    /**
     * Default constructor
     */
    public BaseAction() {
        // Default constructor for java 21
    }

    /**
     * Create a new Context for responses
     *
     * @return ContextType with generated request id and utc timestamp
     */
    public ContextType createContext() {
        ContextType context = new ContextType();
        context.setRequestId(RandomUtil.generateId());
        context.setTimestamp(DateUtil.nowUTCTruncatedToMillis());
        return context;
    }

}
