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
package hu.icellmobilsoft.quarkus.sampler.common.core.logging;

import java.text.MessageFormat;
import java.util.stream.Stream;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import hu.icellmobilsoft.coffee.cdi.logger.LogProducer;

/**
 * Interceptor of logging entry and exit around a method
 * 
 * @author speter555
 * @since 0.1.0
 */
@Interceptor
@LogMethodEntryAndExit
public class LogEntryAndExitInterceptor {

    /**
     * Logging enter and exit from context. Log contains class name, method, parameters names, parameters values
     * 
     * @param ctx
     *            invocation context
     * @return called methods return answer
     * @throws Exception
     *             any error
     */
    @AroundInvoke
    public Object loggingEntryAndExitLogging(final InvocationContext ctx) throws Exception {
        Class<?> originalClass = ctx.getMethod().getDeclaringClass();
        String methodName = ctx.getMethod().getName();
        String[] paramsName = Stream.of(ctx.getMethod().getParameters())
                .map(parameter -> MessageFormat.format("{0} [{1}]", parameter.getName(), parameter.getType().getName()))
                .toArray(String[]::new);
        Object[] paramsValue = ctx.getParameters();
        String methodInfo = getCalledMethodWithOnlyPathParams(originalClass, methodName, paramsName);
        logEnter(originalClass, methodInfo, paramsValue);
        try {
            return ctx.proceed();
        } finally {
            logReturn(originalClass, methodInfo, paramsValue);
        }
    }

    private String getCalledMethodWithOnlyPathParams(Class<?> originalClass, String methodName, String... paramNames) {
        return getCalledMethodWithParamsBase(originalClass, methodName, paramNames) + ")";
    }

    private String getCalledMethodWithParamsBase(Class<?> originalClass, String methodName, String... paramNames) {
        StringBuilder methodInfo = new StringBuilder(" ").append(originalClass).append(".").append(methodName).append("(");
        int index = 0;
        for (String paramName : paramNames) {
            if (index > 0) {
                methodInfo.append(", ");
            }
            methodInfo.append(paramName).append(": [{").append(index++).append("}]");
        }
        return methodInfo.toString();
    }

    private void logReturn(Class<?> originalClass, String methodInfo, Object... params) {
        LogProducer.logToAppLogger(logger -> logger.trace("<<" + methodInfo, params), originalClass);
    }

    private void logEnter(Class<?> originalClass, String methodInfo, Object... params) {
        LogProducer.logToAppLogger(logger -> logger.trace(">>" + methodInfo, params), originalClass);
    }

}
