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
package hu.icellmobilsoft.quarkus.sampler.etcd.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;

import hu.icellmobilsoft.coffee.module.etcd.health.EtcdHealth;
import hu.icellmobilsoft.coffee.se.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

/**
 * Health check for etcd availability
 * 
 * @author czenczl
 * @since 0.1.0
 */
@ApplicationScoped
public class EtcdHealthCheck {

    public static final String ETCD_BUILDER_NAME = "etcd";

    @Inject
    Logger logger;

    @Inject
    EtcdHealth etcdHealth;

    /**
     * Check etcd availability
     * 
     * The created {@link HealthCheckResponse} contains information about whether the etcd server is reachable.
     */
    public HealthCheckResponse checkEtcd() {
        try {
            return etcdHealth.checkConnection(ETCD_BUILDER_NAME);
        } catch (Throwable e) {
            // we catch every exception and error so that the probe doesn't encounter any unhandled errors or exceptions
            logger.error("Error occured while checking etcd resource.", e);
            return HealthCheckResponse.builder().name(ETCD_BUILDER_NAME).up().build();
        }

    }

    @Produces
    @Startup
    public HealthCheck produceEtcdStartup() {
        return this::checkEtcd;
    }
    
}
