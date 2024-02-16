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
package hu.icellmobilsoft.quarkus.sampler.rest.test.restclient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.QuarkusSamplerPath;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.test.ITestQuarkusSamplerServiceRest;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.test.test.TestResponse;

/**
 * RestClient with xml in contentType and Accept.
 *
 * @author speter555
 * @since 0.1.0
 * @see ITestQuarkusSamplerServiceRest
 */
@Path(QuarkusSamplerPath.TEST_QUARKUS_SAMPLER_SERVICE)
@RegisterRestClient
public interface XmlITestQuarkusSamplerServiceRest extends ITestQuarkusSamplerServiceRest {

    /**
     * Test GET operation
     *
     * @param testString
     *            {@link String} test value
     * @param testInteger
     *            {@link Integer} test value
     * @param testLong
     *            {@link Long} test value
     * @param testBoolean
     *            {@link Boolean} test value
     * @return {@link TestResponse} response
     * @throws BaseException
     *             if any error occurs
     */
    @GET
    @Produces(value = { MediaType.APPLICATION_XML })
    TestResponse getTest(@QueryParam(QuarkusSamplerPath.PARAM_TEST_STRING) String testString,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_INTEGER) Integer testInteger, @QueryParam(QuarkusSamplerPath.PARAM_TEST_LONG) Long testLong,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_BOOLEAN) Boolean testBoolean) throws BaseException;

    /**
     * Test POST operation
     *
     * @param baseRequest
     *            base request from body
     * @param testString
     *            {@link String} test value
     * @param testInteger
     *            {@link Integer} test value
     * @param testLong
     *            {@link Long} test value
     * @param testBoolean
     *            {@link Boolean} test value
     * @return {@link SampleResponse} response
     * @throws BaseException
     *             if any error occurs
     */
    @POST
    @Consumes(value = { MediaType.APPLICATION_XML })
    @Produces(value = { MediaType.APPLICATION_XML })
    SampleResponse postTest(BaseRequest baseRequest, @QueryParam(QuarkusSamplerPath.PARAM_TEST_STRING) String testString,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_INTEGER) Integer testInteger, @QueryParam(QuarkusSamplerPath.PARAM_TEST_LONG) Long testLong,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_BOOLEAN) Boolean testBoolean) throws BaseException;
}
