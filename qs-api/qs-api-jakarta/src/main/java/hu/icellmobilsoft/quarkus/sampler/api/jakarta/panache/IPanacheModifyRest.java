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

import java.util.List;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.PanacheServicePath;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.ServicePath;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;

/**
 * REST service interface for panache modify actions.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@Tag(name = "REST endpoints for panache service calls", description = "REST endpoints for panache service calls")
@Path(PanacheServicePath.REST_PANACHE_SERVICE_MODIFY)
public interface IPanacheModifyRest {

    @Operation(summary = "Modify sample entity", description = "Modifies a sample entity in the database.")
    @PUT
    @Path("/modify")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse putModify() throws BaseException;

    @Operation(summary = "Bulk update sample entity status", description = "Updates the status of multiple sample entities identified by their IDs.")
    @PUT
    @Path("/bulkModifyStatus")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse putBulkUpdateStatus(@QueryParam(ServicePath.PARAM_ID) List<String> ids, @QueryParam("status") SampleStatusEnumType status)
            throws BaseException;

    @Operation(summary = "Delete failed for sample entities",
            description = "Attempts to delete a non-existing SampleEntity to demonstrate exception handling.")
    @DELETE
    @Path("/deleteFailed")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse deleteFailed();

    @Operation(summary = "Delete sample entity by ID", description = "Deletes a sample entity identified by its ID.")
    @DELETE
    @Path("/deleteById" + ServicePath.ID)
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse deleteById(@PathParam(ServicePath.PARAM_ID) String id) throws BaseException;

    @Operation(summary = "Delete first found sample entity", description = "Deletes the first found sample entity in the database.")
    @DELETE
    @Path("/deleteFirstFind")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse deleteFirstFind();
}
