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
package hu.icellmobilsoft.quarkus.sampler.api.jakarta.panache;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.cdi.annotation.xml.ValidateXML;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.PanacheServicePath;
import hu.icellmobilsoft.quarkus.sampler.api.schema.XsdConstants;

/**
 * REST endpoints for panache service calls.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@Tag(name = "REST endpoints for panache service calls", description = "REST endpoints for panache service calls")
@Path(PanacheServicePath.REST_PANACHE_SERVICE)
public interface IPanacheServiceRest {

    /**
     * Runs through every implemented panache method
     *
     * @param baseRequest
     *            {@link BaseRequest}.
     * @return {@link BaseResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    @Operation(summary = "Runs through every implemented panache method", description = "Runs through every implemented panache method")
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    BaseResponse postPanacheMethods(@ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) BaseRequest baseRequest) throws BaseException;
}
