package com.efindi.batch.commons.tasklet;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.efindi.batch.commons.tasklet.function.VoidMethod;
import com.efindi.batch.commons.tasklet.function.VoidMethodExecutionException;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class ParallelStreamTaskletTest extends AbstractListTaskletTest {

  @Test
  @Override
  void runnableTaskletHasOneTask() {
    ParallelStreamTasklet<Runnable> tasklet = ParallelStreamTasklet.of(
        Collections.singletonList(runnableTask));
    assertEquals(tasklet.tasks().size(), 1);
  }

  @Test
  @Override
  void runnableTaskletHasFiveTask() {
    ParallelStreamTasklet<Runnable> tasklet =
        ParallelStreamTasklet.of(
            asList(runnableTask, runnableTask, runnableTask, runnableTask, runnableTask));
    assertEquals(tasklet.tasks().size(), 5);
  }

  @Test
  @Override
  void runnableTaskletShouldRunSuccessfully() {
    ParallelStreamTasklet<Runnable> tasklet =
        ParallelStreamTasklet.of(Collections.singletonList(runnableTask));
    assertAll(() -> tasklet.execute(contribution, chunkContext));
  }

  @Test
  @Override
  void voidMethodTaskletHasOneTask() {
    ParallelStreamTasklet<VoidMethod> tasklet =
        ParallelStreamTasklet.of(Collections.singletonList(voidMethodTask));
    assertEquals(tasklet.tasks().size(), 1);
  }

  @Test
  @Override
  void voidMethodTaskletHasFiveTask() {
    ParallelStreamTasklet<VoidMethod> tasklet =
        ParallelStreamTasklet.of(
            asList(voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask, voidMethodTask));
    assertEquals(tasklet.tasks().size(), 5);
  }

  @Test
  @Override
  void voidMethodTaskletWillThrowVoidMethodExecutionException() {
    ParallelStreamTasklet<VoidMethod> tasklet =
        ParallelStreamTasklet.<VoidMethod>builder().addTasks(voidMethodTaskWithException).build();
    assertThrows(
        VoidMethodExecutionException.class, () -> tasklet.execute(contribution, chunkContext));
  }

  @Test
  @Override
  void voidMethodTaskletShouldRunSuccessfully() {
    ParallelStreamTasklet<VoidMethod> tasklet =
        ParallelStreamTasklet.of(Collections.singletonList(voidMethodTask));
    assertAll(() -> tasklet.execute(contribution, chunkContext));
  }
}
