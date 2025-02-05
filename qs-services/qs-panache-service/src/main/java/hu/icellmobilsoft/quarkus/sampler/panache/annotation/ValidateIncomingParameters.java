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
 * Annotation for intercepting and validating incoming method parameters.
 * <p>
 * This annotation is used to enforce validation rules on method parameters when applied at the class or method level.
 * <p>
 * Validation includes:
 * <ul>
 * <li>Strings must not be blank.</li>
 * <li>Collections must not be empty.</li>
 * <li>Optionals must not be empty.</li>
 * <li>All other parameters must not be null.</li>
 * </ul>
 * <p>
 * Usage:
 * 
 * <pre>{@code
 * @ValidateIncomingParameters
 * public void process(@ParamName("name") String name, @ParamName("items") List<String> items) {
 *     // Business logic
 * }
 * }</pre>
 * <p>
 * This annotation is an {@link InterceptorBinding} and should be used alongside an interceptor that enforces the validation logic.
 *
 * @see jakarta.interceptor.InterceptorBinding
 * @author balazs.joo
 * @since 0.1.0
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER })
public @interface ValidateIncomingParameters {

    /**
     * Indicates whether parameter validation should be enabled. Defaults to {@code true}, but can be set to {@code false} to disable validation on
     * specific methods.
     *
     * @return {@code true} if validation is enabled, otherwise {@code false}
     */
    boolean validate() default true;
}
