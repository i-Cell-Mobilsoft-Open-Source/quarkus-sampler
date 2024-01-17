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
package hu.icellmobilsoft.quarkus.sampler.rest.jsonb;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Instance;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.config.BinaryDataStrategy;

import org.eclipse.yasson.FieldAccessStrategy;

import io.quarkus.jsonb.JsonbConfigCustomizer;

/**
 * Jsonb Config customize
 * 
 * @since 0.1.0
 * @author speter555
 */
public class CustomJsonbConfig {

    /**
     * Default constructor
     */
    public CustomJsonbConfig() {
        // Default constructor for java 21
    }

    // Replaces the CDI producer for JsonbConfig built into Quarkus
    @Dependent
    JsonbConfig jsonConfig(Instance<JsonbConfigCustomizer> customizers) {

        JsonbConfig config = new JsonbConfig()
                // property visibility strategy setting
                .withPropertyVisibilityStrategy(new FieldAccessStrategy())
                .withBinaryDataStrategy(BinaryDataStrategy.BASE_64);
        // Apply all JsonbConfigCustomizer beans (incl. Quarkus)
        // Default customizer in quarkus: io.quarkus.jsonb.customizer.RegisterSerializersAndDeserializersCustomizer (it is a virtual class)
        for (JsonbConfigCustomizer customizer : customizers) {
            customizer.customize(config);
        }

        return config;
    }

}
