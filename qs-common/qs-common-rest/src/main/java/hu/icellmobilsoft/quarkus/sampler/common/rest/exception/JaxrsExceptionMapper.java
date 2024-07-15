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

import java.io.IOException;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.NotSupportedException;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResourceInvoker;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.TechnicalFault;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.rest.exception.IExceptionMessageTranslator;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.dto.exception.enums.FaultType;

/**
 * JAX-RS API default exception mapper
 *
 * @author speter555
 * @since 0.1.0
 */
@Provider
@Dependent
public class JaxrsExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Inject
    @ThisLogger
    AppLogger log;

    @Context
    private HttpServletRequest servletRequest;

    @Inject
    IExceptionMessageTranslator exceptionMessageTranslator;

    /**
     * Default constructor
     */
    public JaxrsExceptionMapper() {
        // Default constructor for java 21
    }

    @Override
    public Response toResponse(WebApplicationException e) {
        log.error("JAX-RS error: ", e);
        log.writeLogToError();
        TechnicalFault dto = new TechnicalFault();
        // status code vagy exception-re figyeljunk? (e.getResponse().getStatus())
        if (e instanceof NotAllowedException) {
            // When returning a nonexistent endpoint + HTTP method pair, the native RESTEASY error is returned.
            // HTTP response code: 405
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_METHOD_NOT_ALLOWED);
        } else if (e instanceof NotSupportedException) {
            // When an unsupported content-type or accept header is encountered, the native RESTEASY error is returned.
            // HTTP response code: 415

            // We examine whether only the Content-Type header is invalid or the Accept header as well.
            try {
                getWildcardContentTypeResourceInvoker();

                exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_UNSUPPORTED_MEDIA_TYPE);
            } catch (NotAcceptableException e2) {
                // If both the Content-Type and Accept headers are invalid
                return Response.fromResponse(e.getResponse())//
                        .entity(getNotAcceptableMessage())//
                        .status(Response.Status.NOT_ACCEPTABLE) // override NOT_SUPPORTED status
                        .build();
            }
        } else if (e instanceof BadRequestException) {
            // When the request is poorly formatted, we return the first improperly formatted XML tag and the error thrown by JAX (what it expected).
            // HTTP response code: 400
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_BAD_REQUEST);
        } else if (e instanceof NotFoundException) {
            // Non-existent URL path
            // HTTP response code: 404
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_NOT_FOUND);
        } else if (e instanceof ForbiddenException) {
            // HTTP response code: 403
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_FORBIDDEN);
        } else if (e instanceof NotAcceptableException) {
            // HTTP response code: 406
            // In this case, an unknown 'Accept' header was received, so in the response, only a straightforward localized translation can be placed,
            // that the standard DTO is not suitable here
            return Response.fromResponse(e.getResponse()).entity(getNotAcceptableMessage()).build();
        } else if (e instanceof NotAuthorizedException) {
            // HTTP response code: 401
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_UNAUTHORIZED);
        } else if (e instanceof InternalServerErrorException) {
            // HTTP response code: 500
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_INTERNAL_SERVER_ERROR);
        } else if (e instanceof ServiceUnavailableException) {
            // HTTP response code: 503
            exceptionMessageTranslator.addCommonInfo(dto, e, FaultType.REST_SERVICE_UNAVAILABLE);
        } else {
            // Any other general error that we do not handle specifically
            exceptionMessageTranslator.addCommonInfo(dto, e, CoffeeFaultType.OPERATION_FAILED);
        }

        String accept = servletRequest.getHeader(HttpHeaders.ACCEPT);
        if (accept != null && ((accept.indexOf("json") < 1 && accept.indexOf("xml") < 1) || !accept.startsWith("application"))) {
            accept = MediaType.APPLICATION_XML;
        }
        return Response.fromResponse(e.getResponse()).entity(dto).type(StringUtils.defaultString(accept, MediaType.APPLICATION_XML)).build();
    }

    private String getNotAcceptableMessage() {
        return FaultType.REST_NOT_ACCEPTABLE + "\n" + exceptionMessageTranslator.getLocalizedMessage(FaultType.REST_NOT_ACCEPTABLE);
    }

    /**
     * "It searches for the {@code ResourceInvoker} with a wildcard content type. This is used to determine if there are any errors with the request besides an invalid content type
     *
     * @return The details of the found ResourceInvoker
     */
    private ResourceInvoker getWildcardContentTypeResourceInvoker() {
        HttpRequest originalRequest = ResteasyProviderFactory.getInstance().getContextData(HttpRequest.class);

        try {
            MockHttpRequest modifiedRequest = MockHttpRequest.deepCopy(originalRequest)//
                    .contentType((MediaType) null)//
                    .contentType(MediaType.WILDCARD_TYPE);
            Registry registry = ResteasyProviderFactory.getInstance().getContextData(Registry.class);
            return registry.getResourceInvoker(modifiedRequest);
        } catch (IOException e) {
            log.error("Error in error handler", e);
            return null;
        }
    }

}
