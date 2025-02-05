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
package hu.icellmobilsoft.quarkus.sampler.api.jakarta.dto.exception;

/**
 * A custom unchecked exception that serves as a wrapper for checked exceptions, allowing seamless propagation without requiring explicit handling.
 * <p>
 * This exception is primarily used to wrap service-layer or repository exceptions into a {@link RuntimeException}, enabling cleaner error handling
 * while maintaining the original exception's context.
 * <p>
 * Features:
 * <ul>
 * <li>Supports exception message customization.</li>
 * <li>Preserves original exception stack trace.</li>
 * <li>Compatible with Java's exception chaining mechanism.</li>
 * </ul>
 * <p>
 * Example usage:
 * 
 * <pre>{@code
 * try {
 *     riskyOperation();
 * } catch (IOException e) {
 *     throw new BaseRuntimeException("I/O operation failed", e);
 * }
 * }</pre>
 *
 * @author balazs.joo
 * @since 0.1.0
 */
public class BaseRuntimeException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     *
     * @param message
     *            the detail message
     */
    public BaseRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified cause. The detail message is derived from the cause.
     *
     * @param cause
     *            the cause of this exception
     */
    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     *
     * @param message
     *            the detail message
     * @param cause
     *            the cause of this exception
     */
    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with full exception chaining support, including suppression and writable stack trace configuration.
     *
     * @param message
     *            the detail message
     * @param cause
     *            the cause of this exception
     * @param enableSuppression
     *            whether suppression is enabled or not
     * @param writableStackTrace
     *            whether the stack trace should be writable
     */
    public BaseRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
