/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.sampler.ts.sample.panache.builder;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;

import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleCoreType;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleValueEnumType;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.dto.BaseBuilder;

/**
 * Sample reqeust {@link SampleRequest} builder class
 *
 * @author bucherarnold
 * @since 0.1.0
 */
@Model
public class SampleRequestBuilder extends BaseBuilder<SampleRequest> {

    @Override
    public SampleRequest createEmpty() {
        SampleRequest request = new SampleRequest();
        request.withContext(DtoHelper.createContext());
        return request;
    }

    @PostConstruct
    public void init() {
        setDto(createEmpty());
    }

    /**
     * Get default filled dto
     *
     * @return valid dto with sample values
     */
    public SampleRequest getDefault() {
        getDto().setSample(new SampleCoreType().withColumnA("colA").withColumnB(SampleValueEnumType.VALUE_B));
        return getDto();
    }
}
