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
package hu.icellmobilsoft.quarkus.sampler.rest.cdi;

import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import hu.icellmobilsoft.coffee.rest.cdi.BaseRequestContainer;
import hu.icellmobilsoft.quarkus.sampler.rest.header.ProjectHeader;

/**
 * Common request scope container
 *
 * @author speter555
 *
 */
@Model
public class RequestContainer {

    private ProjectHeader projectHeader;

    private ProjectHeader defaultProjectHeader = new ProjectHeader();

    @Inject
    private BaseRequestContainer baseRequestContainer;

    /**
     * ProjectHeader producer over Request scope
     * 
     * @return Project header instance
     */
    @Produces
    @RequestScoped
    public ProjectHeader getProjectHeader() {
        if (projectHeader == null) {
            return defaultProjectHeader;
        }
        return projectHeader;
    }

    /**
     * Set project header
     * 
     * @param projectHeader
     *            projectHeader object
     */
    public void setProjectHeader(ProjectHeader projectHeader) {
        this.projectHeader = projectHeader;
    }

    /**
     * Get object map
     * 
     * @return object map
     */
    public Map<String, Object> getObjectMap() {
        return baseRequestContainer.getObjectMap();
    }

    /**
     * Get requestObject to request container
     * 
     * @return request object
     */
    public Object getRequestObject() {
        return baseRequestContainer.getRequestObject();
    }

    /**
     * Set requestObject to request container
     * 
     * @param requestObject
     *            request object
     */
    public void setRequestObject(Object requestObject) {
        baseRequestContainer.setRequestObject(requestObject);
    }
}
