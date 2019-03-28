package com.efindi.batch.commons.tasklet.function;

@FunctionalInterface
public interface VoidMethod {
    Void execute() throws VoidMethodExecutionException;
}
