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

import java.lang.reflect.Parameter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.NoResultException;
import jakarta.persistence.OptimisticLockException;

import hu.icellmobilsoft.coffee.dto.exception.BONotFoundException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.dto.exception.BaseRuntimeException;
import hu.icellmobilsoft.quarkus.sampler.panache.annotation.HandleServiceExceptions;
import hu.icellmobilsoft.quarkus.sampler.panache.annotation.ParamName;

/**
 * Interceptor for handling service exceptions in a consistent manner.
 * <p>
 * This interceptor processes exceptions thrown during the execution of methods annotated with {@link HandleServiceExceptions}. It captures specific
 * exceptions, such as {@link NoResultException} and {@link OptimisticLockException}, and wraps them into application-specific exceptions.
 * <p>
 * Features:
 * <ul>
 * <li>Transforms {@link NoResultException} into a {@link BONotFoundException} with detailed method and parameter information.</li>
 * <li>Transforms {@link OptimisticLockException} into a {@link hu.icellmobilsoft.coffee.dto.exception.OptimisticLockException} with additional
 * context.</li>
 * <li>Handles all other exceptions by wrapping them into a {@link TechnicalException}.</li>
 * </ul>
 * <p>
 * This interceptor executes with a priority of 120, meaning it is applied after lower-priority interceptors but before higher-priority ones.
 * <p>
 * Example usage:
 *
 * <pre>{@code
 * @HandleServiceExceptions
 * public class ExampleService {
 *     // Business logic that may throw exceptions
 * }
 * }</pre>
 *
 * @see HandleServiceExceptions
 * @see BONotFoundException
 * @see hu.icellmobilsoft.coffee.dto.exception.OptimisticLockException
 * @see TechnicalException
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@Interceptor
@HandleServiceExceptions
@Priority(120)
public class HandleServiceExceptionsInterceptor {

    /**
     * Default constructor.
     */
    public HandleServiceExceptionsInterceptor() {
        // Default constructor for Java 21
    }

    /**
     * Intercepts method invocations and handles specific exceptions.
     *
     * @param ctx
     *            the invocation context, providing access to the method, parameters, and other metadata.
     * @return the result of the method invocation.
     */
    @AroundInvoke
    public Object handleExceptions(final InvocationContext ctx) {
        String methodInfo = generateMethodInfo(ctx);
        Object[] paramsValue = Optional.ofNullable(ctx.getParameters()).orElse(new Object[0]);

        try {
            return ctx.proceed();
        } catch (NoResultException e) {
            throw wrap(notFound(methodInfo, paramsValue));
        } catch (OptimisticLockException e) {
            throw wrap(handleOptimisticLockException(methodInfo, e));
        } catch (Exception e) {
            throw wrap(repositoryFailed(e, methodInfo, paramsValue));
        }
    }

    /**
     * Generates a detailed method signature including its parameters.
     *
     * @param ctx
     *            the invocation context.
     * @return formatted method signature.
     */
    private String generateMethodInfo(InvocationContext ctx) {
        return ctx.getMethod().getDeclaringClass().getSimpleName() + "." + ctx.getMethod().getName() + "("
                + Arrays.stream(ctx.getMethod().getParameters())
                        .map(param -> getName(param) + " [" + param.getType().getSimpleName() + "]")
                        .collect(Collectors.joining(", "))
                + ")";
    }

    /**
     * Constructs a {@link BONotFoundException} for missing entity scenarios.
     *
     * @param methodInfo
     *            the method where the exception occurred.
     * @param params
     *            parameters involved in the method call.
     * @return a properly formatted exception.
     */
    private BONotFoundException notFound(String methodInfo, Object... params) {
        return new BONotFoundException(
                CoffeeFaultType.ENTITY_NOT_FOUND,
                MessageFormat.format("Entry for {0} not found! {1}", methodInfo, prepareParametersToLog(params)));
    }

    /**
     * Constructs an {@link OptimisticLockException} with additional context.
     *
     * @param methodInfo
     *            the method where the exception occurred.
     * @param e
     *            the original exception.
     * @return an enriched optimistic lock exception.
     */
    private hu.icellmobilsoft.coffee.dto.exception.OptimisticLockException handleOptimisticLockException(String methodInfo,
            OptimisticLockException e) {
        return new hu.icellmobilsoft.coffee.dto.exception.OptimisticLockException(
                CoffeeFaultType.OPTIMISTIC_LOCK_EXCEPTION,
                MessageFormat.format("Optimistic Lock Error in {0}: {1}", methodInfo, e.getLocalizedMessage()),
                e);
    }

    /**
     * Constructs a {@link TechnicalException} for generic repository failures.
     *
     * @param e
     *            the original exception.
     * @param methodInfo
     *            the method where the exception occurred.
     * @param params
     *            parameters involved in the method call.
     * @return a formatted technical exception.
     */
    private TechnicalException repositoryFailed(Exception e, String methodInfo, Object... params) {
        return new TechnicalException(
                CoffeeFaultType.REPOSITORY_FAILED,
                MessageFormat.format("Error in {0}: {1}", methodInfo, prepareParametersToLog(params)),
                e);
    }

    /**
     * Prepares method parameters for logging and error reporting.
     *
     * @param params
     *            input parameters.
     * @return formatted parameters.
     */
    private Object[] prepareParametersToLog(Object... params) {
        if (Objects.isNull(params)) {
            return new Object[0];
        }

        return Arrays.stream(params).map(param -> {
            if (param instanceof Collection<?> collection) {
                return "Collection[" + collection.stream().map(String::valueOf).collect(Collectors.joining(", ")) + "]";
            }
            if (param instanceof Map<?, ?> map) {
                return "Map["
                        + map.entrySet().stream().map(entry -> "[" + entry.getKey() + ", " + entry.getValue() + "]").collect(Collectors.joining(", "))
                        + "]";
            }
            if (param instanceof Object[] array) {
                return "Array" + Arrays.toString(array);
            }
            return param;
        }).toArray();
    }

    /**
     * Retrieves parameter name from {@link ParamName} annotation if present, otherwise returns reflection name.
     *
     * @param parameter
     *            the method parameter.
     * @return the parameter name.
     */
    private String getName(final Parameter parameter) {
        ParamName annotation = parameter.getAnnotation(ParamName.class);
        return (Objects.nonNull(annotation)) ? annotation.value() : parameter.getName();
    }

    private RuntimeException wrap(BaseException baseException) {
        return new BaseRuntimeException(baseException);
    }
}
