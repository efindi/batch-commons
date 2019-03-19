package com.efindi.batch.commons.function;

@FunctionalInterface
public interface VoidMethod {
    Void execute() throws VoidMethodExecutionException;
}
