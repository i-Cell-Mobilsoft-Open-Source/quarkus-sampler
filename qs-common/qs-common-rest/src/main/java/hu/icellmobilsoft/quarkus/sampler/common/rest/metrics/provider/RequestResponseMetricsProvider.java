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
package hu.icellmobilsoft.quarkus.sampler.common.rest.metrics.provider;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.ConstrainedTo;
import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.Tag;

import hu.icellmobilsoft.quarkus.sample.metrics.MetricsHelper;
import hu.icellmobilsoft.quarkus.sample.metrics.constants.MetricsConstants;
import hu.icellmobilsoft.quarkus.sampler.common.rest.header.ProjectHeader;
import hu.icellmobilsoft.quarkus.sampler.common.rest.metrics.MetricsContainer;
import hu.icellmobilsoft.quarkus.sampler.common.rest.metrics.util.HttpMethodUtil;

/**
 * JAXRS provider what is handling metrics around the request/response
 *
 * @author speter555
 * @since 0.1.0
 */
@Provider
@Dependent
@ConstrainedTo(RuntimeType.SERVER)
public class RequestResponseMetricsProvider implements ContainerRequestFilter, WriterInterceptor {

    @Inject
    MetricsContainer metricsContainer;

    @Inject
    MetricsHelper metricsHelper;

    @Inject
    ProjectHeader projectHeader;

    @Context
    private HttpServletResponse httpServletResponse;

    @Context
    private UriInfo uriInfo;

    /**
     * Default constructor
     */
    public RequestResponseMetricsProvider() {
        // Default constructor for java 21
    }

    /**
     * Create metrics start of request or data preparing
     *
     * @param requestContext
     *            request context object
     */
    @Override
    public void filter(ContainerRequestContext requestContext) {
        String httpMethod = requestContext.getMethod();
        metricsContainer.setHttpMethod(httpMethod);
        if (isMeteredOperation(httpMethod)) {
            metricsContainer.setStartTime(LocalDateTime.now());
        }
    }

    /**
     * Create metrics after create Response
     *
     * @param context
     *            WriterInterceptorContext object
     * @throws IOException
     *             when context proceed has error
     * @throws WebApplicationException
     *             when context proceed has error
     */
    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        String httpMethod = metricsContainer.getHttpMethod();
        if (isMeteredOperation(httpMethod)) {
            Tag sourceTag = new Tag(
                    MetricsConstants.Tag.SOURCE,
                    StringUtils.isBlank(projectHeader.getSource()) ? MetricsConstants.TagValue.SYSTEM : projectHeader.getSource());
            Tag responseCodeTag = new Tag(MetricsConstants.Tag.RESPONSE_CODE, String.valueOf(httpServletResponse.getStatus()));
            String url = uriInfo.getAbsolutePath().toASCIIString();
            // Remove path params from url
            if (uriInfo.getPathParameters() != null) {
                for (Map.Entry<String, List<String>> param : uriInfo.getPathParameters().entrySet()) {
                    for (String p : param.getValue()) {
                        url = url.replace(p, param.getKey());
                    }
                }
            }

            Tag urlTag = new Tag(MetricsConstants.Tag.URL, url);
            updateResponseTimer(Arrays.asList(sourceTag, responseCodeTag, urlTag));
            updateResponseCounter(Arrays.asList(sourceTag, responseCodeTag, urlTag));
        }
        context.proceed();
    }

    /**
     * Metrics update's response time
     *
     * @param tags
     *            tags list
     */
    private void updateResponseTimer(List<Tag> tags) {
        if (metricsContainer.getStartTime() != null) {
            metricsHelper.addTimerMetric(
                    MetricsConstants.Timer.HTTP_RESPONSE_TIME,
                    MetricsConstants.Description.HTTP_RESPONSE_TIME_DESCRIPTION,
                    Duration.between(metricsContainer.getStartTime(), LocalDateTime.now()),
                    MetricUnits.MILLISECONDS,
                    tags.toArray(Tag[]::new));

        }
    }

    /**
     * Metrics update's response counter
     *
     * @param tags
     *            tags list
     */
    private void updateResponseCounter(List<Tag> tags) {
        metricsHelper.addCounterIncOneMetric(
                MetricsConstants.Counter.HTTP_RESPONSE_COUNTER,
                MetricsConstants.Description.HTTP_RESPONSE_COUNTER_DESCRIPTION,
                tags.toArray(Tag[]::new));
    }

    /**
     * Checks if the request http method is allowed to create metrics
     *
     * @param httpMethod
     *            http method
     * @return true if allowed, otherwise false
     */
    private boolean isMeteredOperation(String httpMethod) {
        return httpMethod != null && HttpMethodUtil.ALLOWED_HTTP_METHODS.contains(httpMethod);
    }
}
