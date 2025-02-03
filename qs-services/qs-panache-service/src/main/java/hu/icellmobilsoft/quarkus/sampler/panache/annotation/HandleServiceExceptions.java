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
 * Annotation for handling exceptions at the service layer.
 * <p>
 * This annotation enables an interceptor that automatically catches specific service-level exceptions, transforming them into standardized fault
 * responses. It ensures a **consistent exception handling mechanism** for business logic.
 * <p>
 * Features:
 * <ul>
 * <li>Automatically catches known exceptions and maps them.</li>
 * <li>Reduces boilerplate error handling in service methods.</li>
 * </ul>
 *
 * <p>
 * Example usage:
 * 
 * <pre>{@code
 * @HandleServiceExceptions
 * public void performBusinessLogic() {
 *     // Business logic that may throw exceptions
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
public @interface HandleServiceExceptions {

}
