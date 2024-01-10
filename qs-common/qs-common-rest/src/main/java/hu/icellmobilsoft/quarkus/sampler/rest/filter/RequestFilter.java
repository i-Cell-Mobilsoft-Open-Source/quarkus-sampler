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
package hu.icellmobilsoft.quarkus.sampler.rest.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;

import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.rest.validation.xml.exception.BaseProcessingExceptionWrapper;

/**
 * General util filter, this filter's task is to process request headers
 * <p>
 * It has to be run before exception handlers that the error translations is well (see: {@link PreMatching})
 *
 * @author speter555
 */
@Provider
@PreMatching
@Priority(500)
public class RequestFilter implements ContainerRequestFilter {

    /**
     * Default constructor
     */
    public RequestFilter() {
        // Default constructor for java 21
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            IOUtils.copy(requestContext.getEntityStream(), outputStream);
            outputStream.flush();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            requestContext.setEntityStream(inputStream);
        } catch (Exception e) {
            throw new BaseProcessingExceptionWrapper(new BaseException("Error during xml message read.", e));
        }

    }
}
