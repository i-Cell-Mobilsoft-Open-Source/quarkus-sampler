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
package hu.icellmobilsoft.quarkus.sampler.common.jpa.interceptor;

import java.sql.Connection;
import java.sql.SQLException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.se.logging.Logger;
import hu.icellmobilsoft.quarkus.sampler.common.jpa.shutdown.ShutDownSupport;
import io.agroal.api.AgroalPoolInterceptor;
import io.quarkus.agroal.DataSource;

/**
 * Implement a project-level interceptor to prevent commits upon connection closure in the event of a shutdown.
 * 
 * @author czenczl
 * @since 0.1.0
 */
@ApplicationScoped
@DataSource("icellmobilsoftDS")
public class ProjectAgroalPoolInterceptor implements AgroalPoolInterceptor {

    @Inject
    Logger logger;

    /**
     * temporarily workaround for autocommit on connection close
     */
    @Override
    public void onConnectionDestroy(Connection connection) {
        if (!ShutDownSupport.shutDown) {
            return;
        }
        try {
            // prevent unexpected commit on shutdown
            connection.rollback();
        } catch (SQLException e) {
            logger.error("Failed to rollback under shutdown", e);
            // in that case, the data source connection will not close properly, and an unwanted commit will not occur
            throw new RuntimeException(e);
        }
    }

}
