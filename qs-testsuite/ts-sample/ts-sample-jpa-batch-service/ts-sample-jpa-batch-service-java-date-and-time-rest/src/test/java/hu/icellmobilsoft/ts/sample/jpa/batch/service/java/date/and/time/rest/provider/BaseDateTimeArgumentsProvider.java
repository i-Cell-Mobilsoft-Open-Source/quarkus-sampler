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
package hu.icellmobilsoft.ts.sample.jpa.batch.service.java.date.and.time.rest.provider;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

/**
 * {@link ArgumentsProvider} for base date times.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
public class BaseDateTimeArgumentsProvider implements ArgumentsProvider {

    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");
    private static final ZoneId CET_ZONE_ID = ZoneId.of("CET");

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(OffsetDateTime.now(UTC_ZONE_ID)),
                Arguments.of(OffsetDateTime.now(UTC_ZONE_ID).withYear(1900)),
                Arguments.of(OffsetDateTime.now(CET_ZONE_ID)),
                Arguments.of(OffsetDateTime.now(CET_ZONE_ID).withYear(1900)));
    }
}
