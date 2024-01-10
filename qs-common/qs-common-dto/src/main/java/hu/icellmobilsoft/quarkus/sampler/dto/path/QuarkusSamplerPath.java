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
package hu.icellmobilsoft.quarkus.sampler.dto.path;

/**
 * Service REST paths
 *
 * @author speter555
 * @since 0.1.0
 */
public class QuarkusSamplerPath extends ServicePath {

    /**
     * Service
     */
    public static final String QUARKUS_SAMPLER_SERVICE = "/quarkusSamplerService";

    /**
     * Service test path prefix
     */
    public static final String TEST_QUARKUS_SAMPLER_SERVICE = TEST + QUARKUS_SAMPLER_SERVICE;

    /**
     * {@value} param id
     */
    public static final String PARAM_TEST_STRING = "testString";

    /**
     * {@value} param id
     */
    public static final String PARAM_TEST_INTEGER = "testInteger";

    /**
     * {@value} param id
     */
    public static final String PARAM_TEST_LONG = "testLong";

    /**
     * {@value} param id
     */
    public static final String PARAM_TEST_BOOLEAN = "testBoolean";

}
