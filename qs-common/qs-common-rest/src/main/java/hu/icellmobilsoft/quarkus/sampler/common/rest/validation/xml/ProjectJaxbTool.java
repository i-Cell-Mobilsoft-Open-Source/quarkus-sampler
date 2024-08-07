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
package hu.icellmobilsoft.quarkus.sampler.common.rest.validation.xml;

import java.io.InputStream;

import jakarta.annotation.Priority;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Model;

import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.rest.validation.xml.JaxbTool;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;

/**
 * JaxbTool alternative with override {@link JaxbTool#getRequestVersion(InputStream)} method without inputStream read
 *
 * @since 1.1.0
 * @author speter555
 */
@Alternative
@Model
@Priority(400)
public class ProjectJaxbTool extends JaxbTool {

    /**
     * Default constructor
     */
    public ProjectJaxbTool() {
        super();
    }

    public String getRequestVersion(InputStream entityStream) throws BaseException {
        if (entityStream == null) {
            throw new InvalidParameterException("entityStream is null!");
        } else {
            return null;
        }
    }
}
