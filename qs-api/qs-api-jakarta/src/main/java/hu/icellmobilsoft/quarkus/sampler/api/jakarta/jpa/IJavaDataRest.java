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

import hu.icellmobilsoft.quarkus.sampler.api.schema.XsdConstants;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataUpdateRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.cdi.annotation.xml.ValidateXML;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.JpaBatchServicePath;

/**
 * REST endpoints for JavaData entities.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
@Tag(name = "REST endpoints for JavaData entities", description = "REST endpoints for JavaData entities")
@Path(JpaBatchServicePath.REST_JPA_BATCH_SERVICE_JAVA_DATA)
public interface IJavaDataRest {

    /**
     * Creates and inserts a JavaData entity with BatchService.
     *
     * @param javaDataInsertRequest
     *            {@link JavaDataInsertRequest}.
     * @return {@link JavaDataResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    @Operation(summary = "Creates and inserts a JavaData entity with BatchService.",
            description = "Creates and inserts a JavaData entity with BatchService.")
    @POST
    @Path(JpaBatchServicePath.INSERT)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    JavaDataResponse postInsertJavaDataEntityWithBatchService(
            @ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) JavaDataInsertRequest javaDataInsertRequest) throws BaseException;

    /**
     * Updates a JavaData entity with BatchService.
     *
     * @param javaDataId
     *            ID of JavaData.
     * @param javaDataUpdateRequest
     *            {@link JavaDataUpdateRequest}.
     * @return {@link JavaDataResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    @Operation(summary = "Updates a JavaData entity with BatchService.", description = "Updates a JavaData entity with BatchService.")
    @PUT
    @Path(JpaBatchServicePath.UPDATE_JAVA_DATA_ID)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    JavaDataResponse putUpdateJavaDataEntityWithBatchService(
            @PathParam(JpaBatchServicePath.PARAM_JAVA_DATA_ID) @Parameter(name = JpaBatchServicePath.PARAM_JAVA_DATA_ID,
                    description = "JAVA_DATA.X__ID", required = true) String javaDataId,
            @ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) JavaDataUpdateRequest javaDataUpdateRequest) throws BaseException;

    /**
     * Deletes all JavaData entities with BatchService.
     *
     * @return {@link BaseResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    @Operation(summary = "Deletes all JavaData entities with BatchService.", description = "Deletes all JavaData entities with BatchService.")
    @DELETE
    @Path(JpaBatchServicePath.DELETE)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    BaseResponse deleteAllJavaDataEntitiesWithBatchService() throws BaseException;
}
