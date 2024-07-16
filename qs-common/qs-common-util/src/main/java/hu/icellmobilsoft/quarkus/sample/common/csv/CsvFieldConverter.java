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
package hu.icellmobilsoft.quarkus.sample.common.csv;

/**
 * Interface used for CSV conversion.
 * 
 * @param <T>
 *            The class to which we will convert
 * @author speter555
 */
public interface CsvFieldConverter<T> {

    /**
     * Fetching CSV values
     * 
     * @param object
     *            The objects
     * @return CSV value
     */
    String getCsvValue(T object);

    /**
     * Assigning the CSV value to the corresponding field
     * 
     * @param object
     *            The objects
     * @param csvValue
     *            the CSV value
     * @return The object to which the CSV value is assigned
     */
    T assignTo(T object, String csvValue);
}
