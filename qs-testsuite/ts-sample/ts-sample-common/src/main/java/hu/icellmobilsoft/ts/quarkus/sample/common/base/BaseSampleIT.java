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
package hu.icellmobilsoft.ts.quarkus.sample.common.base;

import java.net.URI;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import hu.icellmobilsoft.roaster.restassured.BaseConfigurableWeldIT;

/**
 * Base class for Sample IT tests.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
public abstract class BaseSampleIT extends BaseConfigurableWeldIT {

    /**
     * Creates the specified rest client.
     *
     * @param restClientClass
     *            the rest client to create.
     * @return the created rest client.
     * @param <T>
     *            rest client type.
     */
    protected <T> T getRestClient(Class<T> restClientClass) {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create(ConfigProvider.getConfig().getValue("sampler.service.sample.base.uri", String.class)))
                .build(restClientClass);
    }
}
