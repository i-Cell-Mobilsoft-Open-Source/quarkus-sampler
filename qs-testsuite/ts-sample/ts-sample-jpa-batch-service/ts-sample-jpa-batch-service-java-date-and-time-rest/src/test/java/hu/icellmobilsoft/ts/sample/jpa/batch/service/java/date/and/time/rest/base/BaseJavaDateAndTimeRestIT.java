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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.java.date.and.time.rest.base;

import java.time.OffsetDateTime;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJavaDateAndTimeRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadateandtime.JavaDateAndTimeInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadateandtime.JavaDateAndTimeInsertType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadateandtime.JavaDateAndTimeResponse;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;

/**
 * Base class for {@link IJavaDateAndTimeRest} tests.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
public abstract class BaseJavaDateAndTimeRestIT extends BaseSampleIT {

    /**
     * Creates a {@link IJavaDateAndTimeRest} rest client.
     *
     * @return {@link IJavaDateAndTimeRest}.
     */
    protected IJavaDateAndTimeRest getRestClient() {
        return getRestClient(IJavaDateAndTimeRest.class);
    }

    /**
     * Creates a JavaDateAndTime.
     *
     * @param baseDateTime
     *            {@link OffsetDateTime}.
     * @return {@link JavaDataResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    protected JavaDateAndTimeResponse createJavaDateAndTime(OffsetDateTime baseDateTime) throws BaseException {
        JavaDateAndTimeInsertType javaDateAndTime = new JavaDateAndTimeInsertType().withBaseDateTime(baseDateTime);

        JavaDateAndTimeInsertRequest request = new JavaDateAndTimeInsertRequest().withJavaDateAndTime(javaDateAndTime);
        request.setContext(DtoHelper.createContext());

        return getRestClient().postInsertJavaDateAndTimeEntityWithBatchService(request);
    }
}
