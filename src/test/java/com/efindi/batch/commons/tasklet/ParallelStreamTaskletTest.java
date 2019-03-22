package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.tasklet.function.VoidMethod;
import com.efindi.batch.commons.tasklet.function.VoidMethodExecutionException;
import com.efindi.batch.commons.tasklet.option.LogOption;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamTaskletTest extends AbstractListTaskletTest {

    @Test
    @Override
    void runnableTaskletHasOneTask() {
        ParallelStreamTasklet<Runnable> tasklet = ParallelStreamTasklet.of(List.of(runnableTask));
        assertEquals(tasklet.tasks().size(), 1);
    }

    @Test
    @Override
    void runnableTaskletHasFiveTask() {
        ParallelStreamTasklet<Runnable> tasklet = ParallelStreamTasklet.of(
                List.of(runnableTask, runnableTask, runnableTask, runnableTask, runnableTask)
        );
        assertEquals(tasklet.tasks().size(), 5);
    }

    @Test
    @Override
    void runnableTaskletShouldRunSuccessfully() {
        ParallelStreamTasklet<Runnable> tasklet = ParallelStreamTasklet.of(List.of(runnableTask));
        assertAll(() -> tasklet.execute(contribution, chunkContext));
    }

    @Test
    @Override
    void voidMethodTaskletHasOneTask() {
        ParallelStreamTasklet<VoidMethod> tasklet = ParallelStreamTasklet.of(List.of(voidMethodTask));
        assertEquals(tasklet.tasks().size(), 1);
    }

    @Test
    @Override
    void voidMethodTaskletHasFiveTask() {
        ParallelStreamTasklet<VoidMethod> tasklet = ParallelStreamTasklet.of(
                List.of(voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask)
        );
        assertEquals(tasklet.tasks().size(), 5);
    }

    @Test
    @Override
    void voidMethodTaskletWillThrowVoidMethodExecutionException() {
        ParallelStreamTasklet<VoidMethod> tasklet = ParallelStreamTasklet
                .<VoidMethod>builder()
                .logConfig(LogOption.of("Test throwing: {}", true))
                .addTasks(voidMethodTaskWithException)
                .build();
        assertThrows(VoidMethodExecutionException.class, () -> tasklet.execute(contribution, chunkContext));
    }

    @Test
    @Override
    void voidMethodTaskletShouldRunSuccessfully() {
        ParallelStreamTasklet<VoidMethod> tasklet = ParallelStreamTasklet.of(List.of(voidMethodTask));
        assertAll(() -> tasklet.execute(contribution, chunkContext));
    }

}
