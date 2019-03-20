package com.efindi.batch.commons.function;

import org.immutables.value.Value;

@Value.Immutable(builder = false)
@Value.Style(allParameters = true)
public interface Task<T> {
    T execute() throws RuntimeException;
}
