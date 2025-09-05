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

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.PanacheServicePath;

/**
 * REST service interface for panache transaction actions.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@Tag(name = "REST endpoints for panache service calls", description = "REST endpoints for panache service calls")
@Path(PanacheServicePath.REST_PANACHE_SERVICE_TRANSACTION)
public interface IPanacheTransactionRest {

    @Operation(summary = "Attempts to save a SampleEntity that fails.", description = "Attempts to save a SampleEntity that fails.")
    @POST
    @Path("/failedSave")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse postFailedSave();

    @Operation(summary = "Attempts to perform a transaction that fails and rolls back.",
            description = "Attempts to perform a transaction that fails and rolls back.")
    @POST
    @Path("/failedTransaction")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse postFailedTransaction();

    @Operation(summary = "Saves a SampleEntity successfully.", description = "Saves a SampleEntity successfully.")
    @POST
    @Path("/successSave")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse postSuccessSave() throws BaseException;

    @Operation(summary = "Saves a SampleEntity successfully with SampleContainerEntity.",
            description = "Saves a SampleEntity successfully with SampleContainerEntity.")
    @POST
    @Path("/successSaveWithContainer")
    @Produces(MediaType.APPLICATION_JSON)
    BaseResponse postSuccessSaveWithContainer() throws BaseException;
}
