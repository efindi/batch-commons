package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.style.DefaultStyle;
import com.efindi.batch.commons.tasklet.function.VoidMethodExecutionException;
import com.efindi.batch.commons.tasklet.task.ITask;
import java.util.List;
import org.immutables.value.Value;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

@Value.Immutable
@DefaultStyle
public abstract class AbstractParallelStreamTasklet<T> extends AbstractExecutionTasklet<T>
    implements ListTasklet<T> {

  @Override
  @Value.Parameter
  public abstract List<ITask<T>> tasks();

  @Override
  @Value.Derived
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    try {
      tasks().parallelStream().forEach(task -> this.run(task, contribution, chunkContext));
    } catch (VoidMethodExecutionException e) {
      if (propagateThrowable()) {
        throw e;
      }
    }
    return RepeatStatus.FINISHED;
  }
}
