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
package hu.icellmobilsoft.quarkus.sampler.common.rest.metrics;

import java.time.LocalDateTime;

import jakarta.enterprise.inject.Model;

/**
 * RequestScope container for Microprofile-metrics
 *
 * @author speter555
 * @since 0.1.0
 */
@Model
public class MetricsContainer {

    private LocalDateTime startTime;

    private String httpMethod;

    /**
     * Default constructor
     */
    public MetricsContainer() {
        // Default constructor for java 21
    }

    /**
     * Getter of startTime
     *
     * @return startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Setter of startTime
     *
     * @param startTime
     *            startTime
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter of httpMethod
     * 
     * @return httpMethod
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * Setter of httpMethod
     * 
     * @param httpMethod
     *            httpMethod
     */
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
}
