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
package hu.icellmobilsoft.quarkus.sampler.panache.shutdown;

import jakarta.enterprise.event.Observes;

import io.quarkus.runtime.ShutdownEvent;

/**
 * Support for shutdown event
 * 
 * @author czenczl
 * @since 0.1.0
 */
public class ShutDownSupport {

    public static boolean shutDown = false;

    /**
     * collect shutdown event information
     * 
     * @param ev
     *            shutdown event
     */
    public void init(@Observes ShutdownEvent ev) {
        shutDown = true;
    }

}
