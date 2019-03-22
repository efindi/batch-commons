package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.style.DefaultStyle;
import com.efindi.batch.commons.tasklet.function.VoidMethodExecutionException;
import com.efindi.batch.commons.tasklet.task.ITask;
import org.immutables.value.Value;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

@Value.Immutable
@DefaultStyle
public abstract class AbstractSequentialTasklet<T> extends AbstractExecutionTasklet<T> implements ListTasklet<T> {

    @Override
    @Value.Parameter
    public abstract List<ITask<T>> tasks();

    @Value.Default
    public long pause() {
        return 1000;
    }

    @Override
    @Value.Derived
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try {
            if (pause() > 0) {
                tasks().forEach(task -> {
                    try {
                        this.run(task);
                        Thread.sleep(pause());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            } else {
                tasks().forEach(this::run);
            }
        } catch (VoidMethodExecutionException e) {
            if (propagateError()) {
                throw e;
            }
        }
        return RepeatStatus.FINISHED;
    }

}
