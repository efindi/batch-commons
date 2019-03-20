package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.function.VoidMethodExecutionException;
import org.immutables.value.Value;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * A tasklet that executes all tasks from a list in parallel.
 */
@Value.Immutable
public abstract class ParallelStreamExecutionTasklet<T> extends ExecutionTasklet<T> {
    @Value.Derived
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try {
            taskList().parallelStream().forEach(this::run);
        } catch (VoidMethodExecutionException e) {
            log(e); throw e;
        }
        return RepeatStatus.FINISHED;
    }
}
