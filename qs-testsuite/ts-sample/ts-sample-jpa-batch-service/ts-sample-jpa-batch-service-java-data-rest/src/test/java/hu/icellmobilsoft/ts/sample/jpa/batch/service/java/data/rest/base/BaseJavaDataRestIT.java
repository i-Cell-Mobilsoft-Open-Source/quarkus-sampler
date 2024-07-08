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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.java.data.rest.base;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.jpa.IJavaDataRest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataInsertType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataResponse;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;

/**
 * Base class for {@link IJavaDataRest} tests.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
public abstract class BaseJavaDataRestIT extends BaseSampleIT {

    /**
     * Creates a {@link IJavaDataRest} rest client.
     *
     * @return {@link IJavaDataRest}.
     */
    protected IJavaDataRest getRestClient() {
        return getRestClient(IJavaDataRest.class);
    }

    /**
     * Creates a JavaData.
     *
     * @param data
     *            data
     * @return {@link JavaDataResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    protected JavaDataResponse createJavaData(String data) throws BaseException {
        JavaDataInsertType javaData = new JavaDataInsertType().withData(data);

        JavaDataInsertRequest request = new JavaDataInsertRequest().withJavaData(javaData);
        request.setContext(DtoHelper.createContext());

        return getRestClient().postInsertJavaDataEntityWithBatchService(request);
    }
}
