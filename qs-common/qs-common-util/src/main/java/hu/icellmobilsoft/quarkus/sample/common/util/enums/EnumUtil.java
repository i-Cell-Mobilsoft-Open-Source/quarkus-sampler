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
package hu.icellmobilsoft.quarkus.sample.common.util.enums;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;

/**
 * Utilites for enums
 * 
 * @author speter555
 *
 */
public class EnumUtil extends hu.icellmobilsoft.coffee.tool.utils.enums.EnumUtil {

    /**
     * Private constructor
     */
    private EnumUtil() {
        // Default constructor for java 21
    }
    /**
     * Enum collection to enum list conversion
     * 
     * @param <A>
     *            type of input enum
     * @param <B>
     *            type of desired enum
     * @param sourceList
     *            enum list to convert
     * @param targetClass
     *            class of desired enum
     * @return instance list of {@code targetClass} (having same name as in {@code sourceList}) - or emptyList, if sourceList is null or empty
     */
    public static <A extends Enum<A>, B extends Enum<B>> List<B> convertList(List<A> sourceList, Class<B> targetClass) {
        if (CollectionUtils.isEmpty(sourceList) || Objects.isNull(targetClass)) {
            return Collections.emptyList();
        }

        return sourceList.stream().map(e -> EnumUtils.getEnum(targetClass, e.name())).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
