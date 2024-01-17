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
package hu.icellmobilsoft.quarkus.sampler.common.rest.cdi;

import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Application scoped container class
 *
 * @author speter555
 *
 */
@ApplicationScoped
public class ApplicationContainer {

    private Map<String, Object> objectMap;

    /**
     * Default constructor
     */
    public ApplicationContainer() {
        // Default constructor for java 21
    }

    /**
     * Get objectMap
     * 
     * @return objectMap
     */
    public Map<String, Object> getObjectMap() {
        if (objectMap == null) {
            objectMap = new HashMap<>();
        }
        return objectMap;
    }
}
