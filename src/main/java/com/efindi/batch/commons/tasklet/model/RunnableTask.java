package com.efindi.batch.commons.tasklet.model;

import org.immutables.value.Value;

@Value.Immutable
public interface RunnableTask extends Task<Runnable> {
}
