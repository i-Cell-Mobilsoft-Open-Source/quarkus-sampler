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
package hu.icellmobilsoft.quarkus.sampler.rest.restclient.exception;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Alternative;
import jakarta.ws.rs.core.Response;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BONotFound;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseExceptionResultType;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BusinessFault;
import hu.icellmobilsoft.coffee.dto.common.commonservice.TechnicalFault;
import hu.icellmobilsoft.coffee.dto.exception.AccessDeniedException;
import hu.icellmobilsoft.coffee.dto.exception.BONotFoundException;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.module.mp.restclient.RestClientPriority;
import hu.icellmobilsoft.coffee.module.mp.restclient.exception.FaultTypeParser;
import hu.icellmobilsoft.coffee.module.mp.restclient.provider.DefaultBaseExceptionResponseExceptionMapper;

/**
 * Default REST client exception converter. ExceptionMapping packs this to WebApplicationException with the same cause thrown here
 *
 * @author speter555
 * @since 0.1.0
 */
@Priority(value = RestClientPriority.EXCEPTION_BASE)
@Dependent
@Alternative
public class ProjectBaseExceptionResponseExceptionMapper extends DefaultBaseExceptionResponseExceptionMapper {

    @Override
    public BaseException toThrowable(Response response) {
        int responseStatus = response.getStatus();

        if (responseStatus == HTTP_STATUS_I_AM_A_TEAPOT) {
            BONotFound dto = readEntity(response, BONotFound.class);
            if (dto != null) {
                return new BONotFoundException(
                        "Entity not found on REST call:\nMessage: [" + dto.getMessage() + "]\nfaultType: [" + dto.getFaultType() + "]");
            }
        } else if (responseStatus == jakarta.ws.rs.core.Response.Status.UNAUTHORIZED.getStatusCode()) {
            BaseExceptionResultType dto = readBaseExceptionResultType(response);
            if (dto != null) {
                return new AccessDeniedException(FaultTypeParser.parseFaultType(dto.getFaultType()), dto.getMessage());
            }
        } else if (responseStatus == jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()
                || responseStatus == jakarta.ws.rs.core.Response.Status.BAD_REQUEST.getStatusCode()) {
            BaseExceptionResultType dto = readBaseExceptionResultType(response);
            if (dto != null) {
                return new MPRestClientBaseException(FaultTypeParser.parseFaultType(dto.getFaultType()), dto.getMessage());
            }
        }
        return super.toThrowable(response);
    }

    private BaseExceptionResultType readBaseExceptionResultType(Response response) {
        BaseExceptionResultType dto;
        try {
            dto = readEntity(response, TechnicalFault.class);
        } catch (ClassCastException e) {
            dto = readEntity(response, BusinessFault.class);
        }
        return dto;
    }

}
