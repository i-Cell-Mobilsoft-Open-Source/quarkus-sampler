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
package hu.icellmobilsoft.quarkus.sample.health;

import java.util.UUID;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

/**
 * Provide base functionality for health checks
 *
 * @author speter555
 * @since 0.1.0
 */
public abstract class AbstractBaseHealthCheck {

    /**
     * Quarkus uuid key
     */
    public static final String QUARKUS_UUID = "quarkusUUID";

    /**
     * Quarkus uuid config key
     */
    public static final String QUARKUS_UUID_KEY = "quarkus.uuid";

    /**
     * create HealthCheckResponseBuilder
     *
     * @return response builder
     */
    public HealthCheckResponseBuilder createHealthCheckResponseBuilder() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.builder().name(getBuilderName());
        builder.withData(QUARKUS_UUID, getQuarkusUuid());
        return builder;
    }

    /**
     * get unique server identifier
     *
     * @return Quarkus UUID
     */
    public String getQuarkusUuid() {
        UUID quarkusUuid = ConfigProvider.getConfig().getValue(QUARKUS_UUID_KEY, UUID.class);
        return quarkusUuid.toString();
    }

    /**
     * get the response builder name
     *
     * @return builder name
     */
    public abstract String getBuilderName();
}
