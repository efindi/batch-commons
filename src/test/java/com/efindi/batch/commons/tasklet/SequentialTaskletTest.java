package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.tasklet.function.VoidMethod;
import com.efindi.batch.commons.tasklet.function.VoidMethodExecutionException;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

class SequentialTaskletTest extends AbstractListTaskletTest {

    @Test
    @Override
    public void runnableTaskletHasOneTask() {
        SequentialTasklet<Runnable> tasklet = SequentialTasklet.of(List.of(runnableTask));
        assertEquals(tasklet.tasks().size(), 1);
    }

    @Test
    @Override
    public void runnableTaskletHasFiveTask() {
        SequentialTasklet<Runnable> tasklet = SequentialTasklet.of(
                List.of(runnableTask, runnableTask, runnableTask, runnableTask, runnableTask)
        );
        assertEquals(tasklet.tasks().size(), 5);
    }

    @Test
    @Override
    public void runnableTaskletShouldRunSuccessfully() {
        SequentialTasklet<Runnable> tasklet = SequentialTasklet.of(List.of(runnableTask));
        assertAll(() -> tasklet.execute(contribution, chunkContext));
    }

    @Test
    @Override
    public void voidMethodTaskletHasOneTask() {
        SequentialTasklet<VoidMethod> tasklet = SequentialTasklet.of(List.of(voidMethodTask));
        assertEquals(tasklet.tasks().size(), 1);
    }

    @Test
    @Override
    public void voidMethodTaskletHasFiveTask() {
        SequentialTasklet<VoidMethod> tasklet = SequentialTasklet.of(
                List.of(voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask)
        );
        assertEquals(tasklet.tasks().size(), 5);
    }

    @Test
    @Override
    void voidMethodTaskletWillThrowVoidMethodExecutionException() {
        SequentialTasklet<VoidMethod> tasklet = SequentialTasklet
                .<VoidMethod>builder()
                .addTasks(voidMethodTaskWithException)
                .build();
        assertThrows(VoidMethodExecutionException.class, () -> tasklet.execute(contribution, chunkContext));
    }

    @Test
    @Override
    void voidMethodTaskletShouldRunSuccessfully() {
        SequentialTasklet<VoidMethod> tasklet = SequentialTasklet.of(List.of(voidMethodTask));
        assertAll(() -> tasklet.execute(contribution, chunkContext));
    }

    @Test
    void sequentialTaskletDefaultWithOneTaskShouldNotRunMoreThanTwoSecond() {
        SequentialTasklet<VoidMethod> tasklet = SequentialTasklet.of(List.of(voidMethodTask));
        assertTimeoutPreemptively(ofSeconds(2), () -> tasklet.execute(contribution, chunkContext));
    }

    @Test
    void sequentialTaskletWithOneTaskAnd10msPauseShouldBeCompletedBefore20ms() {
        SequentialTasklet<VoidMethod> tasklet = SequentialTasklet
                .<VoidMethod>builder()
                .pause(10)
                .addTasks(voidMethodTask)
                .build();
        assertTimeoutPreemptively(ofMillis(20), () -> tasklet.execute(contribution, chunkContext));
    }

    @Test
    void sequentialTaskletDefaultWithFiveEmptyTasksShouldBeCompletedBefore6000ms() {
        SequentialTasklet<VoidMethod> tasklet = SequentialTasklet.of(
                List.of(voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask)
        );
        assertTimeoutPreemptively(ofMillis(6000), () -> tasklet.execute(contribution, chunkContext));
    }

    @Test
    void sequentialTaskletWithFiveEmptyTasksAnd10msPauseShouldBeCompletedBefore60ms() {
        SequentialTasklet<VoidMethod> tasklet = SequentialTasklet
                .<VoidMethod>builder()
                .pause(10)
                .addTasks(voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask)
                .build();
        assertTimeoutPreemptively(ofMillis(60), () -> tasklet.execute(contribution, chunkContext));
    }

}
