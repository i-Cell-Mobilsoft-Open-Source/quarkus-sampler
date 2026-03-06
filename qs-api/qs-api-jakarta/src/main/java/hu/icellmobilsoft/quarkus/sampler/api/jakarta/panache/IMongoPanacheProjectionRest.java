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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.MongoPanacheServicePath;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;

/**
 * REST service interface for mongo panache projection actions.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@Tag(name = "REST endpoints for mongo panache service calls", description = "REST endpoints for mongo panache service calls")
@Path(MongoPanacheServicePath.REST_MONGO_PANACHE_SERVICE_PROJECTION)
public interface IMongoPanacheProjectionRest {

    @Operation(summary = "Get all between with projection",
            description = "Retrieves all mongo sample entities between the specified dates using projection.")
    @GET
    @Path("/between")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse getAllBetweenWithProjection(@QueryParam("from") String fromString, @QueryParam("to") String toString) throws BaseException;

    @Operation(summary = "Get all by status with projection",
            description = "Retrieves all mongo sample entities with the specified status using projection.")
    @PUT
    @Path("/byStatus")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse getAllByStatusProjection(@QueryParam("status") SampleStatusEnumType status);
}
