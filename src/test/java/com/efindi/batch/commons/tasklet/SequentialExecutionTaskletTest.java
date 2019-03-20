package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.function.VoidMethod;
import com.efindi.batch.commons.function.VoidMethodExecutionException;
import com.efindi.batch.commons.log.ImmutableLogConfig;
import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

class SequentialExecutionTaskletTest extends AbstractTaskletTest {

    @Test
    @Override
    void taskletRunnableHasOneTask() {
        ExecutionTasklet<Runnable> runnableExecutionTasklet = ImmutableSequentialExecutionTasklet
                .<Runnable>builder()
                .addTaskList(runnableTask)
                .build();
        assertEquals(runnableExecutionTasklet.taskList().size(), 1);
    }

    @Test
    @Override
    void taskletVoidMethodHasOneTask() {
        ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableSequentialExecutionTasklet
                .<VoidMethod>builder()
                .addTaskList(voidMethodTask)
                .build();
        assertEquals(voidMethodExecutionTasklet.taskList().size(), 1);
    }

    @Test
    @Override
    void taskletRunnableHasFiveTask() {
        ExecutionTasklet<Runnable> runnableExecutionTasklet = ImmutableSequentialExecutionTasklet
                .<Runnable>builder()
                .addTaskList(runnableTask, runnableTask, runnableTask, runnableTask, runnableTask)
                .build();
        assertEquals(runnableExecutionTasklet.taskList().size(), 5);
    }

    @Test
    @Override
    void taskletVoidMethodHasFiveTask() {
        ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableSequentialExecutionTasklet
                .<VoidMethod>builder()
                .addTaskList(voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask)
                .build();
        assertEquals(voidMethodExecutionTasklet.taskList().size(), 5);
    }

    @Test
    @Override
    void taskletVoidMethodWillThrowVoidMethodExecutionException() {
        ExecutionTasklet<VoidMethod> runnableExecutionTasklet = ImmutableSequentialExecutionTasklet
                .<VoidMethod>builder()
                .logConfig(ImmutableLogConfig.of("Test throwing: {}", true))
                .addTaskList(voidMethodTaskWithException)
                .build();
        assertThrows(VoidMethodExecutionException.class, () -> runnableExecutionTasklet.execute(contribution, chunkContext));
    }

    @Test
    @Override
    void taskletShouldRunSuccessfully() {
        ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableSequentialExecutionTasklet
                .<VoidMethod>builder()
                .addTaskList(voidMethodTask)
                .build();
        assertAll(() -> voidMethodExecutionTasklet.execute(contribution, chunkContext));
    }

    @Test
    void sequentialExecutionTaskletDefaultWithOneTaskShouldNotRunMoreThanTwoSecond() {
        ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableSequentialExecutionTasklet
                .<VoidMethod>builder()
                .addTaskList(voidMethodTask)
                .build();
        assertTimeoutPreemptively(ofSeconds(2), () -> voidMethodExecutionTasklet.execute(contribution, chunkContext));
    }

    @Test
    void sequentialExecutionTaskletWithOneTaskAnd10msPauseShouldBeCompletedBefore20ms() {
        ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableSequentialExecutionTasklet
                .<VoidMethod>builder()
                .pause(10)
                .addTaskList(voidMethodTask)
                .build();
        assertTimeoutPreemptively(ofMillis(20), () -> voidMethodExecutionTasklet.execute(contribution, chunkContext));
    }

    @Test
    void sequentialExecutionTaskletDefaultWithFiveEmptyTasksShouldBeCompletedBefore6000ms() {
        ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableSequentialExecutionTasklet
                .<VoidMethod>builder()
                .addTaskList(voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask)
                .build();
        assertTimeoutPreemptively(ofMillis(6000), () -> voidMethodExecutionTasklet.execute(contribution, chunkContext));
    }

    @Test
    void sequentialExecutionTaskletWithFiveEmptyTasksAnd10msPauseShouldBeCompletedBefore60ms() {
        ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableSequentialExecutionTasklet
                .<VoidMethod>builder()
                .pause(10)
                .addTaskList(voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask)
                .build();
        assertTimeoutPreemptively(ofMillis(60), () -> voidMethodExecutionTasklet.execute(contribution, chunkContext));
    }

}
