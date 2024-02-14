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
package hu.icellmobilsoft.quarkus.sampler.api.jakarta.dto.exception;

import hu.icellmobilsoft.coffee.dto.exception.BusinessException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;

/**
 * Exception for sampler's sample check
 * 
 * @author Imre Scheffer
 * @since 2.0.0
 */
public class SamplerException extends BusinessException {

    private static final long serialVersionUID = -6095346128924046090L;

    /**
     * Constructor for SamplerException.
     *
     * @param message
     *            message
     */
    public SamplerException(String message) {
        this(CoffeeFaultType.OPERATION_FAILED, message, null);
    }

    /**
     * Constructor for SamplerException.
     *
     * @param faultTypeEnum
     *            faultTypeEnum
     * @param message
     *            message
     */
    public SamplerException(Enum<?> faultTypeEnum, String message) {
        this(faultTypeEnum, message, null);
    }

    /**
     * Constructor for SamplerException.
     * 
     * @param faultTypeEnum
     *            faultTypeEnum
     * @param message
     *            message
     * @param e
     *            e
     */
    public SamplerException(Enum<?> faultTypeEnum, String message, Throwable e) {
        super(faultTypeEnum, message, e);
    }

}
