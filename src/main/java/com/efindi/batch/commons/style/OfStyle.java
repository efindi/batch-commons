package com.efindi.batch.commons.style;

import org.immutables.value.Value;

@Value.Style(
        typeImmutable = "*",
        typeAbstract = {"Abstract*", "I*"},
        allParameters = true,
        defaults = @Value.Immutable(builder = false, copy = false)
)
public @interface OfStyle {
}
