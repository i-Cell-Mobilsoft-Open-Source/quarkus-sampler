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
package hu.icellmobilsoft.quarkus.sampler.rest.logger;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.ConstrainedTo;
import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.ext.Provider;

import hu.icellmobilsoft.coffee.dto.common.LogConstants;
import hu.icellmobilsoft.coffee.rest.log.BaseRestLogger;
import hu.icellmobilsoft.quarkus.sampler.rest.filter.CustomPriorities;

/**
 * REST request-response logger
 *
 * @author speter555
 * @since 0.1.0
 * 
 */
@Provider
@Priority(CustomPriorities.PRE_AUTHENTICATION)
@Dependent
@ConstrainedTo(RuntimeType.SERVER)
public class RestLogger extends BaseRestLogger {

    /**
     * Default constructor
     */
    public RestLogger() {
        // Default constructor for java 21
    }

    @Override
    public String sessionKey() {
        return LogConstants.LOG_SESSION_ID;
    }
}
