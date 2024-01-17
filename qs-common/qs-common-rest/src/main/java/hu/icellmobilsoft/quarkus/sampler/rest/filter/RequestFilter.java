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

import java.io.IOException;

import jakarta.annotation.Priority;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.quarkus.sampler.rest.cdi.RequestContainer;
import hu.icellmobilsoft.quarkus.sampler.rest.header.ProjectHeader;

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

    @Context
    private HttpServletRequest request;

    /**
     * Default constructor
     */
    public RequestFilter() {
        // Default constructor for java 21
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        RequestContainer requestContainer = CDI.current().select(RequestContainer.class).get();
        ProjectHeader header = ProjectHeader.readHeaders(requestContext);

        // Set remote IP if in Forwarded header not specified.
        if (StringUtils.isBlank(header.getForwarded()) && request != null) {
            header.setForwarded(request.getRemoteAddr());
        }

        requestContainer.setProjectHeader(header);

    }
}
