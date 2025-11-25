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
package hu.icellmobilsoft.quarkus.sampler.common.core.parameter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.validation.ParamValidatorUtil;
import hu.icellmobilsoft.qs.common.se.exception.BaseRuntimeException;

/**
 * An interceptor to validate incoming method parameters before the method execution. This interceptor ensures that parameters annotated with
 * {@link ValidateIncomingParameters} are validated based on predefined rules.
 *
 * <p>
 * Validation includes:
 * <ul>
 * <li>Strings must not be blank.</li>
 * <li>Collections must not be empty.</li>
 * <li>Optionals must not be empty.</li>
 * <li>All other parameters must not be null.</li>
 * </ul>
 * If validation fails, a {@link BaseRuntimeException} is thrown.
 *
 * <p>
 * This interceptor is executed with a priority of 110, meaning it runs after lower-priority interceptors but before higher-priority ones.
 *
 * <p>
 * Example usage:
 *
 * <pre>{@code
 * @ValidateIncomingParameters()
 * public void process(@ParamName("name") String name, @ParamName("items") List<String> items) {
 *     // Business logic
 * }
 * }</pre>
 *
 * @see ValidateIncomingParameters
 * @see ParamValidatorUtil
 * @see ParamName
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@Interceptor
@ValidateIncomingParameters
@Priority(110)
public class ValidateIncomingParametersInterceptor {

    /**
     * Default constructor.
     */
    public ValidateIncomingParametersInterceptor() {
        // Default constructor for Java 21
    }

    /**
     * Intercepts method invocations and validates the method parameters if the {@link ValidateIncomingParameters} annotation is present on the method
     *
     * @param ctx
     *            the invocation context, providing access to the method, parameters, and other metadata.
     * @return the result of the method invocation.
     * @throws BaseRuntimeException
     *             if validation fails.
     * @throws Exception
     *             if an error occurs during method execution.
     */
    @AroundInvoke
    public Object handleParameters(final InvocationContext ctx) throws Exception {

        Method method = ctx.getMethod();
        Class<?> originalClass = ctx.getTarget().getClass();

        if (!isValidationEnabled(originalClass, method)) {
            return ctx.proceed();
        }

        try {
            var parameters = method.getParameters();
            var values = ctx.getParameters();

            for (int i = 0; i < values.length; i++) {
                Parameter parameter = parameters[i];
                var name = getName(parameter);
                var allowEmptyCollection = isEmptyCollectionAllowed(parameter);
                var value = values[i];

                validateParameter(name, value, allowEmptyCollection);
            }
        } catch (BaseException e) {
            throw new BaseRuntimeException(e);
        }
        return ctx.proceed();
    }

    private boolean isValidationEnabled(Class<?> originalClass, Method method) {

        int modifiers = method.getModifiers();
        if (Modifier.isPublic(modifiers)) {
            return true;
        }

        return isValidateNonPublicMethods(originalClass, method);
    }

    private boolean isValidateNonPublicMethods(Class<?> originalClass, Method method) {

        ValidateIncomingParameters validateIncomingParameters = method.getAnnotation(ValidateIncomingParameters.class);
        if (Objects.nonNull(validateIncomingParameters)) {
            return validateIncomingParameters.validateNonPublicMethods();
        }

        validateIncomingParameters = originalClass.getAnnotation(ValidateIncomingParameters.class);
        if (Objects.nonNull(validateIncomingParameters)) {
            return validateIncomingParameters.validateNonPublicMethods();
        }

        return false;
    }

    /**
     * Validates the provided parameter based on its type.
     *
     * @param name
     *            The parameter name.
     * @param value
     *            The parameter value.
     * @param allowEmptyCollection
     **            Whether an empty collection is allowed; if true, empty collections are permitted (only null is disallowed), otherwise collections
     *            must be non\-empty.
     * @throws BaseRuntimeException
     *             if validation fails.
     */
    private void validateParameter(String name, Object value, boolean allowEmptyCollection) throws BaseException {
        switch (value) {
            case String sv -> ParamValidatorUtil.requireNonBlank(sv, name);
            case Collection<?> cv -> validateCollectionParameter(name, cv, allowEmptyCollection);
            case Optional<?> ov -> ParamValidatorUtil.requireNonEmpty(ov, name);
            case null, default -> ParamValidatorUtil.requireNonNull(value, name);
        }
    }

    private void validateCollectionParameter(String name, Collection<?> cv, boolean allowEmptyCollection) throws BaseException {
        if (allowEmptyCollection) {
            ParamValidatorUtil.requireNonNull(cv, name);
        } else {
            ParamValidatorUtil.requireNonEmpty(cv, name);
        }
    }

    /**
     * Retrieves the name of the given parameter, either from the {@link ParamName} annotation or from the reflection metadata.
     *
     * @param parameter
     *            the parameter to get the name of.
     * @return the extracted parameter name.
     */
    private String getName(final Parameter parameter) {
        ParamName annotation = parameter.getAnnotation(ParamName.class);
        return (Objects.nonNull(annotation)) ? annotation.value() : parameter.getName();
    }

    private boolean isEmptyCollectionAllowed(Parameter parameter) {
        AllowEmptyCollection allowEmptyCollection = parameter.getAnnotation(AllowEmptyCollection.class);
        return Objects.nonNull(allowEmptyCollection);
    }

}
