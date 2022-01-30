package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.tasklet.function.VoidMethod;
import com.efindi.batch.commons.tasklet.function.VoidMethodExecutionException;
import com.efindi.batch.commons.tasklet.task.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractTaskletTest {

  protected static Task<Runnable> runnableTask;
  protected static Task<VoidMethod> voidMethodTask;
  protected static Task<VoidMethod> voidMethodTaskWithException;
  @Mock
  protected StepContribution contribution;
  @Mock
  protected ChunkContext chunkContext;

  @BeforeAll
  static void initAll() {
    StepExecution stepExecution = Mockito.mock(StepExecution.class);
    StepContext stepContext = Mockito.mock(StepContext.class);
    ChunkContext chunkContext = Mockito.mock(ChunkContext.class);

    Mockito.when(chunkContext.getStepContext()).thenReturn(stepContext);
    Mockito.when(stepContext.getStepExecution()).thenReturn(stepExecution);
    runnableTask = Task.of(() -> {});
    voidMethodTask = Task.of((stepContribution, stepChunkContext) -> {});
    voidMethodTaskWithException =
        Task.of((stepContribution, stepChunkContext) -> {
          throw new VoidMethodExecutionException("Test Throwing VoidMethodExecutionException");
        });
  }

  abstract void runnableTaskletShouldRunSuccessfully();

  abstract void voidMethodTaskletShouldRunSuccessfully();

  abstract void voidMethodTaskletWillThrowVoidMethodExecutionException();
}
