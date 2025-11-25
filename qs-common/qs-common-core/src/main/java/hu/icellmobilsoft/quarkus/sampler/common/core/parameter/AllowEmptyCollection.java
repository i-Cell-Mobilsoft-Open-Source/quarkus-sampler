package hu.icellmobilsoft.quarkus.sampler.common.core.parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

/**
 * Marker annotation indicating that a {@link Collection} parameter may be empty.
 *
 * @author attila.kiss3
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface AllowEmptyCollection {
}
