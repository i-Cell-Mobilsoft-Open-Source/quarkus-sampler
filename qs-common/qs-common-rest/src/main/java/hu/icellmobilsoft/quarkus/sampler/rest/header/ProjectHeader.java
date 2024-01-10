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
package hu.icellmobilsoft.quarkus.sampler.rest.header;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.se.logging.Logger;

/**
 * Project header class
 *
 * @author speter555
 *
 */
public class ProjectHeader implements IHttpHeaderConstant {

    private static final String HOST_PORT_SEPARATOR = ":";
    private static final String EMPTY_VALUE = "empty";
    private static final String FORWARDED_FOR_TAG = "for=";
    private static final int HOST_PORT_MAX_LENGTH = 15;

    private String sessionToken;
    private String refreshToken;
    private String username;
    private String host;
    private String source;
    private String language;
    private String forwarded;
    private String forwardedForHost;
    private String forwardedForPort;


    /**
     * Default constructor
     */
    public ProjectHeader() {
        // Default constructor for java 21
    }

    /**
     * Create ProjectHeader instance from {@link ContainerRequestContext}
     *
     * @param requestContext
     *            {@link ContainerRequestContext} instance
     * @return ProjectHeader instance
     */
    public static ProjectHeader readHeaders(ContainerRequestContext requestContext) {
        ProjectHeader projectHeader = new ProjectHeader();
        if (requestContext != null) {
            projectHeader = readHeaders(requestContext.getHeaders());
            fillProjectHeaderFromCookie(projectHeader, requestContext.getCookies());
            fillProjectHeaderFromQueryParameter(projectHeader, requestContext.getUriInfo().getQueryParameters());
        }
        return projectHeader;
    }

    /**
     * Create ProjectHeader instance from MultivaluedMap
     *
     * @param headerMap
     *            input
     * @return ProjectHeader instance
     */
    private static ProjectHeader readHeaders(MultivaluedMap<String, String> headerMap) {
        ProjectHeader projectHeader = new ProjectHeader();
        if (headerMap != null) {

            String host = getHeaderValue(headerMap, HEADER_HOST, false);
            if (StringUtils.isBlank(host)) {
                host = getHeaderValue(headerMap, HEADER_XHOST, false);
            }
            projectHeader.setHost(host);

            projectHeader.setLanguage(getHeaderValue(headerMap, HEADER_LANGUAGE, false));
            projectHeader.setSessionToken(getHeaderValue(headerMap, HEADER_SESSION_TOKEN, false));
            projectHeader.setRefreshToken(getHeaderValue(headerMap, HEADER_REFRESH_TOKEN, false));
            projectHeader.setUsername(getHeaderValue(headerMap, HEADER_USERNAME, false));
            projectHeader.setSource(getHeaderValue(headerMap, HEADER_SOURCE, false));
            projectHeader.setForwarded(getHeaderValue(headerMap, HEADER_FORWARDED, false));
        }
        return projectHeader;
    }

    private static void fillProjectHeaderFromCookie(ProjectHeader projectHeader, Map<String, Cookie> cookieMap) {
        if (projectHeader != null && cookieMap != null) {

            Cookie host = cookieMap.get(HEADER_HOST);
            if (host == null || StringUtils.isBlank(host.getValue())) {
                host = cookieMap.get(HEADER_XHOST);
            }
            if (StringUtils.isBlank(projectHeader.getHost())) {
                projectHeader.setHost(host.getValue());
            }

            if (StringUtils.isBlank(projectHeader.getLanguage())) {
                projectHeader.setLanguage(getCookieValue(cookieMap, HEADER_LANGUAGE, false));
            }

            if (StringUtils.isBlank(projectHeader.getSessionToken())) {
                projectHeader.setSessionToken(getCookieValue(cookieMap, HEADER_SESSION_TOKEN, false));
            }

            if (StringUtils.isBlank(projectHeader.getRefreshToken())) {
                projectHeader.setRefreshToken(getCookieValue(cookieMap, HEADER_REFRESH_TOKEN, false));
            }

            if (StringUtils.isBlank(projectHeader.getUsername())) {
                projectHeader.setUsername(getCookieValue(cookieMap, HEADER_USERNAME, false));
            }

            if (StringUtils.isBlank(projectHeader.getSource())) {
                projectHeader.setSource(getCookieValue(cookieMap, HEADER_SOURCE, false));
            }

            if (StringUtils.isBlank(projectHeader.getForwarded())) {
                projectHeader.setForwarded(getCookieValue(cookieMap, HEADER_FORWARDED, false));
            }

        }
    }

