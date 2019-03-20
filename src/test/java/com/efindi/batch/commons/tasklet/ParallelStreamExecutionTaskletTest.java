package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.function.VoidMethod;
import com.efindi.batch.commons.function.VoidMethodExecutionException;
import com.efindi.batch.commons.log.ImmutableLogConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParallelStreamExecutionTaskletTest extends AbstractTaskletTest {

    @Test
    @Override
    void taskletRunnableHasOneTask() {
        ExecutionTasklet<Runnable> runnableExecutionTasklet = ImmutableParallelStreamExecutionTasklet
                .<Runnable>builder()
                .addTaskList(runnableTask)
                .build();
        assertEquals(runnableExecutionTasklet.taskList().size(), 1);
    }

    @Test
    @Override
    void taskletVoidMethodHasOneTask() {
        ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableParallelStreamExecutionTasklet
                .<VoidMethod>builder()
                .addTaskList(voidMethodTask)
                .build();
        assertEquals(voidMethodExecutionTasklet.taskList().size(), 1);
    }

    @Test
    @Override
    void taskletRunnableHasFiveTask() {
        ExecutionTasklet<Runnable> runnableExecutionTasklet = ImmutableParallelStreamExecutionTasklet
                .<Runnable>builder()
                .addTaskList(runnableTask, runnableTask, runnableTask, runnableTask, runnableTask)
                .build();
        assertEquals(runnableExecutionTasklet.taskList().size(), 5);
    }

    @Test
    @Override
    void taskletVoidMethodHasFiveTask() {
        ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableParallelStreamExecutionTasklet
                .<VoidMethod>builder()
                .addTaskList(voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask)
                .build();
        assertEquals(voidMethodExecutionTasklet.taskList().size(), 5);
    }

    @Test
    @Override
    void taskletVoidMethodWillThrowVoidMethodExecutionException() {
        ExecutionTasklet<VoidMethod> runnableExecutionTasklet = ImmutableParallelStreamExecutionTasklet
                .<VoidMethod>builder()
                .logConfig(ImmutableLogConfig.of("Test throwing: {}", true))
                .addTaskList(voidMethodTaskWithException)
                .build();
        assertThrows(VoidMethodExecutionException.class, () -> runnableExecutionTasklet.execute(contribution, chunkContext));
    }

    @Test
    @Override
    void taskletShouldRunSuccessfully() {
        ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableParallelStreamExecutionTasklet
                .<VoidMethod>builder()
                .addTaskList(voidMethodTask)
                .build();
        assertAll(() -> voidMethodExecutionTasklet.execute(contribution, chunkContext));
    }

}
