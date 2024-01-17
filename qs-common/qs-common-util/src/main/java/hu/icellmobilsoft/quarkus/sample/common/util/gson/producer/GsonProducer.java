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
package hu.icellmobilsoft.quarkus.sample.common.util.gson.producer;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.YearMonth;
import java.util.Date;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.icellmobilsoft.coffee.tool.gson.ByteArrayConverter;
import hu.icellmobilsoft.coffee.tool.gson.ClassTypeAdapter;
import hu.icellmobilsoft.coffee.tool.gson.DateConverter;
import hu.icellmobilsoft.coffee.tool.gson.DurationConverter;
import hu.icellmobilsoft.coffee.tool.gson.LocalDateConverter;
import hu.icellmobilsoft.coffee.tool.gson.OffsetDateTimeConverter;
import hu.icellmobilsoft.coffee.tool.gson.OffsetTimeConverter;
import hu.icellmobilsoft.coffee.tool.gson.XMLGregorianCalendarConverter;
import hu.icellmobilsoft.coffee.tool.gson.YearMonthConverter;

/**
 * {@link Gson} CDI producer.
 *
 * @author speter555
 * @since 0.1.0
 */
@ApplicationScoped
public class GsonProducer {

    private Gson gson;


    /**
     * Default constructor
     */
    public GsonProducer() {
        // Default constructor for java 21
    }

    /**
     * Returns {@link Gson} with custom type adapters.
     *
     * @return {@link Gson}
     */
    @Produces
    public Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().disableHtmlEscaping()
                    .registerTypeAdapter(Class.class, new ClassTypeAdapter())
                    .registerTypeAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarConverter())
                    .registerTypeAdapter(Date.class, new DateConverter())
                    .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter())
                    .registerTypeAdapter(OffsetTime.class, new OffsetTimeConverter())
                    .registerTypeAdapter(LocalDate.class, new LocalDateConverter())
                    .registerTypeAdapter(Duration.class, new DurationConverter())
                    .registerTypeAdapter(YearMonth.class, new YearMonthConverter())
                    .registerTypeHierarchyAdapter(byte[].class, new ByteArrayConverter())
                    .create();
        }
        return gson;
    }
}
