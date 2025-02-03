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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for specifying a custom parameter name in method signatures.
 * <p>
 * This annotation can be applied to method parameters to define a specific name that can be used in logging, validation, or tracing instead of the
 * default reflection-based parameter name.
 * <p>
 * Example usage:
 * 
 * <pre>{@code
 * public void process(@ParamName("username") String name) {
 *     // Business logic
 * }
 * }</pre>
 * <p>
 * **Usage Recommendations:**
 * <ul>
 * <li>Always provide a meaningful name for clarity.</li>
 * <li>Do not use empty strings, as this defeats the purpose of naming parameters.</li>
 * <li>Ensure consistency across method parameters to avoid confusion.</li>
 * </ul>
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface ParamName {

    /**
     * Specifies the custom name for the annotated parameter.
     * <p>
     * **Important:** The provided name should not be empty, as it is used for logging, validation, or tracing purposes.
     *
     * @return the custom parameter name
     */
    String value();
}
