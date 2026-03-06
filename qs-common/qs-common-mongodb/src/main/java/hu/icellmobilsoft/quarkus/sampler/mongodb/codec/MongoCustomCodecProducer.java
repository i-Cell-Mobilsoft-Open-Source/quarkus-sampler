/*-
 * #%L
 * Quarkus-sampler
 * %%
 * Copyright (C) 2024 - 2025 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.quarkus.sampler.mongodb.codec;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import org.bson.codecs.configuration.CodecRegistries;

import com.mongodb.MongoClientSettings;

/**
 * CDI producer for custom MongoDB codec registry. This producer configures MongoDB client settings with custom codecs, including the
 * {@link OffsetDateTimeCodec} for proper OffsetDateTime handling.
 *
 * @author balazs.joo
 * @since 0.1.0
 */
@ApplicationScoped
public class MongoCustomCodecProducer {

    @Produces
    public MongoClientSettings.Builder mongoClientSettingsBuilder() {
        return MongoClientSettings.builder()
                .codecRegistry(
                        CodecRegistries.fromRegistries(
                                MongoClientSettings.getDefaultCodecRegistry(),
                                CodecRegistries.fromProviders(new OffsetDateTimeCodecProvider())));
    }
}
