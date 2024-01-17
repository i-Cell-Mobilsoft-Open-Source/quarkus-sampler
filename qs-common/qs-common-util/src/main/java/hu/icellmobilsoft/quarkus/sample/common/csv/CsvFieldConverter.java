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
 * CSV konvertálásnál használt interface
 * 
 * @param <T>
 *            az osztály, amelyre konvertálunk majd
 * @author speter555
 */
public interface CsvFieldConverter<T> {

    /**
     * CSV érték lekérdezése
     * 
     * @param object
     *            az objektum
     * @return CSV érték
     */
    String getCsvValue(T object);

    /**
     * Hozzárendeli a CSV értéket a megfelelő mezőhöz
     * 
     * @param object
     *            az objektum
     * @param csvValue
     *            a CSV érték
     * @return az objektum, amelyhez hozzá van rendelve a CSV érték
     */
    T assignTo(T object, String csvValue);
}
