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

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.PanacheServicePath;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;

/**
 * REST service interface for panache query actions.
 * 
 * @author balazs.joo
 * @since 0.1.0
 */
@Tag(name = "REST endpoints for panache service calls", description = "REST endpoints for panache service calls")
@Path(PanacheServicePath.REST_PANACHE_SERVICE_QUERY)
public interface IPanacheQueryRest {

    @GET
    @Path("/findFailure")
    @Operation(summary = "Get find failure", description = "Attempts to find entities with a query that is designed to fail.")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse getFindFailure();

    @GET
    @Path("/byStatus")
    @Operation(summary = "Get all by status", description = "Retrieves all sample entities with the specified status.")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse getAllByStatus(@QueryParam("status") SampleStatusEnumType status);

    @GET
    @Path("/between")
    @Operation(summary = "Get all between", description = "Retrieves all sample entities between the specified dates.")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse getAllBetween(@QueryParam("from") String fromString, @QueryParam("to") String toString) throws BaseException;

    @GET
    @Path("/idsBetween")
    @Operation(summary = "Get all ids between", description = "Retrieves the IDs of all sample entities between the specified dates.")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse getAllIdsBetween(@QueryParam("from") String fromString, @QueryParam("to") String toString) throws BaseException;

    @GET
    @Path("/betweenNative")
    @Operation(summary = "Get all between native", description = "Retrieves all sample entities between the specified dates using a native query.")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse getAllBetweenNative(@QueryParam("from") String fromString, @QueryParam("to") String toString) throws BaseException;

    @GET
    @Path("/idsBetweenSampleEntityCreation")
    @Operation(summary = "Get all ids between sample entity creation",
            description = "Retrieves the IDs of all sample entities based on their creation timestamps between the specified dates.")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse getAllIdsBetweenSampleEntityCreation(@QueryParam("from") String fromString, @QueryParam("to") String toString) throws BaseException;

    @POST
    @Path("/query")
    @Operation(summary = "Post query", description = "Performs a query based on the provided criteria and supports pagination.")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse postQuery(@QueryParam("status") SampleStatusEnumType status, @QueryParam("from") String fromString,
            @QueryParam("to") String toString, @QueryParam("page") int page, @QueryParam("size") int size) throws BaseException;

    @POST
    @Path("/queryCriteria")
    @Operation(summary = "Post query criteria",
            description = "Performs a criteria-based query based on the provided criteria and supports pagination.")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse postQueryCriteria(@QueryParam("status") SampleStatusEnumType status, @QueryParam("from") String fromString,
            @QueryParam("to") String toString, @QueryParam("page") int page, @QueryParam("size") int size) throws BaseException;
}
