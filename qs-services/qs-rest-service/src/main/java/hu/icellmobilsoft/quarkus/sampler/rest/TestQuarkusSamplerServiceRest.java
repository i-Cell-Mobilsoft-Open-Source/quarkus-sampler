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
package hu.icellmobilsoft.quarkus.sampler.rest;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.rest.rest.BaseRestService;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.test.ITestQuarkusSamplerServiceRest;
import hu.icellmobilsoft.quarkus.sampler.common.core.logging.LogMethodEntryAndExit;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.test.test.TestResponse;
import hu.icellmobilsoft.quarkus.sampler.rest.action.SamplerPostAction;
import hu.icellmobilsoft.quarkus.sampler.rest.action.SamplerPutAction;
import hu.icellmobilsoft.quarkus.sampler.rest.action.SamplerTestAction;

/**
 * Sampler Service rest implementation
 *
 * @author speter555
 * @since 0.1.0
 */
@Model
public class TestQuarkusSamplerServiceRest extends BaseRestService implements ITestQuarkusSamplerServiceRest {

    @Inject
    SamplerTestAction samplerTestAction;

    @Inject
    SamplerPostAction samplerPostAction;

    @Inject
    SamplerPutAction samplerPutAction;
    /**
     * Default constructor
     */
    public TestQuarkusSamplerServiceRest() {
        // Default constructor for java 21
    }

    @Override
    public TestResponse getTest(String testString, Integer testInteger, Long testLong, Boolean testBoolean) throws BaseException {
        return wrapPathParam4(
                samplerTestAction::getTest,
                testString,
                testInteger,
                testLong,
                testBoolean,
                "getTest",
                "testString",
                "testInteger",
                "testLong",
                "testBoolean");
    }

    @Override
    @LogMethodEntryAndExit
    public SampleResponse postTest(BaseRequest baseRequest, String testString, Integer testInteger, Long testLong, Boolean testBoolean)
            throws BaseException {
        return samplerPostAction.postTest(baseRequest, testString, testInteger, testLong, testBoolean);
    }

    @Override
    @LogMethodEntryAndExit
    public SampleResponse postTest(SampleRequest request) throws BaseException {
        return samplerPostAction.postTest(request);
    }

    @Override
    @LogMethodEntryAndExit
    public SampleResponse putTest(BaseRequest baseRequest, String testString, Integer testInteger, Long testLong, Boolean testBoolean)
            throws BaseException {
        return samplerPutAction.putTest(baseRequest, testString, testInteger, testLong, testBoolean);
    }
}
