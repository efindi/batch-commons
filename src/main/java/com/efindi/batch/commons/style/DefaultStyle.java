package com.efindi.batch.commons.style;

import org.immutables.value.Value;

@Value.Style(
        typeImmutable = "*",
        typeAbstract = {"Abstract*", "I*"}
)
public @interface DefaultStyle {}