    private static void fillProjectHeaderFromQueryParameter(ProjectHeader projectHeader, MultivaluedMap<String, String> queryParameterMap) {
        if (projectHeader != null && queryParameterMap != null) {

            String host = queryParameterMap.getFirst(HEADER_HOST);
            if (StringUtils.isBlank(host)) {
                host = queryParameterMap.getFirst(HEADER_XHOST);
            }
            if (StringUtils.isBlank(projectHeader.getHost())) {
                projectHeader.setHost(host);
            }

            if (StringUtils.isBlank(projectHeader.getLanguage())) {
                projectHeader.setLanguage(getFirstQueryParameterValue(queryParameterMap, HEADER_LANGUAGE, false));
            }

            if (StringUtils.isBlank(projectHeader.getSessionToken())) {
                projectHeader.setSessionToken(getFirstQueryParameterValue(queryParameterMap, HEADER_SESSION_TOKEN, false));
            }

            if (StringUtils.isBlank(projectHeader.getRefreshToken())) {
                projectHeader.setRefreshToken(getFirstQueryParameterValue(queryParameterMap, HEADER_REFRESH_TOKEN, false));
            }

            if (StringUtils.isBlank(projectHeader.getUsername())) {
                projectHeader.setUsername(getFirstQueryParameterValue(queryParameterMap, HEADER_USERNAME, false));
            }

            if (StringUtils.isBlank(projectHeader.getSource())) {
                projectHeader.setSource(getFirstQueryParameterValue(queryParameterMap, HEADER_SOURCE, false));
            }

            if (StringUtils.isBlank(projectHeader.getForwarded())) {
                projectHeader.setForwarded(getFirstQueryParameterValue(queryParameterMap, HEADER_FORWARDED, false));
            }
        }
    }

    private void handleForwardedHeader(String headerValue) {
        String forwardedHost;
        String port = null;
        String hostPort = headerValue;
        if (StringUtils.isNotBlank(headerValue) && StringUtils.contains(headerValue, FORWARDED_FOR_TAG)) {
            Optional<String> o = Arrays.stream(StringUtils.split(headerValue, ";"))
                    .filter(s -> StringUtils.startsWithIgnoreCase(StringUtils.trim(s), FORWARDED_FOR_TAG))
                    .findFirst();
            if (o.isPresent()) {
                hostPort = StringUtils.substringAfter(o.get(), FORWARDED_FOR_TAG);
            }
        }
        if (StringUtils.contains(hostPort, HOST_PORT_SEPARATOR)) {
            forwardedHost = StringUtils.trim(StringUtils.substringBefore(hostPort, HOST_PORT_SEPARATOR));
            port = StringUtils.trim(StringUtils.substringAfter(hostPort, HOST_PORT_SEPARATOR));
        } else {
            forwardedHost = StringUtils.trim(hostPort);
        }
        setForwardedForHost(StringUtils.defaultString(StringUtils.left(forwardedHost, HOST_PORT_MAX_LENGTH), EMPTY_VALUE));
        setForwardedForPort(StringUtils.defaultString(StringUtils.left(port, HOST_PORT_MAX_LENGTH), EMPTY_VALUE));
    }

