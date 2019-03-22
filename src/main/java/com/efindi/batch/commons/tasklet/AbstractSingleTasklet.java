package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.style.DefaultStyle;
import com.efindi.batch.commons.tasklet.function.VoidMethodExecutionException;
import com.efindi.batch.commons.tasklet.task.ITask;
import org.immutables.value.Value;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Value.Immutable
@DefaultStyle
public abstract class AbstractSingleTasklet<T> extends AbstractExecutionTasklet<T> implements Tasklet {
    @Value.Parameter
    public abstract ITask<T> task();

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try {
            run(task());
        } catch (VoidMethodExecutionException e) {
            if (propagateThrowable()) {
                throw e;
            }
        }
        return RepeatStatus.FINISHED;
    }
}
