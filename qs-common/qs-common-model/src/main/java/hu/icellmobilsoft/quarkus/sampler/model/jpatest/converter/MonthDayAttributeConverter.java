/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 - 2023 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.quarkus.sampler.model.jpatest.converter;

import java.time.MonthDay;

import jakarta.persistence.AttributeConverter;

/**
 * {@link AttributeConverter} for {@link MonthDay} and {@link String}.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
public class MonthDayAttributeConverter implements AttributeConverter<MonthDay, String> {

    @Override
    public String convertToDatabaseColumn(MonthDay attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public MonthDay convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return MonthDay.parse(dbData);
    }
}