    /**
     * Get value for key in MultivaluedMap
     *
     * @param headerMap
     *            input
     * @param key
     *            which key
     * @param required
     *            when key is not in headers and it is true, then create warn log message, else create debug message
     * @return key value in input
     */
    private static String getHeaderValue(MultivaluedMap<String, String> headerMap, String key, boolean required) {
        Logger log = Logger.getLogger(ProjectHeader.class);
        try {
            if (headerMap == null) {
                return null;
            }

            List<String> values = headerMap.get(key);
            if (values == null || values.isEmpty()) {
                String msg = "Request header doesnt contain (" + key + ") key";
                if (required) {
                    log.warn(msg);
                } else {
                    log.debug(msg);
                }
                return null;
            } else {
                return values.get(0);
            }
        } catch (Exception e) {
            log.warn("Error in getHeaderValue(" + key + ")", e);
            return null;
        }
    }

    private static String getCookieValue(Map<String, Cookie> cookieMap, String key, boolean required) {
        Logger log = Logger.getLogger(ProjectHeader.class);
        try {
            if (cookieMap == null) {
                return null;
            }

            Cookie cookie = cookieMap.get(key);
            if (cookie == null || StringUtils.isBlank(cookie.getValue())) {
                String msg = "Request query parameter doesnt contain (" + key + ") key";
                if (required) {
                    log.warn(msg);
                } else {
                    log.debug(msg);
                }
                return null;
            } else {
                return cookie.getValue();
            }
        } catch (Exception e) {
            log.warn("Error in getFirstQueryParameterValue(" + key + ")", e);
            return null;
        }
    }

    private static String getFirstQueryParameterValue(MultivaluedMap<String, String> queryParameter, String key, boolean required) {
        Logger log = Logger.getLogger(ProjectHeader.class);
        try {
            if (queryParameter == null) {
                return null;
            }

            List<String> values = queryParameter.get(key);
            if (values == null || values.isEmpty()) {
                String msg = "Request query parameter doesnt contain (" + key + ") key";
                if (required) {
                    log.warn(msg);
                } else {
                    log.debug(msg);
                }
                return null;
            } else {
                return values.get(0);
            }
        } catch (Exception e) {
            log.warn("Error in getFirstQueryParameterValue(" + key + ")", e);
            return null;
        }
    }

    /**
     * Getter of sessionToken
     * 
     * @return sessionToken
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * Setter of sessionToken
     *
     * @param sessionToken
     *            sessionToken
     */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    /**
     * Getter of refreshToken
     *
     * @return refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Setter of refreshToken
     *
     * @param refreshToken
     *            refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Getter of username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of username
     *
     * @param username
     *            username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of host
     *
     * @return host
     */
    public String getHost() {
        return host;
    }

    /**
     * Setter of host
     *
     * @param host
     *            host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Getter of source
     *
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * Setter of source
     *
     * @param source
     *            source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Getter of language
     *
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setter of language
     *
     * @param language
     *            language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Getter of forwarded
     *
     * @return forwarded
     */
    public String getForwarded() {
        return forwarded;
    }

    /**
     * Setter of forwarded
     *
     * @param forwarded
     *            forwarded
     */
    public void setForwarded(String forwarded) {
        this.forwarded = forwarded;
    }

    /**
     * Getter of forwardedForHost
     *
     * @return forwardedForHost
     */
    public String getForwardedForHost() {
        if (forwardedForHost == null && forwarded != null) {
            handleForwardedHeader(forwarded);
        }
        return forwardedForHost;
    }

    /**
     * Setter of forwardedForHost
     *
     * @param forwardedForHost
     *            forwardedForHost
     */
    public void setForwardedForHost(String forwardedForHost) {
        this.forwardedForHost = forwardedForHost;
    }

    /**
     * Getter of forwardedForPort
     *
     * @return forwardedForHost
     */
    public String getForwardedForPort() {
        if (forwardedForPort == null && forwarded != null) {
            handleForwardedHeader(forwarded);
        }
        return forwardedForPort;
    }

    /**
     * Setter of forwardedForPort
     *
     * @param forwardedForPort
     *            forwardedForPort
     */
    public void setForwardedForPort(String forwardedForPort) {
        this.forwardedForPort = forwardedForPort;
    }

}
