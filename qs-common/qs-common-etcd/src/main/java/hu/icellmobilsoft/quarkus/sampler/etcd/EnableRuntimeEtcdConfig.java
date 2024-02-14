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
package hu.icellmobilsoft.quarkus.sampler.etcd;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import org.jboss.logging.Logger;

import hu.icellmobilsoft.coffee.module.etcd.producer.RuntimeEtcdConfigSource;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

/**
 * Enable Runtime Etcd config
 * 
 * @since 0.1.0
 * @author speter555
 */
@ApplicationScoped
public class EnableRuntimeEtcdConfig {

    private final Logger LOGGER = Logger.getLogger(EnableRuntimeEtcdConfig.class);

    /**
     * Startup event where etcd config is enabled
     * 
     * @param ev
     *            startup event
     */
    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
        RuntimeEtcdConfigSource.setActive(false);
    }

    /**
     * Shutdown event where etcd config is disabled
     * 
     * @param ev
     *            shutdown event
     */
    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
        RuntimeEtcdConfigSource.setActive(false);
    }
}
