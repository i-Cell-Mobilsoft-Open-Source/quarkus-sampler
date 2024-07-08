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
package hu.icellmobilsoft.quarkus.sampler.rest.action;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sample.common.util.string.RandomUtil;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleStatusEnumType;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleType;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleValueEnumType;
import hu.icellmobilsoft.quarkus.sampler.rest.restclient.ITestQuarkusSamplerServiceRestRegisteredClient;

/**
 * Sampler Put Test action
 *
 * @author speter555
 * @since 0.1.0
 */
@Model
public class SamplerPutAction extends BaseAction {

    @Inject
    @RestClient
    ITestQuarkusSamplerServiceRestRegisteredClient testRestRegisteredClient;

    /**
     * Default constructor
     */
    public SamplerPutAction() {
        // Default constructor for java 21
    }

    /**
     * Test logic. Test LogSpecifier
     * 
     * @param baseRequest
     *            base request parameter from post http call
     * @param testString
     *            Query parameter for testing
     * @param testInteger
     *            Query parameter for testing
     * @param testLong
     *            Query parameter for testing
     * @param testBoolean
     *            Query parameter for testing
     * @return {@link SampleResponse} Sample response
     * @throws BaseException
     *             asdf
     */
    public SampleResponse putTest(BaseRequest baseRequest, String testString, Integer testInteger, Long testLong, Boolean testBoolean)
            throws BaseException {
        testRestRegisteredClient.postTest(baseRequest, testString, testInteger, testLong, testBoolean);
        SampleResponse response = new SampleResponse();
        response.setSample(
                new SampleType().withSampleId(RandomUtil.generateId())
                        .withSampleStatus(SampleStatusEnumType.DONE)
                        .withColumnA("A")
                        .withColumnB(SampleValueEnumType.VALUE_C));
        handleSuccessResultType(response, baseRequest);
        return response;
    }

}
