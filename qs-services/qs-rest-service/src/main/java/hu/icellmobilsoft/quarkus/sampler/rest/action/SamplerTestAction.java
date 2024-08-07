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

import java.text.MessageFormat;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.rest.projectstage.ProjectStage;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.test.test.TestResponse;

/**
 * Sampler Test action
 *
 * @author speter555
 * @since 0.1.0
 */
@Model
public class SamplerTestAction extends BaseAction {

    @Inject
    @ThisLogger
    AppLogger log;

    @Inject
    ProjectStage projectStage;

    /**
     * Default constructor
     */
    public SamplerTestAction() {
        // Default constructor for java 21
    }

    /**
     *
     * Test action process
     *
     * @param testString
     *            Query parameter for testing
     * @param testInteger
     *            Query parameter for testing
     * @param testLong
     *            Query parameter for testing
     * @param testBoolean
     *            Query parameter for testing
     * @return {@link TestResponse} Test respons
     * @throws BaseException
     *             error
     */
    public TestResponse getTest(String testString, Integer testInteger, Long testLong, Boolean testBoolean) throws BaseException {
        log.info(MessageFormat.format("Query parameters: [{0}], [{1}], [{2}], [{3}]", testString, testInteger, testLong, testBoolean));

        TestResponse response = new TestResponse();
        response.setFuncCode(FunctionCodeType.OK);
        handleSuccessResultType(response);
        return response;
    }

}
