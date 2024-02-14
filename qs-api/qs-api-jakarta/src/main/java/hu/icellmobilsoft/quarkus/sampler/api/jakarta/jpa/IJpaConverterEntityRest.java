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
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.jpaconverterentity.JpaConverterEntityUpdateRequest;
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
 * REST endpoints for JpaConverterEntity entities.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
@Tag(name = "REST endpoints for JpaConverterEntity entities", description = "REST endpoints for JpaConverterEntity entities")
@Path(JpaBatchServicePath.REST_JPA_BATCH_SERVICE_JPA_CONVERTER_ENTITY)
public interface IJpaConverterEntityRest {

    /**
     * Creates and inserts a JpaConverterEntity entity with BatchService.
     *
     * @param jpaConverterEntityInsertRequest
     *            {@link JpaConverterEntityInsertRequest}.
     * @return {@link JpaConverterEntityResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    @Operation(summary = "Creates and inserts a JpaConverterEntity entity with BatchService.",
            description = "Creates and inserts a JpaConverterEntity entity with BatchService.")
    @POST
    @Path(JpaBatchServicePath.INSERT)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    JpaConverterEntityResponse postInsertJpaConverterEntityEntityWithBatchService(
            @ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) JpaConverterEntityInsertRequest jpaConverterEntityInsertRequest) throws BaseException;

    /**
     * Updates a JpaConverterEntity entity with BatchService.
     *
     * @param jpaConverterEntityId
     *            ID of JpaConverterEntity.
     * @param jpaConverterEntityUpdateRequest
     *            {@link JpaConverterEntityUpdateRequest}.
     * @return {@link JpaConverterEntityResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    @Operation(summary = "Updates a JpaConverterEntity entity with BatchService.",
            description = "Updates a JpaConverterEntity entity with BatchService.")
    @PUT
    @Path(JpaBatchServicePath.UPDATE_JPA_CONVERTER_ENTITY_ID)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    JpaConverterEntityResponse putUpdateJpaConverterEntityEntityWithBatchService(
            @PathParam(JpaBatchServicePath.PARAM_JPA_CONVERTER_ENTITY_ID) @Parameter(name = JpaBatchServicePath.PARAM_JPA_CONVERTER_ENTITY_ID,
                    description = "JPA_CONVERTER_ENTITY.X__ID", required = true) String jpaConverterEntityId,
            @ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) JpaConverterEntityUpdateRequest jpaConverterEntityUpdateRequest) throws BaseException;

    /**
     * Deletes all JpaConverterEntity entities with BatchService.
     *
     * @return {@link BaseResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    @Operation(summary = "Deletes all JpaConverterEntity entities with BatchService.",
            description = "Deletes all JpaConverterEntity entities with BatchService.")
    @DELETE
    @Path(JpaBatchServicePath.DELETE)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    BaseResponse deleteAllJpaConverterEntityEntitiesWithBatchService() throws BaseException;
}
