package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.function.VoidMethodExecutionException;
import org.immutables.value.Value;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * A tasklet that executes each task from a list with a pause (default to 1000ms).
 */
@Value.Immutable
public abstract class SequentialExecutionTasklet<T> extends ExecutionTasklet<T> {
    @Value.Default
    public long pause() {
        return 1000;
    }

    @Value.Derived
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try {
            if (pause() > 0) {
                taskList().forEach(task -> {
                    try {
                        this.run(task);
                        Thread.sleep(pause());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            } else {
                taskList().forEach(this::run);
            }
        } catch (VoidMethodExecutionException e) {
            log(e); throw e;
        }
        return RepeatStatus.FINISHED;
    }
}
