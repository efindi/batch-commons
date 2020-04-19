package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.tasklet.function.VoidMethod;
import com.efindi.batch.commons.tasklet.task.ITask;
import org.immutables.value.Value;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;

public abstract class AbstractExecutionTasklet<T> {

  @Value.Default
  protected boolean propagateThrowable() {
    return true;
  }

  @Value.Derived
  protected void run(ITask<T> task, StepContribution contribution, ChunkContext chunkContext)
      throws RuntimeException {
    if (task.task() instanceof Runnable) {
      ((Runnable) task.task()).run();
    } else if (task.task() instanceof VoidMethod) {
      ((VoidMethod) task.task()).execute(contribution, chunkContext);
    }
  }
}
