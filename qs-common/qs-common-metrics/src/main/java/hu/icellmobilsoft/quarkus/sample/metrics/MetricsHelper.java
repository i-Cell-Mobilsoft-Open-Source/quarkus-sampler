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
package hu.icellmobilsoft.quarkus.sample.metrics;

import java.time.Duration;
import java.util.Arrays;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.Tag;

/**
 * Metrics helper class
 *
 * @author speter555
 * @since 0.1.0
 */
@Dependent
public class MetricsHelper {

    @Inject
    MetricRegistry metricRegistry;

    /**
     * Default constructor
     */
    public MetricsHelper() {
        // Default constructor for java 21
    }

    /**
     * Add Gauge type metric
     * 
     * @param metadataName
     *            metadata's name
     * @param metadataDescription
     *            metadata's description
     * @param gaugeValue
     *            value for gauge
     * @param tagKey
     *            tag's key
     * @param tagValue
     *            tag's value
     */
    public void addGaugeMetric(String metadataName, String metadataDescription, int gaugeValue, String tagKey, String tagValue) {
        Tag tag = new Tag(tagKey, tagValue);
        addGaugeMetric(metadataName, metadataDescription, gaugeValue, tag);
    }

    /**
     * Add Gauge type metric
     * 
     * @param metadataName
     *            metadata's name
     * @param metadataDescription
     *            metadata's description
     * @param gaugeValue
     *            value for gauge
     * @param tags
     *            tags for metric
     */
    public void addGaugeMetric(String metadataName, String metadataDescription, int gaugeValue, Tag... tags) {
        Metadata metadataG = Metadata.builder().withName(metadataName).withDescription(metadataDescription).withType(MetricType.GAUGE).build();
        if (tags != null && CollectionUtils.isNotEmpty(Arrays.asList(tags))) {
            metricRegistry.gauge(metadataG, () -> gaugeValue, tags);
        } else {
            metricRegistry.gauge(metadataG, () -> gaugeValue);
        }
    }

    /**
     * Add Gauge type metric
     * 
     * @param metadataName
     *            metadata's name
     * @param metadataDescription
     *            metadata's description
     * @param tagKey
     *            tag's key
     * @param tagValue
     *            tag's value
     */
    public void addCounterIncOneMetric(String metadataName, String metadataDescription, String tagKey, String tagValue) {
        Tag keyTag = new Tag(tagKey, tagValue);
        addCounterIncOneMetric(metadataName, metadataDescription, keyTag);
    }

    /**
     * Add Gauge type metric
     *
     * @param metadataName
     *            metadata's name
     * @param metadataDescription
     *            metadata's description
     * @param tags
     *            tags for metric
     */
    public void addCounterIncOneMetric(String metadataName, String metadataDescription, Tag... tags) {
        Metadata metadata = Metadata.builder().withName(metadataName).withDescription(metadataDescription).withType(MetricType.COUNTER).build();
        if (tags != null && CollectionUtils.isNotEmpty(Arrays.asList(tags))) {
            metricRegistry.counter(metadata, tags).inc();
        } else {
            metricRegistry.counter(metadata).inc();
        }
    }

    /**
     * Add Timer type metric
     *
     * @param metadataName
     *            metadata's name
     * @param metadataDescription
     *            metadata's description
     * @param duration
     *            duration for timer
     * @param unit
     *            unit for metadata
     * @param tagKey
     *            tag's key
     * @param tagValue
     *            tag's value
     */
    public void addTimerMetric(String metadataName, String metadataDescription, Duration duration, String unit, String tagKey, String tagValue) {
        Tag tag = new Tag(tagKey, tagValue);
        addTimerMetric(metadataName, metadataDescription, duration, unit, tag);
    }

    /**
     * Add Timer type metric
     *
     * @param metadataName
     *            metadata's name
     * @param metadataDescription
     *            metadata's description
     * @param duration
     *            duration for timer
     * @param unit
     *            unit for metadata
     * @param tags
     *            tags for metric
     */
    public void addTimerMetric(String metadataName, String metadataDescription, Duration duration, String unit, Tag... tags) {
        Metadata metadata = Metadata.builder()
                .withName(metadataName)
                .withDescription(metadataDescription)
                .withType(MetricType.TIMER)
                .withUnit(unit)
                .build();
        if (tags != null && CollectionUtils.isNotEmpty(Arrays.asList(tags))) {
            metricRegistry.timer(metadata, tags).update(duration);
        } else {
            metricRegistry.timer(metadata).update(duration);
        }
    }

}
