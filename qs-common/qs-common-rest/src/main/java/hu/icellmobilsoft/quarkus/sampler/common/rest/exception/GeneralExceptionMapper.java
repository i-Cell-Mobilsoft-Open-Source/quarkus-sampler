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
package hu.icellmobilsoft.quarkus.sampler.common.rest.exception;

import java.util.Objects;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.exception.BaseExceptionWrapper;
import hu.icellmobilsoft.coffee.rest.exception.DefaultGeneralExceptionMapper;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.dto.exception.BaseRuntimeException;

/**
 * Exception mapper for non-handled exception throwing
 *
 * @author speter555
 *
 */
@Provider
@Dependent
public class GeneralExceptionMapper extends DefaultGeneralExceptionMapper {

    @Inject
    @ThisLogger
    AppLogger log;

    /**
     * Default constructor
     */
    public GeneralExceptionMapper() {
        // Default constructor for java 21
    }

    @Override
    public Response toResponse(Exception e) {
        Response result = null;
        if (e instanceof BaseExceptionWrapper<?>) {
            Exception unwrappedException = super.unwrapException((Exception & BaseExceptionWrapper<?>) e);
            if (unwrappedException instanceof BaseException be) {
                result = this.handleWrappedException(be);
            } else {
                this.log.trace("Unwrapped exception is not a BaseException, proceeding with default exception handling.");
            }
        } else if (e instanceof BaseRuntimeException bre) {
            Exception unwrappedException = this.unwrapRuntimeException(bre);
            if (unwrappedException instanceof BaseException be) {
                result = this.handleWrappedException(be);
            } else {
                this.log.trace("Unwrapped exception is not a BaseException, proceeding with default exception handling.");
            }
        }

        return Objects.requireNonNullElseGet(result, () -> this.handleException(e));
    }

    protected <W extends BaseRuntimeException> Exception unwrapRuntimeException(W wrappedException) {
        if (wrappedException.getCause() instanceof BaseException be) {
            this.log.trace("Wrapped BaseException cause.");
            return be;
        }
        this.log.error("Unknown error in cause: ", wrappedException);
        this.log.writeLogToError();
        return wrappedException;
    }
}
