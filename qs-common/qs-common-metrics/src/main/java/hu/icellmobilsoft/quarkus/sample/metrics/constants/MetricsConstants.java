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
package hu.icellmobilsoft.quarkus.sample.metrics.constants;

/**
 * All microprofile-metrics constants
 *
 * @author speter555
 * @since 0.1.0
 */
public interface MetricsConstants {

    /**
     * microprofile-metrics with @Counted annotation or metrics counter constants only used in code
     */
    interface Counter {
        /**
         * application_http_response_counter <br>
         * The number of incoming HTTP requests<br>
         * {@value Description#HTTP_RESPONSE_COUNTER_DESCRIPTION}
         */
        String HTTP_RESPONSE_COUNTER = "http_response_counter";

    }

    /**
     * microprofile-metrics with @Histogram annotation or metrics counter constants only used in code
     */
    interface Histogram {

    }

    /**
     * microprofile-metrics with @Timer annotation or metrics counter constants only used in code
     */
    interface Timer {
        /**
         * application_http_response_time_ms <br>
         * The number of incoming HTTP requests and their response times.<br>
         * {@value Description#HTTP_RESPONSE_TIME_DESCRIPTION}
         */
        String HTTP_RESPONSE_TIME = "http_response_time";
    }

    /**
     * Tags over microprofile-metrics
     */
    interface Tag {
        /**
         * Response code tag
         */
        String RESPONSE_CODE = "response_code";
        /**
         * Url tag
         */
        String URL = "url";

        /**
         * Source tag
         */
        String SOURCE = "source";

    }

    /**
     * microprofile-metrics kezelesnel felhasznalt tag-ek (default) értékeire konstansok
     *
     * @author speter555
     *
     */
    interface TagValue {

        /**
         * System
         */
        String SYSTEM = "SYSTEM";
    }

    /**
     * Metadata name constants
     */
    interface Name {

    }

    /**
     * Description constants
     */
    interface Description {

        /**
         * Number of incoming HTTP requests.
         */
        String HTTP_RESPONSE_COUNTER_DESCRIPTION = "Number of incoming HTTP requests.";

        /**
         * Number and response time (seconds) of incoming HTTP requests.
         */
        String HTTP_RESPONSE_TIME_DESCRIPTION = "Number and response time (seconds) of incoming HTTP requests.";

    }
}
