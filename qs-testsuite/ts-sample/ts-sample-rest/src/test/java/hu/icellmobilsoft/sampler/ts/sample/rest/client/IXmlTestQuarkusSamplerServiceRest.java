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
package hu.icellmobilsoft.sampler.ts.sample.rest.client;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import hu.icellmobilsoft.coffee.cdi.annotation.xml.ValidateXML;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifier;
import hu.icellmobilsoft.coffee.rest.log.annotation.LogSpecifiers;
import hu.icellmobilsoft.coffee.rest.log.annotation.enumeration.LogSpecifierTarget;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.path.QuarkusSamplerPath;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.test.ITestQuarkusSamplerServiceRest;
import hu.icellmobilsoft.quarkus.sampler.api.schema.XsdConstants;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.test.test.TestResponse;

/**
 * Xml rest client interface
 * 
 * @author speter555
 * @since 0.1.0
 */
@Path(QuarkusSamplerPath.TEST_QUARKUS_SAMPLER_SERVICE)
public interface IXmlTestQuarkusSamplerServiceRest extends ITestQuarkusSamplerServiceRest {

    /**
     * Test tag for openapi
     */
    String TAG = "Test REST operations";

    /**
     * Description of test parameters
     */
    String TEST_DESCRIPTION = "Query param for testing";

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
    @Operation(summary = "Test GET operation", //
            description = "Dummy operation for testing Sampler service")
    @Tag(ref = ITestQuarkusSamplerServiceRest.TAG)
    @GET
    @LogSpecifiers(value = { @LogSpecifier(target = { LogSpecifierTarget.CLIENT_REQUEST }, maxEntityLogSize = 10000),
            @LogSpecifier(target = { LogSpecifierTarget.CLIENT_RESPONSE }, maxEntityLogSize = 1000) })
    @Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    TestResponse getTest(
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_STRING) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_STRING,
                    description = TEST_DESCRIPTION) String testString,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_INTEGER) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_INTEGER,
                    description = TEST_DESCRIPTION) Integer testInteger,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_LONG) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_LONG,
                    description = TEST_DESCRIPTION) Long testLong,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_BOOLEAN) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_BOOLEAN,
                    description = TEST_DESCRIPTION) Boolean testBoolean)
            throws BaseException;

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
    @Operation(summary = "Test POST operation", //
            description = "Dummy operation for testing Sampler service")
    @Tag(ref = ITestQuarkusSamplerServiceRest.TAG)
    @POST
    @LogSpecifier(target = { LogSpecifierTarget.CLIENT_REQUEST }, maxEntityLogSize = 20)
    @LogSpecifier(target = { LogSpecifierTarget.CLIENT_RESPONSE }, noLog = true)
    @Consumes(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    SampleResponse postTest(@ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) BaseRequest baseRequest,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_STRING) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_STRING,
                    description = TEST_DESCRIPTION) String testString,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_INTEGER) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_INTEGER,
                    description = TEST_DESCRIPTION) Integer testInteger,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_LONG) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_LONG,
                    description = TEST_DESCRIPTION) Long testLong,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_BOOLEAN) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_BOOLEAN,
                    description = TEST_DESCRIPTION) Boolean testBoolean)
            throws BaseException;

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
    @Operation(summary = "Test POST operation", //
            description = "Dummy operation for testing Sampler service")
    @Tag(ref = ITestQuarkusSamplerServiceRest.TAG)
    @PUT
    @Consumes(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @LogSpecifier(target = LogSpecifierTarget.REQUEST, maxEntityLogSize = 10)
    @LogSpecifier(target = LogSpecifierTarget.RESPONSE, maxEntityLogSize = 5000)
    SampleResponse putTest(@ValidateXML(xsdPath = XsdConstants.SUPER_XSD_PATH) BaseRequest baseRequest,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_STRING) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_STRING,
                    description = TEST_DESCRIPTION) String testString,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_INTEGER) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_INTEGER,
                    description = TEST_DESCRIPTION) Integer testInteger,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_LONG) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_LONG,
                    description = TEST_DESCRIPTION) Long testLong,
            @QueryParam(QuarkusSamplerPath.PARAM_TEST_BOOLEAN) @Parameter(name = QuarkusSamplerPath.PARAM_TEST_BOOLEAN,
                    description = TEST_DESCRIPTION) Boolean testBoolean)
            throws BaseException;
}
