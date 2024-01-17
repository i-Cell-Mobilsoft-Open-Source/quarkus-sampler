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
package hu.icellmobilsoft.quarkus.sampler.etcd;

import hu.icellmobilsoft.coffee.module.configdoc.ConfigDoc;

/**
 * Etcd keys what is used in project
 *
 * @author speter555
 */
@ConfigDoc
public interface EtcdKey {

    /**
     * Public prefix
     */
    @ConfigDoc(exclude = true)
    String PREFIX_PUBLIC = "public.";

    /**
     * Protected prefix
     */
    @ConfigDoc(exclude = true)
    String PREFIX_PROTECTED = "protected.";

    /**
     * Private prefix
     */
    @ConfigDoc(exclude = true)
    String PREFIX_PRIVATE = "private.";

    /**
     * Millis postfix
     */
    @ConfigDoc(exclude = true)
    String POSTFIX_MILLIS = ".millis";

    /**
     * Seconds postfix
     */
    @ConfigDoc(exclude = true)
    String POSTFIX_SECONDS = ".seconds";

    /**
     * Minutes postfix
     */
    @ConfigDoc(exclude = true)
    String POSTFIX_MINUTES = ".minutes";

    /**
     * Days postfix
     */
    @ConfigDoc(exclude = true)
    String POSTFIX_DAYS = ".days";

    /**
     * Years postfix
     */
    @ConfigDoc(exclude = true)
    String POSTFIX_YEARS = ".years";

}
