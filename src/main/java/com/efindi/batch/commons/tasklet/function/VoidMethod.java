package com.efindi.batch.commons.tasklet.function;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;

@FunctionalInterface
public interface VoidMethod {
  void execute(StepContribution contribution, ChunkContext chunkContext)
      throws VoidMethodExecutionException;
}
