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
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import org.apache.commons.lang3.BooleanUtils;

import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.tool.utils.validation.ParamValidatorUtil;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.dto.exception.BaseRuntimeException;
import hu.icellmobilsoft.quarkus.sampler.panache.annotation.ParamName;
import hu.icellmobilsoft.quarkus.sampler.panache.annotation.ValidateIncomingParameters;

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
        var annotation = ctx.getMethod().getAnnotation(ValidateIncomingParameters.class);
        if (Objects.isNull(annotation) || BooleanUtils.isFalse(annotation.validate())) {
            return ctx.proceed();
        }

        try {
            var parameters = ctx.getMethod().getParameters();
            var values = ctx.getParameters();

            for (int i = 0; i < values.length; i++) {
                var name = getName(parameters[i]);
                var value = values[i];

                validateParameter(name, value);
            }
            return ctx.proceed();
        } catch (BaseException e) {
            throw new BaseRuntimeException(e);
        }
    }

    /**
     * Validates the provided parameter based on its type.
     *
     * @param name
     *            The parameter name.
     * @param value
     *            The parameter value.
     * @throws BaseException
     *             if validation fails.
     */
    private void validateParameter(String name, Object value) throws BaseException {
        switch (value) {
            case String sv -> ParamValidatorUtil.requireNonBlank(sv, name);
            case Collection<?> cv -> ParamValidatorUtil.requireNonEmpty(cv, name);
            case Optional<?> ov -> ParamValidatorUtil.requireNonEmpty(ov, name);
            case null, default -> ParamValidatorUtil.requireNonNull(value, name);
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
}
