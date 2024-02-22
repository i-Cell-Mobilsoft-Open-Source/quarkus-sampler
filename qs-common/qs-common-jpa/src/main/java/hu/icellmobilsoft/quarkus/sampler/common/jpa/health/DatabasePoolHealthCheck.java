/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 - 2024 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.quarkus.sampler.common.jpa.health;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import hu.icellmobilsoft.coffee.jpa.health.DatabasePoolHealth;
import hu.icellmobilsoft.coffee.se.logging.Logger;

/**
 * Health check for database pool availability
 * 
 * @author czenczl
 * @author Imre Scheffer
 * @since 2.5.0
 *
 */
@ApplicationScoped
public class DatabasePoolHealthCheck {

    /**
     * persistence.xml dialect
     */
    public static final String H2_DIALECT = "org.hibernate.dialect.H2Dialect";
    /**
     * key from persistence settings
     */
    public static final String HIBERNATE_DIALECT = "HIBERNATE_DIALECT";

    @Inject
    private Logger logger;

    @Inject
    private DatabasePoolHealth databasePoolHealth;

    @Inject
    private Config config;

    private String builderName;

    /**
     * Init the health builderName by using the given database dialect
     */
    @PostConstruct
    public void initHealthConfig() {
//        String dialect = config.getValue(HIBERNATE_DIALECT, String.class);
//        if (dialect.contains(H2_DIALECT)) {
            builderName = "h2";
//        } else {
//            builderName = "other";
//        }
    }

    /**
     * Checking database pool usage
     * 
     * @return The created {@link HealthCheckResponse} contains information about whether the database connection pool usage is below the desired
     *         threshold.
     */
    public HealthCheckResponse checkDatabase() {
        try {
            return databasePoolHealth.checkDatabasePoolUsage(builderName);
        } catch (Throwable e) {
            // we catch every exception and error so that the probe doesn't encounter any unhandled errors or exceptions
            logger.error("Error occured while checking database pool resource.", e);
            return HealthCheckResponse.builder().name(builderName).up().build();
        }
    }

    /**
     * Adding to health/ready endpoint
     * 
     * @return health values
     */
    @Produces
    @Readiness
    public HealthCheck produceDatabaseStartup() {
        return this::checkDatabase;
    }

}
