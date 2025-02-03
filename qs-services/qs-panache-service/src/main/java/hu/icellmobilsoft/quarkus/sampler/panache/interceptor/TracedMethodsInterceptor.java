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
package hu.icellmobilsoft.quarkus.sampler.panache.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import hu.icellmobilsoft.quarkus.sampler.panache.annotation.TracedMethods;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;

/**
 * Interceptor for tracing method executions using OpenTelemetry. This interceptor applies to methods annotated with {@link TracedMethods} and records
 * execution details.
 *
 * <p>
 * Tracing includes:
 * <ul>
 * <li>Method execution time</li>
 * <li>Method parameters (name-value pairs)</li>
 * <li>Exception recording (if any error occurs)</li>
 * </ul>
 *
 * <p>
 * This interceptor executes with a priority of 130, meaning it runs after lower-priority interceptors but before higher-priority ones.
 *
 * <p>
 * Example usage:
 * 
 * <pre>{@code
 * @TracedMethods
 * public void process(String name, List<String> items) {
 *     // Business logic
 * }
 * }</pre>
 *
 * @see TracedMethods
 * @author balazs.joo
 * @since 0.1.0
 */
@Interceptor
@TracedMethods
@Priority(130)
public class TracedMethodsInterceptor {

    @Inject
    Tracer tracer;

    /**
     * Intercepts method invocations and traces their execution.
     *
     * @param ctx
     *            The invocation context, providing access to method metadata and arguments.
     * @return The result of the method execution.
     * @throws Exception
     *             If an error occurs during method execution.
     */
    @AroundInvoke
    public Object handleParameters(final InvocationContext ctx) throws Exception {
        String methodName = ctx.getMethod().getDeclaringClass().getSimpleName() + "." + ctx.getMethod().getName();

        // Retrieve or create a span
        Span span = tracer.spanBuilder(methodName).startSpan();

        // Attach span to the current scope
        try (Scope scope = span.makeCurrent()) {
            try {
                return ctx.proceed();
            } catch (Exception e) {
                span.recordException(e);
                span.setStatus(StatusCode.ERROR);
                throw e;
            } finally {
                span.end();
            }
        }
    }
}
