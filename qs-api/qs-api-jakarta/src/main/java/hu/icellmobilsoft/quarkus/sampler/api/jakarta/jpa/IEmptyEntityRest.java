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
package hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.cdi.annotation.xml.ValidateXML;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.JpaBatchServicePath;
import hu.icellmobilsoft.quarkus.sampler.api.schema.XsdConstants;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.emptyentity.EmptyEntityResponse;

/**
 * REST endpoints for EmptyEntity entities.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
@Tag(name = "REST endpoints for EmptyEntity entities", description = "REST endpoints for EmptyEntity entities")
@Path(JpaBatchServicePath.REST_JPA_BATCH_SERVICE_EMPTY_ENTITY)
public interface IEmptyEntityRest {

    /**
     * Creates an EmptyEntity.
     *
     * @param baseRequest
     *            {@link BaseRequest}.
     * @return {@link EmptyEntityResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    @Operation(summary = "Creates an EmptyEntity.", description = "Creates an EmptyEntity.")
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    EmptyEntityResponse postEmptyEntity(@ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) BaseRequest baseRequest) throws BaseException;
}
