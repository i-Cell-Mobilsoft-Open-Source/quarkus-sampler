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
package hu.icellmobilsoft.quarkus.sampler.api.jakarta.dto.exception.enums;

import hu.icellmobilsoft.coffee.dto.error.IFaultType;

/**
 * Project specific faults
 *
 * @author speter555
 * @since 0.1.0
 *
 */
public enum FaultType implements IFaultType<FaultType> {

    /**
     * HTTP 400 Response.Status.BAD_REQUEST<br>
     * Localized handling of TechnicalFault<br>
     * Original source is javax.ws.rs.BadRequestException
     */
    REST_BAD_REQUEST,
    /**
     * HTTP 401 Response.Status.UNAUTHORIZED<br>
     * Localized handling of TechnicalFault<br>
     * Original source is javax.ws.rs.NotAuthorizedException
     */
    REST_UNAUTHORIZED,
    /**
     * HTTP 403 Response.Status.FORBIDDEN<br>
     * Localized handling of TechnicalFault<br>
     * Original source is javax.ws.rs.ForbiddenException
     */
    REST_FORBIDDEN,
    /**
     * HTTP 404 Response.Status.NOT_FOUND<br>
     * Localized handling of TechnicalFault<br>
     * Original source is javax.ws.rs.NotFoundException
     */
    REST_NOT_FOUND,
    /**
     * HTTP 405 Response.Status.METHOD_NOT_ALLOWED<br>
     * Localized handling of TechnicalFault<br>
     * Original source is javax.ws.rs.NotAllowedException
     */
    REST_METHOD_NOT_ALLOWED,
    /**
     * HTTP 406 Response.Status.NOT_ACCEPTABLE<br>
     * Localized handling of TechnicalFault<br>
     * Original source is javax.ws.rs.NotAcceptableException
     */
    REST_NOT_ACCEPTABLE,
    /**
     * HTTP 415 Response.Status.UNSUPPORTED_MEDIA_TYPE<br>
     * Localized handling of TechnicalFault<br>
     * Original source is javax.ws.rs.NotSupportedException
     */
    REST_UNSUPPORTED_MEDIA_TYPE,
    /**
     * HTTP 500 Response.Status.INTERNAL_SERVER_ERROR<br>
     * Localized handling of TechnicalFault<br>
     * Original source is javax.ws.rs.InternalServerErrorException
     */
    REST_INTERNAL_SERVER_ERROR,
    /**
     * HTTP 503 Response.Status.SERVICE_UNAVAILABLE<br>
     * Localized handling of TechnicalFault<br>
     * Original source is javax.ws.rs.ServiceUnavailableException
     */
    REST_SERVICE_UNAVAILABLE,

    /**
     * HTTP 400 Response.Status.BAD_REQUEST<br>
     * Request could not be converted to target object<br>
     */
    INVALID_TYPE_ERROR,

    /**
     * HTTP 400 Response.Status.BAD_REQUEST<br>
     * The request sent could not be parsed as a valid JSON<br>
     */
    JSON_PARSE_ERROR,

    /**
     * HTTP 400 Response.Status.BAD_REQUEST<br>
     * Could not deserialize JSON<br>
     */
    JSON_INVALID_FORMAT_ERROR,

    /**
     * HTTP 400 Response.Status.BAD_REQUEST<br>
     * JSON mapping exception<br>
     */
    JSON_MAPPING_ERROR,

    /**
     * HTTP 400 Response.Status.BAD_REQUEST<br>
     * HTTP message cannot be processed<br>
     */
    HTTP_MESSAGE_ERROR,

    /**
     * HTTP 400 Response.Status.BAD_REQUEST<br>
     * General I/O exception<br>
     */
    IO_ERROR,

    /**
     * HTTP 400 Response.Status.BAD_REQUEST<br>
     * Validation error<br>
     */
    VALIDATION_ERROR,

    /**
     * Invalid response - xsd validation failed
     */
    SAMPLE_INVALID_RESPONSE,
}
