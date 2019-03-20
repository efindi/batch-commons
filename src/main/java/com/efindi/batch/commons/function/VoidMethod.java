package com.efindi.batch.commons.function;

@FunctionalInterface
public interface VoidMethod extends Task<Void> {
    Void execute() throws VoidMethodExecutionException;
}
