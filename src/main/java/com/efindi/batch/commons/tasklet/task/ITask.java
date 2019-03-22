package com.efindi.batch.commons.tasklet.task;

import com.efindi.batch.commons.style.OfStyle;
import org.immutables.value.Value;

@Value.Immutable
@OfStyle
public interface ITask<T> {
    T task() throws RuntimeException;
}
