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
package hu.icellmobilsoft.quarkus.sample.common.util.date;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Class for date utility methods. Extends {@link hu.icellmobilsoft.coffee.tool.utils.date.DateUtil}.
 *
 * @author speter555
 * @since 0.1.0
 */
public class DateUtil extends hu.icellmobilsoft.coffee.tool.utils.date.DateUtil {

    /**
     * Private constructor
     */
    private DateUtil() {
        // Default constructor for java 21
    }

    /**
     *
     * Tells if the {@link OffsetDateTime} param is in the time interval between {@code intervalStart} and {@code intervalEnd} {@link OffsetDateTime}
     * params where {@code intervalStart} is inclusive and {@code intervalEnd} is exclusive. If dateTime or intervalStart parameter is null, then the
     * method returns null.
     * 
     * @param dateTime
     *            dateTime
     * @param intervalStart
     *            interval start (inclusive)
     * @param intervalEnd
     *            interval end (exclusive) - null value means the interval has no end
     * @return true if dateTime is in time interval, false if not, null if dateTime or intervalStart param is null
     */
    public static Boolean isOffsetDateTimeInTimeInterval(OffsetDateTime dateTime, OffsetDateTime intervalStart, OffsetDateTime intervalEnd) {
        if (Objects.isNull(dateTime) || Objects.isNull(intervalStart)) {
            return null;
        }
        return (dateTime.isAfter(intervalStart) || dateTime.isEqual(intervalStart)) && (intervalEnd == null || dateTime.isBefore(intervalEnd));
    }

    /**
     *
     * Tells if the {@link LocalDate} param is in the time interval between intervalStart and intervalEnd {@link LocalDate} params inclusively. If any
     * parameter is null, then the method returns null as well.
     *
     * @param date
     *            date
     * @param intervalStart
     *            interval start
     * @param intervalEnd
     *            interval end
     * @return true if date is in time interval, false if not, null if any param is null
     */
    public static Boolean isLocalDateInTimeInterval(LocalDate date, LocalDate intervalStart, LocalDate intervalEnd) {
        if (Objects.isNull(date) || Objects.isNull(intervalStart) || Objects.isNull(intervalEnd)) {
            return null;
        }
        return (date.isAfter(intervalStart) || date.isEqual(intervalStart)) && (date.isBefore(intervalEnd) || date.isEqual(intervalEnd));
    }

}
