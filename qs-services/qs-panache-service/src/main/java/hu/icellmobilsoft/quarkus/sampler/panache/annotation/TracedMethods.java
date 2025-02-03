/*-
 * #%L
 * Quarkus-sampler
 * %%
 * Copyright (C) 2024 - 2025 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.quarkus.sampler.panache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.interceptor.InterceptorBinding;

/**
 * Annotation for enabling method-level tracing using OpenTelemetry.
 * <p>
 * This annotation is used to automatically trace the execution of methods or classes by applying an OpenTelemetry-based interceptor.
 * <p>
 * Features:
 * <ul>
 * <li>Records method execution duration</li>
 * <li>Captures method parameters and exceptions</li>
 * <li>Integrates with OpenTelemetry-compatible tracing tools (e.g., Jaeger, Grafana Tempo)</li>
 * </ul>
 * <p>
 * Usage:
 * 
 * <pre>{@code
 * @TracedMethods
 * public void process(String data) {
 *     // Business logic
 * }
 * }</pre>
 * <p>
 *
 * @see jakarta.interceptor.InterceptorBinding
 * @author balazs.joo
 * @since 0.1.0
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface TracedMethods {
}
