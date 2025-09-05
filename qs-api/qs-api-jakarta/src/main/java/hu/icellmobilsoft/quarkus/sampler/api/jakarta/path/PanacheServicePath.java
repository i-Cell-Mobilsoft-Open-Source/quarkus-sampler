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
package hu.icellmobilsoft.quarkus.sampler.api.jakarta.path;

/**
 * PATHS for panache-service
 * 
 * @author balazs.joo
 * @since 0.1.0
 */
public class PanacheServicePath extends ServicePath {

    /**
     * /rest/panacheService
     */
    public static final String REST_PANACHE_SERVICE = REST + "/panacheService";

    public static final String REST_PANACHE_SERVICE_MODIFY = REST_PANACHE_SERVICE + "/modify";
    public static final String REST_PANACHE_SERVICE_PROJECTION = REST_PANACHE_SERVICE + "/projection";
    public static final String REST_PANACHE_SERVICE_QUERY = REST_PANACHE_SERVICE + "/query";
    public static final String REST_PANACHE_SERVICE_TRANSACTION = REST_PANACHE_SERVICE + "/transaction";

    private PanacheServicePath() {
        super();
    }
}
